package lt.jmsys.spark.gwt.application.client.common.v2.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class ActivityStopSource implements HasActivityStopHandlers {

    HandlerManager manager = new HandlerManager(this);

    @Override
    public void fireEvent(GwtEvent<?> event) {
        manager.fireEvent(event);
    }

    @Override
    public HandlerRegistration addActivityStopHandler(ActivityStopHandler handler) {
        return manager.addHandler(ActivityStopEvent.getType(), handler);
    }

}
