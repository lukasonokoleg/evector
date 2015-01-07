package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface ValueClickHandler<E> extends EventHandler {

    void onClick(ValueClickEvent<E> event);
}
