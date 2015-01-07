package lt.jmsys.spark.gwt.application.common.client.application;

import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.helper.State;
import lt.jmsys.spark.gwt.application.client.helper.StateManager;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ActivityPool {

    private StateManager<Activity> cache = new StateManager<Activity>();

    public ActivityMapper wrap(final ActivityMapper mapper) {
        return new ActivityMapper() {

            @Override
            public Activity getActivity(final Place place) {
                if (place instanceof PlaceWithId) {
                    PlaceWithId p = (PlaceWithId) place;
                    State<Activity> state = cache.getState(p.getStateId());
                    if (null != state) {
                        return state.getItem();
                    } else {
                        Activity activity = mapper.getActivity(place);
                        cache.updateState(p.getStateId(), null, activity);
                        return activity;
                    }
                } else {
                    return mapper.getActivity(place);
                }
            }
        };
    }

    public AsyncActivityMapper wrap(final AsyncActivityMapper mapper) {
        return new AsyncActivityMapper() {

            @Override
            public void getActivity(final Place place, final AsyncCallback<Activity> callback) {
                if (place instanceof ModulePlace) {
                    final PlaceWithId p = (PlaceWithId) place;
                    State<Activity> state = cache.getState(p.getStateId());
                    if (null != state) {
                        callback.onSuccess(state.getItem());
                    } else {
                        mapper.getActivity(place, new AsyncCallback<Activity>() {

                            @Override
                            public void onSuccess(Activity activity) {
                                cache.updateState(p.getStateId(), null, activity);
                                callback.onSuccess(activity);
                            }

                            @Override
                            public void onFailure(Throwable caught) {
                                callback.onFailure(caught);
                            }
                        });
                    }
                } else {
                    mapper.getActivity(place, callback);
                }
            }
        };
    }
}
