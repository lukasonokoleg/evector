package eu.itreegroup.spark.gwt.application.client.security;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.DocumentType;

public interface PrivilegesServiceAsync {

    void findDocumentPrivileges(DocumentType type, Long id, AsyncCallback<DocumentPrivileges> callback);

}
