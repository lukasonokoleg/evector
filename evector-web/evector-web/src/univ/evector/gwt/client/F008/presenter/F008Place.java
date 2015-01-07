package univ.evector.gwt.client.F008.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.beans.emotion.EmotionModel;
import univ.evector.gwt.client.F008.view.F008View;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F008Place extends BaseFormPlace<EmotionModel, F008Place> {

    public F008Place() {
        super(null);
    }

    public F008Place(Number id) {
        super(id);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F008Place> implements PlaceTokenizer<F008Place> {

        @Override
        public F008Place getPlace() {
            return new F008Place(null);
        }
    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<EmotionModel, F008Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<EmotionModel, F008Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F008Presenter(new F008View()));
            }
        });

    }

}