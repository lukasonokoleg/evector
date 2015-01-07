package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface TopicEventHandler<E> extends EventHandler {

    void onMessage(TopicEvent<E> message);

}
