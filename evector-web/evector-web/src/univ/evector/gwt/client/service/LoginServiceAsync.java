package univ.evector.gwt.client.service;

import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;
import univ.evector.gwt.client.L001.bean.LoginType;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync extends lt.jmsys.spark.gwt.application.common.client.login.service.LoginServiceAsync {

    void getFaceBookLoginUrl(AsyncCallback<String> callback);

    void create_session_by_facebook(AsyncCallback<LoginResult> callback);

    void getUserLoginType(AsyncCallback<LoginType> callback);

}