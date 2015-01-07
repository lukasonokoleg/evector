package univ.evector.gwt.client.service;

import univ.evector.beans.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserRegistrationServiceAsync {

    void defaultRegistrationUser(AsyncCallback<User> callback);

    void registerUser(User user, String password, AsyncCallback<Void> callback);

}
