package univ.evector.gwt.client.helper;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.Document;

public class EvectorDocumentHelper {

    public static final String TEMP_DOCUMENT_ID_PREFIX = "fid:";

    public static String DEFAULT_TEMP_FILE_PREFIX = "file-upload";
    public static String DEFAULT_TEMP_FILE_SUFFIX = "tmp";

    private static String SERVER_DOCUMENT_ID_DELIMITER = ":";
    private static String SERVER_DOCUMENT_ID_DELIMITER_REGEX = "\\:";
    public static String SERVER_DOCUMENT_ID_PREFIX = "PREF_";

    private static String BASE_DOWNLOAD_URL = "download-inc-file?";

    private static String URL_PARAM_DELIMITER = "&";
    private static String URL_PARAM_ID = "id=";

    private static int DOC_ID_INDEX = 1;

    private static String getDocumentDBId(String docServerId, int index) {
        String retVal = null;
        if (docServerId != null) {
            String[] properties = docServerId.split(SERVER_DOCUMENT_ID_DELIMITER_REGEX);
            if (properties != null && properties.length > index) {
                retVal = properties[index];
            }
        }
        return retVal;
    }

    public static String getDB_doc_id(String documentServerId) {
        String retVal = getDocumentDBId(documentServerId, DOC_ID_INDEX);
        return retVal;
    }

    public static boolean isTemporaryServerId(String documentServerId) {
        boolean retVal = false;
        if (!ConversionHelper.isEmpty(documentServerId)) {
            retVal = documentServerId.startsWith(TEMP_DOCUMENT_ID_PREFIX);
        }
        return retVal;
    }

    public static String constructDocumentDownloadUrl(Document document) {
        StringBuilder builder = new StringBuilder();
        String serverDocId = constructServerDocumentId(document);
        builder.append(BASE_DOWNLOAD_URL);
        builder.append(URL_PARAM_ID);
        builder.append(serverDocId);
        return builder.toString();
    }

    public static String constructServerDocumentId(Document document) {
        StringBuilder builder = new StringBuilder();

        Long id = document.getDoc_id();

        builder.append(SERVER_DOCUMENT_ID_PREFIX);
        builder.append(SERVER_DOCUMENT_ID_DELIMITER);
        builder.append(id);

        return builder.toString();
    }

}
