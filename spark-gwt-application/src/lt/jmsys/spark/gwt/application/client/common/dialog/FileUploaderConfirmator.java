package lt.jmsys.spark.gwt.application.client.common.dialog;

import lt.jmsys.spark.gwt.fileupload.client.presenter.Confirmator;
import lt.jmsys.spark.gwt.fileupload.client.presenter.FileUploadPresenter.FileUploadItemDisplay;

import com.google.gwt.user.client.Command;

public class FileUploaderConfirmator extends Confirmator {

    public void confirmFileDelete(FileUploadItemDisplay<?> itemView, String defaultMessage, Command command) {
        ConfirmHelper.confirm(defaultMessage, command);
    }
}
