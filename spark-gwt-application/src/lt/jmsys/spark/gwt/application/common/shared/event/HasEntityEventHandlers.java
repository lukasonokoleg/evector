package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasEntityEventHandlers<T> extends HasHandlers {

    HandlerRegistration addEntityEventHandler(EntityEventHandler<T> handler);

    Class<T> getAssociatedClass();
}
