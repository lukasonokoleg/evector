package univ.evector.gwt.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.fileupload.server.FileDownloadServlet;
import lt.jmsys.spark.gwt.fileupload.server.FileUploadException;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import univ.evector.beans.Document;
import univ.evector.beans.DocumentWithBlob;
import univ.evector.beans.DocumentWithFile;
import univ.evector.gwt.client.helper.EvectorDocumentHelper;
import univ.evector.gwt.client.service.DocumentService;

@SuppressWarnings("serial")
public class EvectorFileDownloadServlet extends FileDownloadServlet {

    private static final Logger LOGGER = Logger.getLogger(EvectorFileDownloadServlet.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd HH_mm_ss";

    private static final SimpleDateFormat FILE_NAME_DATE_PATTERN = new SimpleDateFormat(DATE_PATTERN);

    private DocumentService documentService;

    public EvectorFileDownloadServlet() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        documentService = springContext.getBean(DocumentService.class);
    }

    @Override
    public void downloadFile(String documentServerId, HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        if (EvectorDocumentHelper.isTemporaryServerId(documentServerId)) {
            downloadFromTemporaryDocuments(documentServerId, request, response);
        } else {
            try {
                downloadFromDbDocuments(documentServerId, request, response);
            } catch (SparkBusinessException e) {
                LOGGER.error("Cought SQL SparkBusinessException while downloading file from VVSIS DB. "//
                        + "documentServerId: " + documentServerId//
                        + "", e);
            } catch (SQLException e) {
                LOGGER.error("Cought SQL exception while downloading file from VVSIS DB. "//
                        + "documentServerId: " + documentServerId//
                        + "", e);
            }
        }
    }

    protected void downloadFromDbDocuments(String documentServerId, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,
            SparkBusinessException {
        if (!ConversionHelper.isEmpty(documentServerId)) {
            String doc_id = EvectorDocumentHelper.getDB_doc_id(documentServerId);

            DocumentWithBlob document = documentService.findDocumentWithBlob(doc_id);
            if (document != null) {

                String name = document.getDoc_name();
                String mimetype = document.getDoc_mime_type();

                Blob documentBlob = document.getDoc_blob();

                if (documentBlob != null) {
                    InputStream input = documentBlob.getBinaryStream();

                    int doc_size = (int) documentBlob.length();

                    writeFile(request, response, input, name, mimetype, doc_size);
                }
            } else {
                LOGGER.error("Can not find document. documentServerId"//
                        + documentServerId //
                        + ", doc_id: " //
                        + doc_id);
            }
        }
    }

    protected void downloadFromTemporaryDocuments(String documentServerId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DocumentWithFile document = EvectorFileUploadServlet.getTmpDocument(request, documentServerId);
        if (document != null) {
            File file = document.getDoc_file();
            String name = document.getDoc_name();
            String mimeType = document.getDoc_mime_type();
            if (file != null) {
                FileInputStream input = new FileInputStream(file);
                try {
                    int doc_file_size = (int) file.length();
                    writeFile(request, response, input, name, mimeType, doc_file_size);
                } finally {
                    input.close();
                }
            }

        } else {
            LOGGER.error("Received temporary documentServerId, which did not correspond to the actual document. documentServerId: " + documentServerId);
        }
    }

    @Override
    protected void writeFile(HttpServletRequest request, HttpServletResponse response, InputStream input, String fileName, String contentType, int size) throws IOException {
        boolean thumbnail = (request.getParameter(PARAM_THUMBNAIL) != null);
        boolean resize = (request.getParameter(PARAM_RESIZE) != null);
        if (thumbnail || resize) {
            super.writeFile(request, response, input, fileName, contentType, size);
        } else {
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Length", String.valueOf(size));
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
            int l;
            byte[] bytes = new byte[32 * 1024];
            while ((l = input.read(bytes)) != -1) {
                out.write(bytes, 0, l);
            }
        }
    }

    protected static String convertDateToFilename() {
        return convertDateToFilename(null);
    }

    protected static String convertDateToFilename(Date date) {
        if (date == null) {
            date = new Date();
        }
        return FILE_NAME_DATE_PATTERN.format(date);
    }

}
