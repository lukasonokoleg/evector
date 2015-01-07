package lt.jmsys.spark.gwt.application.client.application.presenter;

import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;

import com.google.gwt.place.shared.PlaceTokenizer;

public class NotImplPlace extends PlaceWithId {

    public NotImplPlace(String menuCode) {
        super(null, menuCode, null);
    }

    public static class Tokenizer extends PlaceWithId.Tokenizer<NotImplPlace> implements PlaceTokenizer<NotImplPlace> {

        @Override
        public NotImplPlace getPlace() {
            return new NotImplPlace(null);
        }
    }
}