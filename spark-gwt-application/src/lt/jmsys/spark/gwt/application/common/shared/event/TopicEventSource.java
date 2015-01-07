package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class TopicEventSource<E> implements HasTopicEventHandlers<E> {

    private EventBus eventBus;

    public TopicEventSource(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public HandlerRegistration addTopicEventHandler(String topic, TopicEventHandler<E> handler) {
        return eventBus.addHandler(TopicEvent.getType(topic), handler);
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

}
