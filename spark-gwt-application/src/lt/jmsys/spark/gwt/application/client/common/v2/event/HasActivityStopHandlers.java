package lt.jmsys.spark.gwt.application.client.common.v2.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasActivityStopHandlers extends HasHandlers {

    HandlerRegistration addActivityStopHandler(ActivityStopHandler handler);
}
