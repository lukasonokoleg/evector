package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

public class ValueClickSource<E> implements HasValueClickHandlers<E> {

    private EventBus eventBus = new SimpleEventBus();
    private Class<E> associatedClass;

    public ValueClickSource(Class<E> associatedClass) {
        this.associatedClass = associatedClass;
    }

    @Override
    public HandlerRegistration addValueClickEventHandler(ValueClickHandler<E> handler) {
        return eventBus.addHandler(ValueClickEvent.getType(associatedClass), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    @Override
    public Class<E> getAssociatedClass() {
        return associatedClass;
    }

    public void click(E value) {
        ValueClickEvent.fire(this, value);
    }

}
