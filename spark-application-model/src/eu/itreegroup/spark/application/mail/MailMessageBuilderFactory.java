package eu.itreegroup.spark.application.mail;

import javax.mail.MessagingException;

public interface MailMessageBuilderFactory {

    MailMessageBuilder getBuilder(MailMessageRequest request) throws MessagingException;
}
