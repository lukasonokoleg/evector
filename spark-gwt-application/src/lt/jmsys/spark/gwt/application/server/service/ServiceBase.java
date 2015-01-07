package lt.jmsys.spark.gwt.application.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lt.jmsys.spark.bind.service.Service;
import lt.jmsys.spark.gwt.client.callback.LoginException;
import lt.jmsys.spark.gwt.server.service.ConnectionGetter;

public class ServiceBase {

    private ServletServiceContext context;

    public ServiceBase(ServletServiceContext context) {
        super();
        this.context = context;
    }

    protected ConnectionGetter getConnectionGetter() {
        return context.getConnectionGetter();
    }

    protected <T extends Service> T getServiceImplAuth(Class<T> cl) throws LoginException {
        return context.getServiceImplAuth(cl);
    }

    protected <T extends Service> T getServiceImpl(Class<T> cl) throws LoginException {
        return context.getServiceImpl(cl);
    }

    protected HttpServletRequest getThreadLocalRequest() {
        return context.getThreadLocalRequest();
    }

    protected HttpServletResponse getThreadLocalResponse() {
        return context.getThreadLocalResponse();
    }

}
