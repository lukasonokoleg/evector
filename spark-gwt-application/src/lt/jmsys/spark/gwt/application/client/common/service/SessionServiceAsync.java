package lt.jmsys.spark.gwt.application.client.common.service;

import java.util.ArrayList;

import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionServiceAsync {
    
    void preloadSessionData(boolean doSignOut, AsyncCallback<PreloadedSessionData> callback);
    
    void touchSession(AsyncCallback<Void> callback);
    
    void receiveMessages(AsyncCallback<ArrayList<Message>> callback);

}
