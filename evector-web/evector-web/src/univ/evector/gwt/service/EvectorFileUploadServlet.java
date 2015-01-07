package univ.evector.gwt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lt.jmsys.spark.gwt.fileupload.server.FileUploadException;
import lt.jmsys.spark.gwt.fileupload.server.FileUploadServlet;

import org.apache.log4j.Logger;

import univ.evector.beans.DocumentWithFile;
import univ.evector.gwt.client.helper.EvectorDocumentHelper;

@SuppressWarnings("serial")
public class EvectorFileUploadServlet extends FileUploadServlet implements HttpSessionListener {

    private static final Logger LOGGER = Logger.getLogger(EvectorFileUploadServlet.class);

    /**
     * TEMP_DOCS_MAPPED_BY_SID - > Temporary documents mapped by session id.
     */
    protected static Map<String, Map<String, DocumentWithFile>> TEMP_DOCS_MAPPED_BY_SID = new HashMap<>();
    protected transient ThreadLocal<HttpServletRequest> perThreadRequest;

    public EvectorFileUploadServlet() {
        super();
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doPost(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doGet(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doDelete(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    protected final void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doHead(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    protected final void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doOptions(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doPut(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    protected final void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initCall(request, response);
            super.doTrace(request, response);
        } finally {
            cleanupCall();
        }
    }

    @Override
    public String uploadFile(InputStream input, final String doc_name, final int doc_size, final String doc_mite_type) throws FileUploadException, IOException {
        HttpServletRequest request = getThreadLocalRequest();
        return uploadFile(request, doc_name, doc_mite_type, input, (long) doc_size);
    }

    public static String uploadFile(HttpServletRequest request, final String doc_name, final String doc_mime_type, final InputStream input, final Long size)
            throws FileUploadException, IOException {
        String sid = getSid(request);
        LOGGER.info("Uploading file for session with sid: " + sid);
        return uploadFile(sid, doc_name, doc_mime_type, input, size);
    }

    private static String createDocumentServerId() {
        return EvectorDocumentHelper.TEMP_DOCUMENT_ID_PREFIX + UUID.randomUUID().toString();
    }

    private static String uploadFile(String sid, String doc_name, String doc_mime_type, InputStream input, Long inputSize) throws FileUploadException, IOException {
        String id = createDocumentServerId();
        DocumentWithFile document = createTmpDocument(doc_name, doc_mime_type, input, inputSize);
        getTmpDocumentsBySessionContext(sid).put(id, document);

        LOGGER.info("Succesfully uploaded new document. Id: " + id);
        return id;
    }

    private static void writeFile(File outPutFile, InputStream input) throws IOException {
        FileOutputStream out = new FileOutputStream(outPutFile);
        try {
            byte[] buff = new byte[BUF_SIZE];
            int l;
            while ((l = input.read(buff)) != -1) {
                out.write(buff, 0, l);
            }
        } finally {
            out.close();
        }
    }

    /*    public static String registerTemporaryFile(HttpServletRequest request, File file, String name, final String doc_mime_type) throws FileUploadException, IOException {
            String sid = getSid(request);
            String id = getRandomTmpDocId();
            DocumentDTO newTmpDocument = new DocumentDTO();
            newTmpDocument.setFile(file);
            newTmpDocument.setDoc_name(name);
            newTmpDocument.setDoc_mime_type(doc_mime_type);
            Map<String, DocumentDTO> documentsMappedBySid = getTmpDocumentsBySessionContext(sid);
            documentsMappedBySid.put(id, newTmpDocument);

            LOGGER.info("Succesfully registered newly uploaded document. tmpId:" + id + ", sid: " + sid);

            return id;
        }*/

    @Override
    public void deleteFile(String id, HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        String sid = getSid(request);
        if (!deleteTmpDocumentBySid(sid, id)) {
            deleteTmpDocumentBySid(null, id);
        }
    }

    public static boolean deleteTmpDocumentBySid(String sid, String id) {
        boolean retVal = false;
        Map<String, DocumentWithFile> mappedDocuments = getTmpDocumentsBySessionContext(sid);
        if (mappedDocuments.containsKey(id)) {
            retVal = true;
            DocumentWithFile document = mappedDocuments.get(id);
            if (document != null && document.getDoc_file() != null) {
                document.getDoc_file().delete();
            }
            mappedDocuments.remove(id);
        }
        return retVal;
    }

    public static File getTmpDocumentFile(HttpServletRequest request, String id) {
        DocumentWithFile document = getTmpDocument(request, id);
        File retVal = null;
        if (document != null) {
            retVal = document.getDoc_file();
        }
        return retVal;
    }

    public static DocumentWithFile getTmpDocument(HttpServletRequest request, String id) {
        String sid = getSid(request);
        DocumentWithFile retVal = getTmpDocumentBySessionContext(sid, id);
        if (retVal == null) {
            retVal = getTmpDocumentBySessionContext(null, id);
        }
        return retVal;
    }

    public static DocumentWithFile getTmpDocumentBySessionContext(String sid, String id) {
        if (id == null) {
            throw new IllegalArgumentException("Input variable id has NULL value.");
        }
        Map<String, DocumentWithFile> mappedDocuments = getTmpDocumentsBySessionContext(sid);
        DocumentWithFile document = null;
        if (mappedDocuments != null) {
            document = mappedDocuments.get(id);
        }
        return document;
    }

    protected static Map<String, DocumentWithFile> getTmpDocumentsBySessionContext(String sid) {
        synchronized (TEMP_DOCS_MAPPED_BY_SID) {
            Map<String, DocumentWithFile> tempFiles = TEMP_DOCS_MAPPED_BY_SID.get(sid);
            if (null == tempFiles) {
                tempFiles = Collections.synchronizedMap(new HashMap<String, DocumentWithFile>());
            }
            TEMP_DOCS_MAPPED_BY_SID.put(sid, tempFiles);
            // TODO if sessionId==null cleanup old files
            return tempFiles;
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionId = session.getId();
        Map<String, DocumentWithFile> documentsMappedByDocId;
        synchronized (TEMP_DOCS_MAPPED_BY_SID) {
            documentsMappedByDocId = TEMP_DOCS_MAPPED_BY_SID.get(sessionId);
        }
        if (null != documentsMappedByDocId) {
            for (DocumentWithFile document : documentsMappedByDocId.values()) {
                File file = document.getDoc_file();
                if (file != null) {
                    file.delete();
                }
            }
            synchronized (TEMP_DOCS_MAPPED_BY_SID) {
                TEMP_DOCS_MAPPED_BY_SID.remove(sessionId);
            }
        }
    }

    protected static String getSid(HttpServletRequest request) {
        HttpSession session = null != request ? request.getSession(false) : null;
        String sessionId = null != session ? session.getId() : null;
        return sessionId;
    }

    protected synchronized void initCall(HttpServletRequest request, HttpServletResponse response) {
        validateThreadLocalData();
        perThreadRequest.set(request);
    }

    protected void cleanupCall() {
        perThreadRequest.set(null);
    }

    private void validateThreadLocalData() {
        if (perThreadRequest == null) {
            perThreadRequest = new ThreadLocal<HttpServletRequest>();
        }
    }

    protected final HttpServletRequest getThreadLocalRequest() {
        synchronized (this) {
            validateThreadLocalData();
            return perThreadRequest.get();
        }
    }

    private static DocumentWithFile createTmpDocument(String doc_name, String doc_mime_type, InputStream input, Long size) throws IOException {
        File outPutFile = craeteTmpFile();
        DocumentWithFile retVal = DocumentWithFile.newInstance(size, doc_name, doc_mime_type, outPutFile);
        writeFile(outPutFile, input);
        outPutFile.deleteOnExit();
        return retVal;
    }

    private static File craeteTmpFile() throws IOException {
        String fileNamePrefix = EvectorDocumentHelper.DEFAULT_TEMP_FILE_PREFIX;
        String fileNameSuffix = EvectorDocumentHelper.DEFAULT_TEMP_FILE_SUFFIX;
        File retVal = File.createTempFile(fileNamePrefix, fileNameSuffix);
        retVal.deleteOnExit();
        return retVal;
    }

}
