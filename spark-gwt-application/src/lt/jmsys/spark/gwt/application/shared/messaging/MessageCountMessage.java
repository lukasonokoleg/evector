package lt.jmsys.spark.gwt.application.shared.messaging;

import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;
import lt.jmsys.spark.gwt.application.common.shared.messaging.MessageEvent;
import lt.jmsys.spark.gwt.application.common.shared.messaging.MessageEventSource;

import com.google.gwt.event.shared.EventBus;

public class MessageCountMessage implements Message {

    private static final long serialVersionUID = 1L;

    private int unreadMessages;
    
    
    public MessageCountMessage() {
    }
    
    public MessageCountMessage(int unreadMessages){
        this.unreadMessages = unreadMessages;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
    
    @Override
    public void fireMessageEvent(EventBus eventBus) {
        MessageEventSource<MessageCountMessage> source = new MessageEventSource<MessageCountMessage>(eventBus, MessageCountMessage.class);
        MessageEvent.fire(source, this);
    }
    
    @Override
    public String getMessageTag() {
        return MessageCountMessage.class.getName();
    }

}
