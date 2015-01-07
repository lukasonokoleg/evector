package lt.jmsys.spark.gwt.application.client.application;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncPresenterCreator<T, P> implements RunAsyncCallback {

    protected AsyncCallback<BaseFormPresenter<T, P>> callback;

    public AsyncPresenterCreator(AsyncCallback<BaseFormPresenter<T, P>> callback) {
        this.callback = callback;
    }

    @Override
    public void onFailure(Throwable reason) {
        callback.onFailure(reason);
    }

}
