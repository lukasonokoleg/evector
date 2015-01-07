package lt.jmsys.spark.gwt.application.common.client.login.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;
import lt.jmsys.spark.gwt.application.shared.security.PrivateData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {

    LoginResult create_session_by_pass(String username, @PrivateData String password, @PrivateData String newPassword1, @PrivateData String newPassword2, boolean rememberLogin) throws SparkBusinessException;

    LoginResult relogin(Double userId, @PrivateData String password, Double companyId) throws SparkBusinessException;

    LoginResult create_session_by_key() throws SparkBusinessException;

    void forgotPassword(String email) throws SparkBusinessException;

    Boolean hasSessionKey() throws SparkBusinessException;

}
