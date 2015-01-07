package univ.evector.gwt.client.F006.presenter;

import java.util.List;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.beans.Document;
import univ.evector.gwt.client.F006.view.F006View;
import univ.evector.gwt.client.index.IndexMenuMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F006Place extends BaseFormPlace<List<Document>, F006Place> {

    public F006Place() {
        super(null, IndexMenuMapper.F.NOT_IN_MENU, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F006Place> implements PlaceTokenizer<F006Place> {

        @Override
        public F006Place getPlace() {
            return new F006Place();
        }
    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<List<Document>, F006Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<List<Document>, F006Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F006Presenter(new F006View()));
            }
        });
    }

}