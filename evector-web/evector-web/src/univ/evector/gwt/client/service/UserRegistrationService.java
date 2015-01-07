package univ.evector.gwt.client.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import univ.evector.beans.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface UserRegistrationService extends RemoteService {

    User defaultRegistrationUser() throws SparkBusinessException;

    void registerUser(User user, String password) throws SparkBusinessException;

}
