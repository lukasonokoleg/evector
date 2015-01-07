package univ.evector.gwt.client.application.common.client.helper;

import java.util.List;

import lt.jmsys.spark.gwt.application.common.client.helper.FileUploadHelper;
import lt.jmsys.spark.gwt.fileupload.client.FileUploadItem;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadPresenter;
import univ.evector.beans.Document;
import univ.evector.beans.helper.DocumentHelper;

public class EvectorFileUploadHelper {

    public static FileUploadPresenter<FileUploadItem> createFileUploadPresenter() {
        String downloadUrl = "download-inc-file";
        return FileUploadHelper.createFileUploadPresenter(false, downloadUrl);
    }

    public static void createFileUploadPresenterCallBack(FileUploadPresenter fileUploadPresenter, final Runnable ccb) {
        FileUploadHelper.createFileUploadPresenterCallBack(fileUploadPresenter, ccb);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void setDocumentsToUploadPresenter(FileUploadPresenter fileUploadPresenter, List<Document> documents) {

        if (fileUploadPresenter != null) {
            fileUploadPresenter.clear();
            List<FileUploadItem> items = DocumentHelper.convertToFileUploadItems(documents);
            if (items != null) {
                for (FileUploadItem item : items) {
                    fileUploadPresenter.addItem(item);
                }
            }
        }
    }

}
