package univ.evector.gwt.client.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;
import univ.evector.gwt.client.L001.bean.LoginType;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends lt.jmsys.spark.gwt.application.common.client.login.service.LoginService {

    String getFaceBookLoginUrl() throws SparkBusinessException;

    LoginResult create_session_by_facebook() throws SparkBusinessException;

    LoginType getUserLoginType() throws SparkBusinessException;

}
