package lt.jmsys.spark.gwt.application.common.shared.messaging;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class MessageEventSource<E extends Message> implements HasMessageEventHandlers<E> {

    private EventBus eventBus;
    private Class<E> associatedClass;
    private String category;

    public MessageEventSource(EventBus eventBus, Class<E> associatedClass) {
        this(eventBus, associatedClass, null);
    }

    public MessageEventSource(EventBus eventBus, Class<E> associatedClass, String category) {
        this.eventBus = eventBus;
        this.associatedClass = associatedClass;
        this.category = category;
    }

    @Override
    public HandlerRegistration addMessageEventHandler(MessageEventHandler<E> handler) {
        return eventBus.addHandler(MessageEvent.getType(associatedClass, category), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    @Override
    public Class<E> getAssociatedClass() {
        return associatedClass;
    }

    @Override
    public String getCategory() {
        return category;
    }

}
