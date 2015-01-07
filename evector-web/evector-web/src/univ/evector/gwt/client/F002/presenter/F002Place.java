package univ.evector.gwt.client.F002.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.gwt.client.F002.view.F002View;
import univ.evector.gwt.client.index.IndexMenuMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F002Place extends BaseFormPlace<Void, F002Place> {

    public F002Place() {
        super(null, IndexMenuMapper.F.F002, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F002Place> implements PlaceTokenizer<F002Place> {

        @Override
        public F002Place getPlace() {
            return new F002Place();
        }

    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<Void, F002Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<Void, F002Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F002Presenter(new F002View()));
            }
        });
    }

}
