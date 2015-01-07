package univ.evector.gwt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.Document;
import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.browse.FindDocuments;
import univ.evector.db.dao.DocumentDao;
import univ.evector.gwt.client.service.DocumentService;
import eu.itreegroup.spark.gwt.rpc.AcceptsHttpServletContext;
import eu.itreegroup.spark.gwt.rpc.HttpServletContext;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService, AcceptsHttpServletContext {

    @Autowired
    private DocumentDao documentDao;

    private HttpServletContext httpServletContext;

    @Override
    public void saveDocuments(List<Document> documents) throws SparkBusinessException {
        HttpServletRequest request = null;
        if (httpServletContext != null) {
            request = httpServletContext.getHttpServletRequest();
        }
        documentDao.saveDocuments(documents, request);
    }

    @Override
    public QueryResult<FindDocuments> findDocuments(String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        return documentDao.findDocuments(queryMetaData, query);
    }

    @Override
    public void setHttpServletContext(HttpServletContext httpServletContext) {
        this.httpServletContext = httpServletContext;
    }

    @Override
    public Document findDocument(Long doc_id) throws SparkBusinessException {
        Document retVal = documentDao.findDocument(doc_id);
        return retVal;
    }

    @Override
    public DocumentWithBlob findDocumentWithBlob(String docId) throws SparkBusinessException {
        DocumentWithBlob retVal = documentDao.findDocumentWithBlob(docId);
        return retVal;
    }

}