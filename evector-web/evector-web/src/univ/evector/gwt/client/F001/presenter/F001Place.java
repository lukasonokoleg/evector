package univ.evector.gwt.client.F001.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.gwt.client.F001.view.F001View;
import univ.evector.gwt.client.index.IndexMenuMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F001Place extends BaseFormPlace<Void, F001Place> {

    public F001Place() {
        super(null, IndexMenuMapper.F.F001, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F001Place> implements PlaceTokenizer<F001Place> {

        @Override
        public F001Place getPlace() {
            return new F001Place();
        }

    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<Void, F001Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<Void, F001Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F001Presenter(new F001View()));
            }
        });
    }

}
