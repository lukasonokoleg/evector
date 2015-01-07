package eu.itreegroup.spark.application.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public interface MailMessageBuilder {

    MimeMessage build(MailMessageRequest request, Session mailSession) throws MessagingException;
}
