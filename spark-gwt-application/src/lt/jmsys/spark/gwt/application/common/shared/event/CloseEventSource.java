package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class CloseEventSource<T> implements HasCloseHandlers<T> {

    HandlerManager manager = new HandlerManager(this);

    @Override
    public void fireEvent(GwtEvent<?> event) {
        manager.fireEvent(event);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<T> handler) {
        return manager.addHandler(CloseEvent.getType(), handler);
    }

}
