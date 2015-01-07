package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseFormPlace<T, P> extends PlaceWithId {

    public BaseFormPlace(Number id) {
        super(id);
    }

    public BaseFormPlace(Module module, String menuCode, Number id) {
        super(module, menuCode, id);
    }

    public void createActivity(final AsyncCallback<Activity> callback) {
        AsyncCallback<BaseFormPresenter<T, P>> presenterCallback = new AsyncCallback<BaseFormPresenter<T, P>>() {

            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(BaseFormPresenter<T, P> presenter) {
                callback.onSuccess(new FormActivity<T, P>(presenter));
            }

        };
        createPresenter(presenterCallback);
    }

    public abstract void createPresenter(AsyncCallback<BaseFormPresenter<T, P>> callback);
}
