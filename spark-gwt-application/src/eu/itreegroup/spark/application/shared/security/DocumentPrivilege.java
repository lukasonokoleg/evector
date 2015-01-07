package eu.itreegroup.spark.application.shared.security;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DocumentPrivilege implements Serializable {

    private DocumentFunction function;

    private boolean enabled;

    private String message;

    private String messageCode;

    public DocumentPrivilege() {

    }

    public DocumentPrivilege(DocumentFunction function, boolean enabled) {
        this(function, enabled, null, null);
    }

    public DocumentPrivilege(DocumentFunction function, boolean enabled, String message, String messageCode) {
        this.function = function;
        this.enabled = enabled;
        this.message = message;
        this.messageCode = messageCode;
    }

    public DocumentFunction getFunction() {
        return function;
    }

    public void setFunction(DocumentFunction function) {
        this.function = function;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public static DocumentPrivilege newInstance(DocumentFunction function, boolean enabled) {
        DocumentPrivilege retVal = new DocumentPrivilege(function, enabled);
        return retVal;
    }

    public static DocumentPrivilege newEnabledInstance(DocumentFunction function) {
        DocumentPrivilege retVal = newEnabledInstance(function, null, null);
        return retVal;
    }

    public static DocumentPrivilege newEnabledInstance(DocumentFunction function, String messageCode) {
        DocumentPrivilege retVal = newEnabledInstance(function, messageCode, null);
        return retVal;
    }

    public static DocumentPrivilege newEnabledInstance(DocumentFunction function, String messageCode, String message) {
        DocumentPrivilege retVal = new DocumentPrivilege();
        retVal.setEnabled(true);
        retVal.setFunction(function);
        retVal.setMessage(message);
        retVal.setMessageCode(messageCode);
        return retVal;
    }

    public static DocumentPrivilege newDisabledInstance(DocumentFunction function) {
        DocumentPrivilege retVal = newDisabledInstance(function, null, null);
        return retVal;
    }

    public static DocumentPrivilege newDisabledInstance(DocumentFunction function, String messageCode) {
        DocumentPrivilege retVal = newDisabledInstance(function, messageCode, null);
        return retVal;
    }

    public static DocumentPrivilege newDisabledInstance(DocumentFunction function, String messageCode, String message) {
        DocumentPrivilege retVal = new DocumentPrivilege();
        retVal.setEnabled(false);
        retVal.setFunction(function);
        retVal.setMessage(message);
        retVal.setMessageCode(messageCode);
        return retVal;
    }

    @Override
    public String toString() {
        return "DocumentPrivilege [function=" + function + ", enabled=" + enabled + ", message=" + message + ", messageCode=" + messageCode + "]";
    }

}
