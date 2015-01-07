package lt.jmsys.spark.gwt.application.client.common.v2.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class ActivityStartSource implements HasActivityStartHandlers {

    HandlerManager manager = new HandlerManager(this);

    @Override
    public void fireEvent(GwtEvent<?> event) {
        manager.fireEvent(event);
    }

    @Override
    public HandlerRegistration addActivityStartHandler(ActivityStartHandler handler) {
        return manager.addHandler(ActivityStartEvent.getType(), handler);
    }

}
