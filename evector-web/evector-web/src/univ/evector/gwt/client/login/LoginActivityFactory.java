package univ.evector.gwt.client.login;

import com.google.gwt.core.client.GWT;

public class LoginActivityFactory {

    private static final LoginActivityFactory instance = GWT.create(LoginActivityFactory.class);

    public static LoginActivityFactory getInstance() {
        return instance;
    }

}
