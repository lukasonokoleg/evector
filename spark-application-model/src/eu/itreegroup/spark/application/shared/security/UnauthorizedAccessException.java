package eu.itreegroup.spark.application.shared.security;

import java.io.Serializable;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.Spr_user_message;

@SuppressWarnings("serial")
public class UnauthorizedAccessException extends SparkBusinessException implements Serializable {

    private String message;
    private Spr_user_message[] messages;

    public UnauthorizedAccessException() {
        Spr_user_message msg = new Spr_user_message();
        msg.setCode("error.user.cannot.view");
        msg.setSeverity("E");
        messages = new Spr_user_message[] { msg };
    }

    public UnauthorizedAccessException(String message) {
        this();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public Spr_user_message[] getMessages() {
        return messages;
    }

}
