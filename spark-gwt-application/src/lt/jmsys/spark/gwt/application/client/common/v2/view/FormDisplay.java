package lt.jmsys.spark.gwt.application.client.common.v2.view;

import lt.jmsys.spark.gwt.application.client.view.FormView.ButtonBar;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;
import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public interface FormDisplay<T> extends Display<T> {

    MessageContainer getMessageContainer();

    ButtonBar getButtonBar();

    void showSaveFeedback();

    void showSaveFeedback(String message);

    /**
     * showFeedback(type)
     * showFeedback(type, message)
     * showFeedback(type, relativeToWidget)
     * showFeedback(type, message, relativeToWidget)
     */

    void showFeedback(MessageType type, String message);

    void setPrivileges(FormPrivileges privileges);

    String getFormCaption();

    boolean isButtonsContractSupported();

    void setContext(FormDisplayContext context);

    FormPrivileges getPrivileges();

}
