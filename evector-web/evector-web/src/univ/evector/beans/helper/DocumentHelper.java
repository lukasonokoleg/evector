package univ.evector.beans.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.fileupload.client.FileUploadItem;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadPresenter;
import univ.evector.beans.Document;
import univ.evector.gwt.client.helper.EvectorDocumentHelper;

public class DocumentHelper {

    public static void ensureDocDate(Document document) {
        if (document != null) {

            if (!hasDocDate(document)) {
                document.setDoc_date(new Date());
            }

        }
    }

    public static boolean hasDocDate(Document document) {
        boolean retVal = document != null && document.getDoc_date() != null;
        return retVal;
    }

    /**
     * Convert file upload item ({@link FileUploadItem}) list from {@link FileUploadPresenter} to {@link Document} list
     * @param items
     * @return
     */
    public static List<Document> convertToDocuments(List<FileUploadItem> items) {
        if (ConversionHelper.isEmpty(items)) {
            return null;
        }
        List<Document> retVal = new ArrayList<Document>();
        for (FileUploadItem item : items) {
            if (item != null) {
                Document document = convertToDocument(item);
                retVal.add(document);
            }
        }
        return retVal;
    }

    private static Document convertToDocument(FileUploadItem item) {
        Document retVal = null;

        if (item != null) {
            retVal = new Document();

            Date docDate = item.getDate();
            String docName = item.getName();
            String docMimeType = item.getMimeType();
            String docTmpId = item.getId();
            int docSize = item.getSize();

            retVal.setDoc_date(docDate);
            retVal.setDoc_name(docName);
            retVal.setDoc_mime_type(docMimeType);
            retVal.setDoc_tmp_id(docTmpId);
            retVal.setDoc_size((long) docSize);

        }

        return retVal;
    }

    public static List<FileUploadItem> convertToFileUploadItems(List<Document> documents) {
        List<FileUploadItem> retVal = new ArrayList<>();
        if (documents != null) {
            for (Document document : documents) {
                if (document != null) {
                    FileUploadItem item = convertToFileUploadItem(document);
                    retVal.add(item);
                }
            }
        }
        return retVal;
    }

    private static FileUploadItem convertToFileUploadItem(Document document) {
        FileUploadItem retVal = null;
        if (document != null) {
            retVal = new FileUploadItem();

            Date date = document.getDoc_date();
            String id = EvectorDocumentHelper.constructServerDocumentId(document);
            String mimeType = document.getDoc_mime_type();
            String name = document.getDoc_name();
            String notes = null;
            String title = document.getDoc_name();

            retVal.setDate(date);
            retVal.setId(id);
            retVal.setMimeType(mimeType);
            retVal.setName(name);
            retVal.setNotes(notes);

            retVal.setSize(document.getDoc_size() != null ? document.getDoc_size().intValue() : 0);

            retVal.setTitle(title);

        }
        return retVal;
    }

}
