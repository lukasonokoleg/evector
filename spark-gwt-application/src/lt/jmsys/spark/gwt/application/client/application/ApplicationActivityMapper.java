package lt.jmsys.spark.gwt.application.client.application;

import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.common.client.application.AsyncActivityMapper;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;

public class ApplicationActivityMapper implements AsyncActivityMapper {

    private NotImplPresenter notImplPresenter = new NotImplPresenter();

    public ApplicationActivityMapper() {
        super();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getActivity(Place place, AsyncCallback<Activity> callback) {
        if (place instanceof BaseFormPlace) {
            BaseFormPlace baseFormPlace = (BaseFormPlace) place;
            baseFormPlace.createActivity(callback);
        } else {
            callback.onSuccess(notImplPresenter);
        }
    }

    private static class NotImplPresenter extends AbstractActivity {

        @Override
        public void start(AcceptsOneWidget panel, EventBus eventBus) {
            panel.setWidget(new HTML("<h2 style='text-align: center;'>not implemented yet</h2>"));
        }
    }
}
