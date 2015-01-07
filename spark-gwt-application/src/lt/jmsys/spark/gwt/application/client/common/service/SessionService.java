package lt.jmsys.spark.gwt.application.client.common.service;

import java.util.ArrayList;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SessionService")
public interface SessionService extends RemoteService {

    PreloadedSessionData preloadSessionData(boolean doSignOut) throws SparkBusinessException;
    
    void touchSession();
    
    ArrayList<Message> receiveMessages();

}
