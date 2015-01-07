package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasValueClickHandlers<T> extends HasHandlers {

    HandlerRegistration addValueClickEventHandler(ValueClickHandler<T> handler);

    Class<T> getAssociatedClass();
}
