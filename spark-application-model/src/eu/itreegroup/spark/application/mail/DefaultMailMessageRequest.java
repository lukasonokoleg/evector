package eu.itreegroup.spark.application.mail;

public class DefaultMailMessageRequest implements MailMessageRequest {

    private MailMessageCode code;

    public DefaultMailMessageRequest(MailMessageCode code) {
        this.code = code;
    }

    @Override
    public MailMessageCode getCode() {
        return code;
    }
}
