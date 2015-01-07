package univ.evector.gwt.client.service;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.Document;
import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.browse.FindDocuments;

import com.google.gwt.core.shared.GwtIncompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DocumentServiceAsync {

    void saveDocuments(List<Document> documents, AsyncCallback<Void> callback);

    void findDocuments(String query, QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindDocuments>> callback);

    void findDocument(Long doc_id, AsyncCallback<Document> callback) throws SparkBusinessException;

    @GwtIncompatible
    void findDocumentWithBlob(String doc_id, AsyncCallback<DocumentWithBlob> callback) throws SparkBusinessException;
}