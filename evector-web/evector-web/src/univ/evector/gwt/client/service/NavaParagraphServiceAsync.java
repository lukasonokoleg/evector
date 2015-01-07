package univ.evector.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NavaParagraphServiceAsync {

    void updateBookNavaParagraph(Long prgId, AsyncCallback<Void> callback);

}