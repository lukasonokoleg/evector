package univ.evector.gwt.client.F003.presenter;

import lt.jmsys.spark.gwt.application.client.application.AsyncPresenterCreator;
import lt.jmsys.spark.gwt.application.client.common.presenter.BaseFormPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import univ.evector.beans.book.Book;
import univ.evector.gwt.client.F003.view.F003TabsView;
import univ.evector.gwt.client.index.IndexMenuMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class F003Place extends BaseFormPlace<Book, F003Place> {

    public F003Place() {
        super(null, IndexMenuMapper.F.F003, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<F003Place> implements PlaceTokenizer<F003Place> {

        @Override
        public F003Place getPlace() {
            return new F003Place();
        }

    }

    @Override
    public void createPresenter(AsyncCallback<BaseFormPresenter<Book, F003Place>> callback) {
        GWT.runAsync(new AsyncPresenterCreator<Book, F003Place>(callback) {

            @Override
            public void onSuccess() {
                callback.onSuccess(new F003TabsPresenter(new F003TabsView()));
            }
        });
    }

}
