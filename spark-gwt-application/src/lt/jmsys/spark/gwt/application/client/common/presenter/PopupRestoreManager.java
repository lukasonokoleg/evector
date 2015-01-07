package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.common.dialog.ConfirmationDialog;
import lt.jmsys.spark.gwt.application.client.common.presenter.AlcsPopupEditFormPresenter.AlcsEditFormPopupDisplay;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PlaceHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupRestoreManager<E> {

    private static final int MAX_RESTORE_DEPTH = 5;

    private AlcsPopupEditFormPresenter<E> presenter;
    private Popup view;
    private Place shownAtPlace;
    private PlaceWithId hiddenAtPlace;
    private HandlerRegistration placeRestoreRegistration;
    private HandlerRegistration placeChangeHandlerRedistration;
    private int hiddenAtPlaceDepth;

    private interface Popup extends IsWidget {

        void showPopup();

        void hidePopup(boolean warnIfModified);

        void onRestoreManagerAttached(boolean attached);

        boolean isPopupShowing();

        void addCloseHandler(CloseHandler<Void> handler);
    }

    public boolean attach(AlcsEditFormPopupDisplay<E> view) {
        return attach(null, view);
    }

    public boolean attach(AlcsPopupEditFormPresenter<E> presenter) {
        if (presenter.getView() instanceof AlcsEditFormPopupDisplay) {
            return attach(presenter, (AlcsEditFormPopupDisplay<E>) presenter.getView());
        } else {
            return false;
        }
    }

    public boolean attach(ConfirmationDialog view) {
        return attach(null, new ConfirmPopupAdapter(view));
    }

    private boolean attach(AlcsPopupEditFormPresenter<E> presenter, AlcsEditFormPopupDisplay<E> view) {
        return attach(presenter, new EditFormPopupAdapter<E>(view));
    }

    private boolean attach(AlcsPopupEditFormPresenter<E> presenter, Popup view) {
        detach();
        this.presenter = presenter;
        this.view = view;
        view.asWidget().addAttachHandler(new Handler() {

            @Override
            public void onAttachOrDetach(AttachEvent event) {
                if (event.isAttached()) {
                    shownAtPlace = ClientFactory.getInstance().getPlaceController().getWhere();
                    if (null == placeChangeHandlerRedistration) {
                        placeChangeHandlerRedistration = ClientFactory.getInstance().getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {

                            @Override
                            public void onPlaceChange(PlaceChangeEvent event) {
                                Place place = event.getNewPlace();
                                if (CompositePlace.isPopupPlace(place) || PlaceHelper.isStateEqual(shownAtPlace, place, true)) {
                                    return;
                                }
                                temporarilyHidePopup();
                            }
                        });
                    }
                }
            }
        });
        view.addCloseHandler(new CloseHandler<Void>() {

            @Override
            public void onClose(CloseEvent<Void> event) {
                if (null != placeChangeHandlerRedistration) {
                    placeChangeHandlerRedistration.removeHandler();
                    placeChangeHandlerRedistration = null;
                }
            }
        });
        view.onRestoreManagerAttached(true);
        return true;
    }

    public void detach() {
        if (null != placeChangeHandlerRedistration) {
            placeChangeHandlerRedistration.removeHandler();
            placeChangeHandlerRedistration = null;
        }
        if (null != placeRestoreRegistration) {
            placeRestoreRegistration.removeHandler();
            placeRestoreRegistration = null;
        }
        hiddenAtPlaceDepth = 0;
        presenter = null;
        if (null != view) {
            view.onRestoreManagerAttached(false);
        }
        view = null;
    }

    protected void temporarilyHidePopup() {
        if (view.isPopupShowing()) {
            hiddenAtPlace = (shownAtPlace instanceof PlaceWithId) ? (PlaceWithId) shownAtPlace : null;
            hiddenAtPlaceDepth = 0;
            view.hidePopup(false);

            if (null == placeRestoreRegistration) {
                placeRestoreRegistration = ClientFactory.getInstance().getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {

                    @Override
                    public void onPlaceChange(PlaceChangeEvent event) {
                        restoreTemporarilyHiddenPopup(event);
                    }
                });
            }
        }
    }

    private void restoreTemporarilyHiddenPopup(PlaceChangeEvent event) {
        hiddenAtPlaceDepth++;
        if (hiddenAtPlaceDepth > MAX_RESTORE_DEPTH) {
            if (null != placeRestoreRegistration) {
                placeRestoreRegistration.removeHandler();
                placeRestoreRegistration = null;
                hiddenAtPlace = null;
            }
        } else if (null != hiddenAtPlace) {
            Place place = event.getNewPlace();
            if (place instanceof PlaceWithId) {
                PlaceWithId placeWithId = (PlaceWithId) place;
                if (ConversionHelper.isEqual(hiddenAtPlace.getStateId(), placeWithId.getStateId())) {
                    placeRestoreRegistration.removeHandler();
                    placeRestoreRegistration = null;
                    restorePopup();
                }
            }
        }
    }

    protected void restorePopup() {
        if (null != presenter) {
            presenter.restorePopup();
        } else {
            view.showPopup();
        }
    }

    private static class EditFormPopupAdapter<E> implements Popup {

        private AlcsEditFormPopupDisplay<E> view;

        public EditFormPopupAdapter(AlcsEditFormPopupDisplay<E> view) {
            this.view = view;
        }

        @Override
        public void showPopup() {
            view.showPopup();
        }

        @Override
        public void onRestoreManagerAttached(boolean attached) {
            view.onRestoreManagerAttached(attached);
        }

        @Override
        public boolean isPopupShowing() {
            return view.isPopupShowing();
        }

        @Override
        public Widget asWidget() {
            return view.asWidget();
        }

        @Override
        public void hidePopup(boolean warnIfModified) {
            view.hidePopup(warnIfModified);
        }

        @Override
        public void addCloseHandler(final CloseHandler<Void> handler) {
            view.addCloseHandler(new CloseHandler<AlcsPopupEditFormPresenter.AlcsEditFormPopupDisplay<E>>() {

                @Override
                public void onClose(CloseEvent<AlcsEditFormPopupDisplay<E>> event) {
                    handler.onClose(null);
                }
            });

        }

    }

    private static class ConfirmPopupAdapter implements Popup {

        private ConfirmationDialog view;

        public ConfirmPopupAdapter(ConfirmationDialog view) {
            this.view = view;
        }

        @Override
        public void showPopup() {
            view.confirm();
        }

        @Override
        public void onRestoreManagerAttached(boolean attached) {

        }

        @Override
        public boolean isPopupShowing() {
            return view.isPopupShowing();
        }

        @Override
        public Widget asWidget() {
            return view.asWidget();
        }

        @Override
        public void hidePopup(boolean warnIfModified) {
            view.hidePopup(true);
        }

        @Override
        public void addCloseHandler(final CloseHandler<Void> handler) {
            view.addCloseHandler(new CloseHandler<PopupPanel>() {

                @Override
                public void onClose(CloseEvent<PopupPanel> event) {
                    handler.onClose(null);
                }
            });

        }

    }
}
