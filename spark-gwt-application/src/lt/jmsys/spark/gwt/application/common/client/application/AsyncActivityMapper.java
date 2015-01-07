package lt.jmsys.spark.gwt.application.common.client.application;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AsyncActivityMapper {

    public void getActivity(Place place, AsyncCallback<Activity> callback);
}
