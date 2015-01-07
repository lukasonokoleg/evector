package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class EntityEventSource<E> implements HasEntityEventHandlers<E> {

    private EventBus eventBus;
    private Class<E> associatedClass;

    public EntityEventSource(Class<E> associatedClass) {
        this.associatedClass = associatedClass;
    }

    @Override
    public HandlerRegistration addEntityEventHandler(EntityEventHandler<E> handler) {
        return eventBus.addHandler(EntityEvent.getType(associatedClass), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public Class<E> getAssociatedClass() {
        return associatedClass;
    }

}
