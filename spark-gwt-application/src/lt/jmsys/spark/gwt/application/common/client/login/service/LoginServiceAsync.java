package lt.jmsys.spark.gwt.application.common.client.login.service;

import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

    void create_session_by_pass(String p_email, String p_password, String newPassword1, String newPassword2, boolean rememberLogin, AsyncCallback<LoginResult> callback);

    void create_session_by_key(AsyncCallback<LoginResult> callback);

    void relogin(Double userId, String p_password, Double companyId, AsyncCallback<LoginResult> callback);

    void forgotPassword(String email, AsyncCallback<Void> callback);

    void hasSessionKey(AsyncCallback<Boolean> callback);
}
