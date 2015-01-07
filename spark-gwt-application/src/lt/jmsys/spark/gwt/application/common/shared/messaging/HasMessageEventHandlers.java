package lt.jmsys.spark.gwt.application.common.shared.messaging;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasMessageEventHandlers<T extends Message> extends HasHandlers {

    HandlerRegistration addMessageEventHandler(MessageEventHandler<T> handler);

    Class<T> getAssociatedClass();
    
    String getCategory();
}
