package univ.evector.gwt.client.L002.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.beans.User;
import univ.evector.gwt.client.L002.view.L002View;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class L002Place extends BaseFormPlace<User, L002Place> {

    public L002Place() {
        super(null, null, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<L002Place> implements PlaceTokenizer<L002Place> {

        @Override
        public L002Place getPlace() {
            return new L002Place();
        }
    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<User, L002Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<User, L002Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new L002Presenter(new L002View()));
            }
        });
    }

}
