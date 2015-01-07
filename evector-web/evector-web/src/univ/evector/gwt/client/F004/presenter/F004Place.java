package univ.evector.gwt.client.F004.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.gwt.client.F004.view.F004View;
import univ.evector.gwt.client.index.IndexMenuMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F004Place extends BaseFormPlace<Void, F004Place> {

    public F004Place() {
        super(null, IndexMenuMapper.F.F004, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F004Place> implements PlaceTokenizer<F004Place> {

        @Override
        public F004Place getPlace() {
            return new F004Place();
        }

    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<Void, F004Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<Void, F004Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F004Presenter(new F004View()));
            }
        });
    }
}
