package univ.evector.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessageServiceAsync {

    void countUnreadMessages(AsyncCallback<Long> callback);

}
