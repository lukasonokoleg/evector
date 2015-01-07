package univ.evector.gwt.client.L001.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.gwt.client.L001.bean.LoginType;
import univ.evector.gwt.client.L001.view.L001View;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class L001Place extends BaseFormPlace<LoginType, L001Place> {

    public L001Place() {
        super(null, null, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<L001Place> implements PlaceTokenizer<L001Place> {

        @Override
        public L001Place getPlace() {
            return new L001Place();
        }

    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<LoginType, L001Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<LoginType, L001Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new L001Presenter(new L001View()));
            }
        });
    }

}
