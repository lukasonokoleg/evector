package lt.jmsys.spark.gwt.application.client.common.dialog;

import java.util.Collection;

import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.Message;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.PopupPanel;

public class ConfirmHelper {

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);

    private static ConfirmationDialog dialog;

    private static Command commandOnConfirm;
    private static Command commandOnCancel;
    private static Collection<Message> messages;

    public static void error(String message, Command confirmCommand, Throwable t) {
        inform(null, message, confirmCommand);
        new CommonProgressShowingCallback<Void>(dialog.getMessageContainer()) {

            @Override
            protected void call() {

            }

        }.onFailure(t);
    }

    public static void inform(String message, Command confirmCommand) {
        inform(null, message, confirmCommand);
    }

    public static void inform(SafeHtml messageAsSafeHtml, Command confirmCommand) {
        inform(messageAsSafeHtml, null, confirmCommand);
    }

    public static void inform(SafeHtml messageAsSafeHtml, String message, Command confirmCommand) {
        if (null == dialog) {
            createInfoDialog();
        }

        commandOnConfirm = confirmCommand != null ? confirmCommand : new Command() {

            @Override
            public void execute() {
            }
        };
        commandOnCancel = commandOnConfirm;

        dialog.setFocusConfirmButton(true);
        if (message != null) {
            dialog.setMessage(message);
        }
        if (messageAsSafeHtml != null) {
            dialog.setMesageAsSafeHtml(messageAsSafeHtml);
        }
        dialog.confirm();
    }

    private static void createInfoDialog() {
        if (null == dialog) {
            dialog = new ConfirmationDialog(null, CC.btnOk()) {

                @Override
                public void onConfirm() {
                    ConfirmHelper.onConfirm();
                }

            };
            dialog.addCloseHandler(new CloseHandler<PopupPanel>() {

                @Override
                public void onClose(CloseEvent<PopupPanel> event) {
                    if (!wasOkClicked()) {
                        onCancel();
                    }
                }
            });
        }
    }

    public static void confirm(String message, Command confirmCommand) {
        confirm(message, confirmCommand, null);
    }

    public static void confirm(String message, Command confirmCommand, boolean focusConfirmButton, Collection<Message> messages) {
        confirm(null, message, confirmCommand, null, focusConfirmButton, messages);
    }

    public static void confirm(SafeHtml messageAsSafeHtml, Command confirmCommand) {
        confirm(messageAsSafeHtml, null, confirmCommand, null, true, null);
    }

    public static void confirm(SafeHtml messageAsSafeHtml, Command confirmCommand, Command cancelCommand) {
        confirm(messageAsSafeHtml, null, confirmCommand, cancelCommand, true, null);
    }

    public static void confirm(String message, Command confirmCommand, boolean focusConfirmButton) {
        confirm(message, confirmCommand, null, focusConfirmButton, null);
    }

    public static void confirm(String message, Command confirmCommand, Command cancelCommand) {
        confirm(message, confirmCommand, cancelCommand, true, null);
    }

    public static void confirm(String message, Command confirmCommand, Command cancelCommand, boolean focusConfirmButton, Collection<Message> messages) {
        confirm(null, message, confirmCommand, cancelCommand, focusConfirmButton, messages);
    }

    public static void confirm(SafeHtml messageAsSafeHtml, String message, Command confirmCommand, Command cancelCommand, boolean focusConfirmButton, Collection<Message> messages) {
        commandOnConfirm = confirmCommand;
        commandOnCancel = cancelCommand;

        ConfirmHelper.messages = messages;

        if (null == dialog) {
            createDialog();
        }
        dialog.setFocusConfirmButton(focusConfirmButton);
        if (message != null) {
            dialog.setMessage(message);
        }
        if (messageAsSafeHtml != null) {
            dialog.setMesageAsSafeHtml(messageAsSafeHtml);
        }
        dialog.confirm();
    }

    private static void createDialog() {
        if (null == dialog) {
            dialog = new ConfirmationDialog(null) {

                @Override
                public void onConfirm() {
                    ConfirmHelper.onConfirm();
                }

                @Override
                public void onCancel() {
                    ConfirmHelper.onCancel();
                }

                @Override
                protected void show() {
                    if (null != messages) {
                        for (Message m : messages) {
                            getMessageContainer().addMessage(m);
                        }
                        getMessageContainer().show();
                    }
                    super.show();
                }
            };
        }
    }

    private static boolean wasOkClicked() {
        return commandOnConfirm == null;
    }

    private static void onConfirm() {
        commandOnConfirm.execute();
        commandOnConfirm = null;
    }

    private static void onCancel() {
        if (commandOnCancel != null) {
            commandOnCancel.execute();
            commandOnCancel = null;
        }
    }

}
