package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public abstract class AlcsPrivilegedEditFormPresenter<E> extends AlcsPopupEditFormPresenter<E> {

    public AlcsPrivilegedEditFormPresenter(AlcsEditFormDisplay<E> view) {
        super(view);
    }

    @Override
    public void showPopup(final MessageContainer parentMsgPanel, final EventBus eventBus, Double valueId) {
        final AsyncCallback<E> valueCallback = new AsyncCallback<E>() {

            @Override
            public void onFailure(Throwable caught) {
                onStartFailedForPopup(parentMsgPanel, eventBus, caught);
            }

            @Override
            public void onSuccess(final E protectedObject) {
                final AsyncCallback<FormPrivileges> privilegesCallback = new AsyncCallback<FormPrivileges>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        onStartFailedForPopup(parentMsgPanel, eventBus, caught);
                    }

                    @Override
                    public void onSuccess(FormPrivileges privileges) {
                        AlcsEditFormDisplay<E> view = getView();
                        view.setPrivileges(privileges);
                        setValue(protectedObject, privileges);
                        if (view instanceof AlcsEditFormPopupDisplay<?>) {
                            AlcsEditFormPopupDisplay<E> tmpView = (AlcsEditFormPopupDisplay<E>) view;
                            tmpView.showPopup();
                        }
                    }
                };
                FormPrivileges.getPrivileges(toProtectedObject(protectedObject), privilegesCallback);
            }

        };
        startFindValue(valueId, valueCallback);
    }

    @Override
    protected void onStartFailedForPopup(MessageContainer messagePanel, EventBus eventBus, Throwable caught) {

        new CommonProgressShowingCallback<Void>(messagePanel) {

            @Override
            protected void call() {
            }
        }.onFailure(caught);
    }

    @Override
    public final void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        start(panel, eventBus, null);
    }

    public final void start(final AcceptsOneWidget panel, final EventBus eventBus, final AsyncCallback<Void> startCallback) {
        final AsyncCallback<E> valueCallback = new AsyncCallback<E>() {

            @Override
            public void onFailure(Throwable caught) {
                onStartFailed(panel, eventBus, caught);
            }

            @Override
            public void onSuccess(final E protectedObject) {
                final AsyncCallback<FormPrivileges> privilegesCallback = new AsyncCallback<FormPrivileges>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        onStartFailed(panel, eventBus, caught);
                        if (null != startCallback) {
                            startCallback.onFailure(caught);
                        }
                    }

                    @Override
                    public void onSuccess(FormPrivileges privileges) {
                        getView().setPrivileges(privileges);
                        start(panel, eventBus, protectedObject, privileges);
                        if (null != startCallback) {
                            startCallback.onSuccess(null);
                        }
                    }
                };

                FormPrivileges.getPrivileges(toProtectedObject(protectedObject), privilegesCallback);
            }

        };
        PlaceController placeController = ClientFactory.getInstance().getPlaceController();
        final Place place = placeController.getWhere();
        final Double id;
        if (place instanceof PlaceWithId) {
            id = ((PlaceWithId) place).getId();
        } else {
            id = null;
        }
        startFindValue(id, valueCallback);
    }

    protected void onStartFailed(AcceptsOneWidget panel, EventBus eventBus, Throwable caught) {
        getView().setPrivileges(FormPrivileges.getDefaultPrivileges());
        getView().setValue(null);
        if (null != panel) {
            panel.setWidget(getView());
        }
        new CommonProgressShowingCallback<Void>(getView().getMessageContainer()) {

            @Override
            protected void call() {
            }
        }.onFailure(caught);
    }

    public final void updateValueAndPrivileges(final Double id) {
        updateValueAndPrivileges(id, null);
    }

    public final void updateValueAndPrivileges(final Double id, final AsyncCallback<E> callback) {
        updateValueAndPrivileges(null, id, callback);
    }

    public final void updateValueAndPrivileges(E value) {
        updateValueAndPrivileges(value, null);
    }

    public final void updateValueAndPrivileges(E value, final AsyncCallback<E> callback) {
        updateValueAndPrivileges(value, null, callback);
    }

    private final void updateValueAndPrivileges(final E value, final Double id, final AsyncCallback<E> callback) {
        final AsyncCallback<E> valueCallback = new AsyncCallback<E>() {

            @Override
            public void onFailure(Throwable caught) {
                if (null != callback) {
                    callback.onFailure(caught);
                }
            }

            @Override
            public void onSuccess(final E protectedObject) {
                final AsyncCallback<FormPrivileges> privilegesCallback = new AsyncCallback<FormPrivileges>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        if (null != callback) {
                            callback.onFailure(caught);
                        }
                    }

                    @Override
                    public void onSuccess(FormPrivileges privileges) {
                        getView().setPrivileges(privileges);
                        setValue(protectedObject, privileges);
                        if (null != callback) {
                            callback.onSuccess(protectedObject);
                        }
                    }
                };
                FormPrivileges.getPrivileges(toProtectedObject(protectedObject), privilegesCallback);
            }

        };
        if (null == value && null != id) {
            startFindValue(id, valueCallback);
        } else {
            valueCallback.onSuccess(value);
        }
    }

    protected abstract void setValue(E value, FormPrivileges privileges);

    protected abstract void start(final AcceptsOneWidget panel, final EventBus eventBus, final E value, final FormPrivileges privileges);

}
