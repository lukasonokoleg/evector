package univ.evector.db.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.Document;
import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.DocumentWithFile;
import univ.evector.beans.browse.FindDocuments;
import univ.evector.beans.helper.DocumentDocIdHelper;
import univ.evector.beans.helper.DocumentHelper;
import univ.evector.db.dao.DocumentDao;
import univ.evector.gwt.service.EvectorFileUploadServlet;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class DocumentDaoImpl extends CommonDaoImpl implements DocumentDao {

    private final static Logger LOGGER = Logger.getLogger(DocumentDaoImpl.class);

    private final static BeanPropertyRowMapper<DocumentWithBlob> DOCUMENT_WITH_BLOB_ROW_MAPPER = new BeanPropertyRowMapper<>(DocumentWithBlob.class);

    private final static BeanPropertyRowMapper<Document> DOCUMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Document.class);

    private final static BeanPropertyRowMapper<FindDocuments> FIND_DOCUMENTS_ROW_MAPPER = new BeanPropertyRowMapper<>(FindDocuments.class);

    @Override
    public void saveDocuments(List<Document> documents, HttpServletRequest request) throws SparkBusinessException {
        List<DocumentWithFile> documentsWithFille = getDocumentsWithFileFromRequest(documents, request);
        saveDocumentsWithFile(documentsWithFille);
    }

    @Override
    public QueryResult<FindDocuments> findDocuments(QueryMetaData queryMetaData, String query) throws SparkBusinessException {
        String select = "SELECT " //
                + "doc.* " //
                + "";
        String from = "FROM "//
                + "evo_documents doc ";
        String where = "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", query);

        return browseJdbcHelper.browse(select, from, where, queryMetaData, FIND_DOCUMENTS_ROW_MAPPER, paramMap);
    }

    @Override
    public Document findDocument(Long docId) throws SparkBusinessException {
        String sql = "SELECT "//
                + "doc.* "//
                + "FROM "//
                + "evo_documents doc"//
                + "WHERE "//
                + "doc.doc_id  =:doc_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("doc_id", docId);

        Document retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, DOCUMENT_ROW_MAPPER);
        return retVal;
    }

    @Override
    public DocumentWithBlob findDocumentWithBlob(String docId) throws SparkBusinessException {
        String sql = "SELECT "//
                + "doc.* "//
                + "FROM "//
                + "evo_documents doc"//
                + "WHERE "//
                + "doc.doc_id  =:doc_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("doc_id", docId);

        DocumentWithBlob retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, DOCUMENT_WITH_BLOB_ROW_MAPPER);
        return retVal;
    }

    private void saveDocumentsWithFile(List<DocumentWithFile> documents) throws SparkBusinessException {
        if (documents != null) {
            for (DocumentWithFile document : documents) {
                saveDocumentWithFile(document);
            }
        }
    }

    private void saveDocumentWithFile(DocumentWithFile document) throws SparkBusinessException {
        if (document == null) {
            throw new IllegalArgumentException();
        }
        if (DocumentDocIdHelper.hasDocId(document)) {
            updateDocument(document);
        } else {
            insertDocument(document);
        }
    }

    private void updateDocument(DocumentWithFile document) {

        String sql = "UPDATE evo_documents "//
                + "SET "//
                + "doc_size = :doc_size, "//
                + "doc_name = :doc_name, "//
                + "doc_mime_type = :doc_mime_type, "//
                + "doc_date = :doc_date, "//
                + "doc_blob = :doc_blob "//
                + "WHERE doc_id = :doc_id "//
                + ""//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(document);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("doc_blob", getSqlLobValue(document));

        namedParamJdbcTemplate.update(sql, paramSource);
    }

    private void insertDocument(DocumentWithFile document) {
        DocumentHelper.ensureDocDate(document);

        String sql = "INSERT INTO evo_documents "//
                + "( "//
                + "doc_size, "//
                + "doc_name, "//
                + "doc_mime_type, "//
                + "doc_date, "//
                + "doc_blob "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":doc_size, "//
                + ":doc_name, "//
                + ":doc_mime_type, "//
                + ":doc_date, "//
                + ":doc_blob "//
                + ") "//
                + "";
        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(document);

        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("doc_blob", getSqlLobValue(document));
        paramSource.registerSqlType("doc_blob", Types.BLOB);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        Long docId = generatedKeyHolder.getKey().longValue();
        document.setDoc_id(docId);
    }

    private SqlLobValue getSqlLobValue(DocumentWithFile document) {
        File file = document.getDoc_file();
        InputStream fileIs = null;
        SqlLobValue retVal = null;
        if (file != null) {
            try {
                fileIs = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                LOGGER.error("Cought FileNotFoundException while saving application message document.", e);
                throw new IllegalStateException("Document must have the file.", e);
            }
        }
        if (fileIs != null) {
            retVal = new SqlLobValue(fileIs, (int) file.length());
        }
        return retVal;
    }

    private List<DocumentWithFile> getDocumentsWithFileFromRequest(List<Document> documents, HttpServletRequest request) throws SparkBusinessException {
        List<DocumentWithFile> retVal = new ArrayList<>();

        if (documents != null) {
            for (Document document : documents) {
                DocumentWithFile documentWithFile = getDocumentWithFile(document, request);
                if (documentWithFile != null) {
                    retVal.add(documentWithFile);
                }
            }
        }
        return retVal;
    }

    private static DocumentWithFile getDocumentWithFile(Document document, HttpServletRequest request) {
        DocumentWithFile retVal = null;
        if (document != null) {
            retVal = new DocumentWithFile();

            String docTmpId = document.getDoc_tmp_id();
            Date docDate = document.getDoc_date();
            File docFile = EvectorFileUploadServlet.getTmpDocumentFile(request, docTmpId);
            Long docId = document.getDoc_id();
            String docMimeType = document.getDoc_mime_type();
            String docName = document.getDoc_name();
            Long docSize = document.getDoc_size();

            retVal.setDoc_tmp_id(docTmpId);
            retVal.setDoc_date(docDate);
            retVal.setDoc_file(docFile);
            retVal.setDoc_id(docId);
            retVal.setDoc_mime_type(docMimeType);
            retVal.setDoc_name(docName);
            retVal.setDoc_size(docSize);

            retVal.setDoc_file(docFile);
        }
        return retVal;
    }

    @Override
    public DocumentWithBlob findDocumentWithBlob(Long docId) throws SparkBusinessException {
        String sql = "SELECT "//
                + "doc.* "//
                + "FROM "//
                + "evo_documents doc "//
                + "WHERE "//
                + "doc.doc_id  =:doc_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("doc_id", docId);

        DocumentWithBlob retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, DOCUMENT_WITH_BLOB_ROW_MAPPER);
        return retVal;
    }

}
