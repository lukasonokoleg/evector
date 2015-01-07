package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStartEvent;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStartHandler;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;

public class PopupRestoreManager<P> {

    private static final int MAX_RESTORE_DEPTH = 5;

    public interface PopupRestoreCallback<P> {

        void restorePopup(FormActivity<?, ?> parentActivity, FormActivity<?, P> popupActivity);
    }

    private FormActivity<?, P> popupActivity;
    private FormActivity<?, ?> parentActivity;
    private PlaceWithId hiddenAtPlace;
    private HandlerRegistration placeRestoreRegistration;
    private HandlerRegistration placeChangeHandlerRedistration;
    private int hiddenAtPlaceDepth;

    private PopupRestoreCallback<P> callback;

    public void attach(FormActivity<?, ?> parentActivity, FormActivity<?, P> popupActivity, PopupRestoreCallback<P> callback) {
        detach();
        this.popupActivity = popupActivity;
        this.parentActivity = parentActivity;
        this.callback = callback;

        // shownAtPlace = ClientFactory.getInstance().getPlaceController().getWhere();
        placeChangeHandlerRedistration = ClientFactory.getInstance().getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {

            @Override
            public void onPlaceChange(PlaceChangeEvent event) {
                temporarilyHidePopup();
            }
        });

        if (null != parentActivity) {
            parentActivity.getStartSource().addActivityStartHandler(new ActivityStartHandler() {

                @Override
                public void onStart(ActivityStartEvent event) {
                    restoreTemporarilyHiddenPopup();
                }
            });
        }
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
        popupActivity = null;
        parentActivity = null;
        callback = null;
    }

    protected void temporarilyHidePopup() {
        //popupActivity.close(null != parentActivity && parentActivity.isStopConfirmed());
    }

    private void restoreTemporarilyHiddenPopup() {
        hiddenAtPlaceDepth++;
        if (hiddenAtPlaceDepth > MAX_RESTORE_DEPTH) {
            if (null != placeRestoreRegistration) {
                placeRestoreRegistration.removeHandler();
                placeRestoreRegistration = null;
                hiddenAtPlace = null;
            }
        } else if (null != hiddenAtPlace) {
            Place place = ClientFactory.getInstance().getPlaceController().getWhere();
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
        if (null != callback) {
            callback.restorePopup(parentActivity, popupActivity);
        }
    }
}
