package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventBus;

public class EventBusHolder {

    private EventBus eventBus;

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

}
