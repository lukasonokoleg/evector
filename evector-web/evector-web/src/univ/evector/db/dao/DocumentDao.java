package univ.evector.db.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.stereotype.Repository;

import univ.evector.beans.Document;
import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.browse.FindDocuments;

@Repository
public interface DocumentDao {

    Document findDocument(Long docId) throws SparkBusinessException;

    DocumentWithBlob findDocumentWithBlob(Long docId) throws SparkBusinessException;

    DocumentWithBlob findDocumentWithBlob(String docId) throws SparkBusinessException;

    void saveDocuments(List<Document> documents, HttpServletRequest request) throws SparkBusinessException;

    QueryResult<FindDocuments> findDocuments(QueryMetaData queryMetaData, String query) throws SparkBusinessException;

}
