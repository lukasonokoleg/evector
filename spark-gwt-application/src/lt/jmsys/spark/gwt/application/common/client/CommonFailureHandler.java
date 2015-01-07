package lt.jmsys.spark.gwt.application.common.client;

import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.Spr_user_message;
import lt.jmsys.spark.gwt.application.client.common.dialog.ConfirmationDialog;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.client.callback.CommonAsyncCallbackView;
import lt.jmsys.spark.gwt.client.ui.message.Message;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;
import lt.jmsys.spark.gwt.user.client.ui.core.validator.MessageDataProvider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.StatusCodeException;

public class CommonFailureHandler extends lt.jmsys.spark.gwt.client.callback.CommonFailureHandler {

    private static final CommonConstants C = GWT.create(CommonConstants.class);

    public CommonFailureHandler(MessageDataProvider pMdp, MessageContainer pMsgc, CommonAsyncCallbackView pCpsc) {
        super(pMdp, pMsgc, pCpsc);
    }

    @Override
    protected boolean handleSystemException(Throwable caught) {
        if (caught instanceof StatusCodeException) {
            StatusCodeException status = (StatusCodeException) caught;
            if (status.getStatusCode() == 409 && status.getEncodedResponse() != null && status.getEncodedResponse().startsWith("VERSION_UPDATE")) {
                ConfirmationDialog dialog = new ConfirmationDialog(C.warnVersionUpdate()) {

                    @Override
                    public void onConfirm() {
                        super.onConfirm();
                        Location.reload();
                    }

                };
                dialog.confirm();
                return true;
            }
        }
        return super.handleSystemException(caught);
    }

    @Override
    protected String getMessageText(Spr_user_message m) {
        String text = super.getMessageText(m);
        if (text != null && text.startsWith("???") && text.endsWith("???")) {
            text = null;
        }
        if (text == null || text.trim().length() == 0) {
            text = "(" + m.getCode() + ") " + m.getDefault_text();
        }
        return text;
    }

    private static CommonFailureHandler instance;
    static {
        instance = new CommonFailureHandler((CommonMessageDataProvider) GWT.create(CommonMessageDataProvider.class), null, null);
    }

    public static MessageType getType(String severity) {
        MessageType type = null;
        if ("E".equalsIgnoreCase(severity)) {
            type = MessageType.ERROR;
        } else if ("W".equalsIgnoreCase(severity)) {
            type = MessageType.WARNING;
        } else if ("I".equalsIgnoreCase(severity)) {
            type = MessageType.INFO;
        } else if ("F".equalsIgnoreCase(severity)) {
            type = MessageType.FAIL;
        } else if ("S".equalsIgnoreCase(severity)) {
            type = MessageType.SUCCESS;
        } else if ("U".equalsIgnoreCase(severity)) {
            type = MessageType.UNAVAILABLE;
        } else if ("F".equalsIgnoreCase(severity)) {
            type = MessageType.SYSTEM_ERROR;
        }
        return type;
    }

    public static Message convertMessage(Spr_user_message message, MessageDataProvider messageDataProvider) {
        final MessageType type = getType(message.getSeverity());
        String text = null != messageDataProvider ? messageDataProvider.getMessageText(message) : null;
        if (text != null && text.startsWith("???") && text.endsWith("???")) {
            text = null;
        }
        text = text == null ? instance.getMessageText(message) : text;
        Message msg = new Message() {

            public MessageType getType() {
                return type;
            }

        };

        msg.setMessage(text);
        return msg;
    }

    public static List<Message> convertMessages(Spr_user_message[] messages, MessageDataProvider messageDataProvider) {
        List<Message> messageList = null;
        if (null != messages) {
            messageList = new ArrayList<Message>();
            for (Spr_user_message m : messages) {
                messageList.add(convertMessage(m, messageDataProvider));
            }
        }
        return messageList;
    }

}
