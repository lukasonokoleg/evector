package lt.jmsys.spark.gwt.application.common.server.service;

import lt.jmsys.spark.bind.service.Service;
import lt.jmsys.spark.gwt.client.callback.LoginException;
import lt.jmsys.spark.gwt.server.service.ConnectionGetter;


public interface ServiceGetter {
    ConnectionGetter getConnectionGetter();
    <T extends Service> T getServiceImpl(Class<T> cl);
    <T extends Service> T getServiceImplAuth(Class<T> cl) throws LoginException;
}
