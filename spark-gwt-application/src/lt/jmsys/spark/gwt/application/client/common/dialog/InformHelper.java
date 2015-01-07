package lt.jmsys.spark.gwt.application.client.common.dialog;

import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;

public class InformHelper {

    private static ConfirmationDialog dialog;

    private static Command commandOnInform;

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);

    public static void inform(String message, Command _commandOnInform) {
        if (null == dialog) {
            createDialog(null);
        }
        commandOnInform = _commandOnInform;
        dialog.setMessage(message);
        dialog.confirm();
    }

    private static void onInform() {
        commandOnInform.execute();
        commandOnInform = null;
    }

    private static void createDialog(String btnInformText) {
        if (null == dialog) {
            Button btnInform = ButtonHelper.getInstance().createOkButton(
                    btnInformText != null ? btnInformText : CC.btnOk());
            dialog = new ConfirmationDialog(null, btnInform, null) {

                public void onConfirm() {
                    onInform();
                }
            };
        }
    }
}
