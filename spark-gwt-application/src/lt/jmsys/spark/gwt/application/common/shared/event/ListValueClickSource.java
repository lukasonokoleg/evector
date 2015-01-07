package lt.jmsys.spark.gwt.application.common.shared.event;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

public class ListValueClickSource<E> implements HasValueClickHandlers<List<E>> {

    private EventBus eventBus = new SimpleEventBus();
    private Class<E> associatedClass;

    public ListValueClickSource(Class<E> associatedClass) {
        this.associatedClass = associatedClass;
    }

    @Override
    public HandlerRegistration addValueClickEventHandler(ValueClickHandler<List<E>> handler) {
        return eventBus.addHandler(ValueClickEvent.getType(associatedClass), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<List<E>> getAssociatedClass() {
        return (Class<List<E>>) associatedClass;
    }

    public void click(List<E> value) {
        ValueClickEvent.fire(this, value);
    }

}
