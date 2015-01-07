package lt.jmsys.spark.gwt.application.client.common.v2.view;

import lt.jmsys.spark.gwt.client.ui.message.MessagePanel;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;

public class FeedbackHandler {

    MessagePanel messageContainer;

    public void init(BaseFormView<?> form) {
        messageContainer = form.getMessageContainer();
    }

    protected MessagePanel getMessageContainer() {
        return messageContainer;
    }

    public void showFeedback(MessageType type, String message) {
        getMessageContainer().clear();
        getMessageContainer().addMessage(type, message);
        if (MessageType.SUCCESS.equals(type)) {
            getMessageContainer().show(null, 6000);
        } else {
            getMessageContainer().show(null);
        }
    }
}
