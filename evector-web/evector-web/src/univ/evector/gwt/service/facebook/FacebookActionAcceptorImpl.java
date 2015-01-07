package univ.evector.gwt.service.facebook;

import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.internal.service.UserService;
import eu.itreegroup.spark.facebook.action.FacebookCallbackAction;
import eu.itreegroup.spark.facebook.action.FacebookLoginAction;
import eu.itreegroup.spark.facebook.action.FacebookLogoutAction;
import eu.itreegroup.spark.facebook.application.FacebookActionHandler;

public class FacebookActionAcceptorImpl implements FacebookActionHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onFacebookLoginAction(FacebookLoginAction fbLogin) {

        
        
    }

    @Override
    public void onFacebookCallbackAction(FacebookCallbackAction fbCallback) {

    }

    @Override
    public void onFacebookLogoutAction(FacebookLogoutAction fbLogout) {

    }

}
