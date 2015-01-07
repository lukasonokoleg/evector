package lt.jmsys.spark.gwt.application.client.application.resource;

import com.google.gwt.i18n.client.Messages;

public interface ApplicationConstants extends Messages {

    String linkUser();

    String linkMessages();

    String linkSettings();

    String linkLogout();

    String linkHelp();

    String hintMessages(int unreadCount);

    String msgSessionExpired();


}