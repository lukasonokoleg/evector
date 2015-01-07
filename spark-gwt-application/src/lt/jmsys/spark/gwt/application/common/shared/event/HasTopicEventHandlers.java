package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasTopicEventHandlers<T> extends HasHandlers {

    HandlerRegistration addTopicEventHandler(String topic, TopicEventHandler<T> handler);
}
