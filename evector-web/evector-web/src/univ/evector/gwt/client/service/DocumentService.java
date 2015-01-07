package univ.evector.gwt.client.service;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.Document;
import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.browse.FindDocuments;

import com.google.gwt.core.shared.GwtIncompatible;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface DocumentService extends RemoteService {

    void saveDocuments(List<Document> documents) throws SparkBusinessException;

    QueryResult<FindDocuments> findDocuments(String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    Document findDocument(Long doc_id) throws SparkBusinessException;

    @GwtIncompatible
    DocumentWithBlob findDocumentWithBlob(String doc_id) throws SparkBusinessException;

}