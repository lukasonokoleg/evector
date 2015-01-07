package lt.jmsys.spark.gwt.application.client.application;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SplitPointCallback implements RunAsyncCallback {

    private AsyncCallback<Activity> callback;

    public SplitPointCallback(AsyncCallback<Activity> callback) {
        this.callback = callback;
    }

    @Override
    public void onFailure(Throwable reason) {
        callback.onFailure(reason);
    }
}
