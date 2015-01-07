package lt.jmsys.spark.gwt.application.common.shared.messaging;

import com.google.gwt.event.shared.EventHandler;

public interface MessageEventHandler<E extends Message> extends EventHandler {

    void onMessage(MessageEvent<E> event);

}
