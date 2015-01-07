package lt.jmsys.spark.gwt.application.client.application;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppActivityFactory {

    private static final AppActivityFactory instance = GWT.create(AppActivityFactory.class);

    public static AppActivityFactory getInstance() {
        return instance;
    }

    void newPub001Activity(final AsyncCallback<Activity> callback) {
        GWT.runAsync(new SplitPointCallback(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(null);
            }

        });
    }

}
