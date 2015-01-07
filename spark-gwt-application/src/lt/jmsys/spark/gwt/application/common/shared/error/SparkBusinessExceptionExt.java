package lt.jmsys.spark.gwt.application.common.shared.error;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.Spr_user_message;

public class SparkBusinessExceptionExt extends SparkBusinessException {

    private static final long serialVersionUID = 1L;
    private Spr_user_message[] messages;

    public SparkBusinessExceptionExt() {

    }

    public SparkBusinessExceptionExt(Spr_user_message[] messages) {
        this.messages = messages;
    }

    public Spr_user_message[] getMessages() {
        return messages;
    }

    public boolean hasMessages() {
        if (messages != null && messages.length > 0) {
            return true;
        } else {
            return false;
        }
    }
}
