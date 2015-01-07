package lt.jmsys.spark.gwt.application.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lt.jmsys.spark.bind.service.Service;
import lt.jmsys.spark.gwt.client.callback.LoginException;
import lt.jmsys.spark.gwt.server.service.ConnectionGetter;

public interface ServletServiceContext {

    ConnectionGetter getConnectionGetter();

    void initSession() throws LoginException;

    <T extends Service> T getServiceImplAuth(Class<T> cl) throws LoginException;

    <T extends Service> T getServiceImpl(Class<T> cl) throws LoginException;

    HttpServletRequest getThreadLocalRequest();

    HttpServletResponse getThreadLocalResponse();
}
