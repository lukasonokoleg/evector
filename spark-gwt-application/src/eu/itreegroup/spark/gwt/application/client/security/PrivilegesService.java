package eu.itreegroup.spark.gwt.application.client.security;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.DocumentType;

@RemoteServiceRelativePath("rpc")
public interface PrivilegesService extends RemoteService {

    DocumentPrivileges findDocumentPrivileges(DocumentType type, Long id) throws SparkBusinessException;

}
