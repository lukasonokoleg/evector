package univ.evector.gwt.client.error.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import lt.jmsys.spark.bind.executor.plsql.errors.Spr_user_message;
import lt.jmsys.spark.gwt.application.common.client.CommonMessageDataProvider;
import lt.jmsys.spark.gwt.client.messages.MessagesByCode;

import com.google.gwt.core.client.GWT;

public class ErrorMessageDataProvider extends CommonMessageDataProvider {

    private static final List<MessagesByCode> errors = new ArrayList<MessagesByCode>();
    static {
        errors.add((MessagesByCode) GWT.create(ErrorMessages.class));
    }

    public ErrorMessageDataProvider() {
        setCustomMessages(null);
    }

    @Override
    public void setCustomMessages(MessagesByCode[] customMessages) {

        List<MessagesByCode> messages = new ArrayList<MessagesByCode>();
        if (null != customMessages) {
            for (int i = 0; i < customMessages.length; i++) {
                messages.add(customMessages[i]);
            }
        }
        for (MessagesByCode m : errors) {
            messages.add(m);
        }

        customMessages = messages.toArray(new MessagesByCode[messages.size()]);

        super.setCustomMessages(customMessages);
    }

    protected String customResolveMessage(Spr_user_message pMsg) {
        String result = null;
        String msg = pMsg.getCode();
        if (null != customMessages) {

            for (MessagesByCode cm : customMessages) {
                try {
                    result = cm.getMessage(msg);
                    if (null != result && !result.startsWith("???")) {
                        if (null != pMsg.getParam1()) {
                            result = result.replaceAll("\\{0\\}", pMsg.getParam1());
                        }
                        if (null != pMsg.getParam2()) {
                            result = result.replaceAll("\\{1\\}", pMsg.getParam2());
                        }
                        if (null != pMsg.getParam3()) {
                            result = result.replaceAll("\\{2\\}", pMsg.getParam3());
                        }
                        if (null != pMsg.getParam4()) {
                            result = result.replaceAll("\\{3\\}", pMsg.getParam4());
                        }
                        if (null != pMsg.getParam5()) {
                            result = result.replaceAll("\\{4\\}", pMsg.getParam5());
                        }
                        result = result.replaceAll("\\{\\d\\}", "");
                        break;
                    }
                } catch (MissingResourceException ignore) {
                }
            }

        }
        return result;
    }
}
