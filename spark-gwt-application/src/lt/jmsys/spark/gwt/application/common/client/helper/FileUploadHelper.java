package lt.jmsys.spark.gwt.application.common.client.helper;

import lt.jmsys.spark.gwt.client.callback.CommonProgressShowingCallback.CommonProcessIndicator;
import lt.jmsys.spark.gwt.fileupload.client.FileUploadItem;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadDisplay;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadItemFactory;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadPresenter;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadPresenter.FileUploadPresenterCallBack;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploaderSettings;
import lt.jmsys.spark.gwt.fileupload.client.v2.view.V2FileUploadView;

import com.google.gwt.core.client.GWT;

public class FileUploadHelper {

    public static void createFileUploadPresenterCallBack(FileUploadPresenter fileUploadPresenter, final Runnable ccb) {
        // to prevent bug : situation : when big file is uploading and user
        // press save button
        final CommonProcessIndicator commonProcessIndicator = new CommonProcessIndicator();
        fileUploadPresenter.onAllUploadDone(new FileUploadPresenterCallBack() {

            public void onSuccess() {
                ccb.run();
            }

            @Override
            public void hideProcessIndicator() {
                commonProcessIndicator.hide();
            }

            @Override
            public void showProcessIndicator() {
                commonProcessIndicator.show();
            }
        });
    }

    public static <E extends FileUploadItem> FileUploadPresenter<E> createFileUploadPresenter(FileUploadItemFactory<E> itemFactory, FileUploadDisplay<E> fileUploadView,
            String downloadUrl) {
        FileUploadPresenter<E> fileUploadPresenter = new FileUploadPresenter<E>(itemFactory, fileUploadView);
        fileUploadPresenter.setDownloadUrl(downloadUrl);
        return fileUploadPresenter;
    }

    public static FileUploadPresenter<FileUploadItem> createFileUploadPresenter(String downloadUrl) {
        boolean showNotes = true;
        return createFileUploadPresenter(showNotes, downloadUrl);
    }

    public static FileUploadPresenter<FileUploadItem> createFileUploadPresenter(boolean showNotes, String downloadUrl) {
        V2FileUploadView<FileUploadItem> fileUploadView = GWT.create(V2FileUploadView.class);
        fileUploadView.setNotesShown(showNotes);
        return createFileUploadPresenter(new FileUploadItemFactory<FileUploadItem>() {

            @Override
            public FileUploadItem createItem() {
                return new FileUploadItem();
            }
        }, fileUploadView, downloadUrl);
    }

    public static void initDefaults() {
        FileUploaderSettings.getDefaults().setMaxFileSize(10 * 1000 * 1000);
    }

}
