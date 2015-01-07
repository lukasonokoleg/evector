package univ.evector.gwt.client.F005.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.gwt.client.F005.view.F005View;
import univ.evector.gwt.client.index.IndexMenuMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F005Place extends BaseFormPlace<Void, F005Place> {

    public F005Place() {
        super(null, IndexMenuMapper.F.F005, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F005Place> implements PlaceTokenizer<F005Place> {

        @Override
        public F005Place getPlace() {
            return new F005Place();
        }

    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<Void, F005Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<Void, F005Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F005Presenter(new F005View()));
            }
        });
    }

}
