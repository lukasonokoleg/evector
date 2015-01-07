package lt.jmsys.spark.gwt.application.common.shared.event;

import java.util.List;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

public class ListValueChangeSource<E> implements HasValueChangeHandlers<List<E>> {

    private EventBus eventBus = new SimpleEventBus();

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<E>> handler) {
        return eventBus.addHandler(ValueChangeEvent.getType(), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }
}
