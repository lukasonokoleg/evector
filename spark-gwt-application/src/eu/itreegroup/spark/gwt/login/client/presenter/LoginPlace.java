package eu.itreegroup.spark.gwt.login.client.presenter;

import lt.jmsys.spark.gwt.application.client.common.presenter.ValuePlace;

import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends ValuePlace<Layout> {

    public LoginPlace() {
        this(null);
    }

    public LoginPlace(Layout layout) {
        super(Module.PUBLIC, null, null, layout);
    }

    public static class Tokenizer extends ValuePlace.Tokenizer<Layout, LoginPlace> implements PlaceTokenizer<LoginPlace> {

        @Override
        public LoginPlace getPlace() {
            return new LoginPlace();
        }
    }

}