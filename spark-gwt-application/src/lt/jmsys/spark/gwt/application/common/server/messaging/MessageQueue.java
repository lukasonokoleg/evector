package lt.jmsys.spark.gwt.application.common.server.messaging;

import com.google.gwt.event.shared.HandlerRegistration;

import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;


public interface MessageQueue {
    int size();
    Message poll();
    void add(Message message);
    HandlerRegistration addQueueListener(QueueListener listener);
}
