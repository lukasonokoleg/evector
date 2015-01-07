package univ.evector.gwt.client.F007.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.beans.emotion.Emotion;
import univ.evector.gwt.client.F007.view.F007View;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F007Place extends BaseFormPlace<Emotion, F007Place> {

    public F007Place(Long id) {
        super(id);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F007Place> implements PlaceTokenizer<F007Place> {

        @Override
        public F007Place getPlace() {
            return new F007Place(null);
        }
    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<Emotion, F007Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<Emotion, F007Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F007Presenter(new F007View()));
            }
        });
    }

}
