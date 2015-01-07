package eu.itreegroup.spark.facebook.application;

import java.io.IOException;

import eu.itreegroup.spark.facebook.action.FacebookCallbackAction;
import eu.itreegroup.spark.facebook.action.FacebookLoginAction;
import eu.itreegroup.spark.facebook.action.FacebookLogoutAction;

public interface FacebookActionHandler {

    void onFacebookLoginAction(FacebookLoginAction fbLogin) throws IOException;

    void onFacebookCallbackAction(FacebookCallbackAction fbCallback) throws IOException;

    void onFacebookLogoutAction(FacebookLogoutAction fbLogout) throws IOException;

}