package lt.jmsys.spark.gwt.application.client.common.dialog;

public interface ConfirmationCallback {

    void onConfirm(ConfirmationDialog dialog, String reasonCode, String text);

    void onCancel();
}
