package eu.itreegroup.spark.application.mail;

import java.util.Arrays;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailService {

    private static final String MAIL_SMTP_PROP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PROP_HOST_VALUE = "localhost";

    private static final String MAIL_SMTP_PROP_CONNECTION_TIME_OUT = "mail.smtp.connectiontimeout";
    private static final String MAIL_SMTP_PROP_CONNECTION_TIME_OUT_VALUE = "30000";

    private static final String MAIL_SMTP_PROP_TIME_OUT = "mail.smtp.timeout";
    private static final String MAIL_SMTP_PROP_TIME_OUT_VALUE = "30000";

    /*    private static final String MAIL_SMTP_PROP_LOCAL_HOST = "mail.smtp.localhost";
        private static final String MAIL_SMTP_PROP_LOCAL_HOST_VALUE = "alcs";*/

    private static final Logger log = Logger.getLogger(MailService.class);

    // SettingsService settingsService;
    MailMessageBuilderFactory builderFactory;

    public MailService() {

    }

    public void sendMessage(MailMessageRequest request) throws MessagingException {
        MailMessageBuilder builder = getMessageBuilder(request);

        Session mailSession = createMailSession();
        MimeMessage message = builder.build(request, mailSession);
        send(message);

    }

    protected MailMessageBuilder getMessageBuilder(MailMessageRequest request) throws MessagingException {
        return builderFactory.getBuilder(request);
    }

    protected Session createMailSession() {
        Properties properties = new Properties();
        properties.setProperty(MAIL_SMTP_PROP_HOST, MAIL_SMTP_PROP_HOST_VALUE);
        properties.setProperty(MAIL_SMTP_PROP_CONNECTION_TIME_OUT, MAIL_SMTP_PROP_CONNECTION_TIME_OUT_VALUE);
        properties.setProperty(MAIL_SMTP_PROP_TIME_OUT, MAIL_SMTP_PROP_TIME_OUT_VALUE);
        // properties.setProperty(MAIL_SMTP_PROP_LOCAL_HOST, MAIL_SMTP_PROP_LOCAL_HOST_VALUE);
        Session session = Session.getDefaultInstance(properties);
        return session;
    }

    protected void send(javax.mail.Message message) throws MessagingException {
        log.debug("Send message - " + messageInfoString(message));
        long start = System.currentTimeMillis();
        Transport.send(message);
        long end = System.currentTimeMillis();
        log.debug("Message sent. Duartion = " + (end - start) + "ms");
    }

    private static String messageInfoString(javax.mail.Message message) {
        try {
            StringBuilder info = new StringBuilder();
            Address[] from = message.getFrom();
            info.append("from = " + (null != from ? Arrays.toString(from) : null));
            Address[] replyTo = message.getReplyTo();
            info.append(", replyTo = " + (null != replyTo ? Arrays.toString(replyTo) : null));
            info.append(", subject = " + message.getSubject());
            Address[] recipients = message.getRecipients(RecipientType.TO);
            info.append(", to = " + (null != recipients ? Arrays.toString(recipients) : null));
            recipients = message.getRecipients(RecipientType.CC);
            info.append(", cc = " + (null != recipients ? Arrays.toString(recipients) : null));
            recipients = message.getRecipients(RecipientType.BCC);
            info.append(", bcc = " + (null != recipients ? Arrays.toString(recipients) : null));
            return info.toString();
        } catch (MessagingException e) {
            return message.toString();
        }
    }

    public void setBuilderFactory(MailMessageBuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }
}
