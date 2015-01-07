package lt.jmsys.spark.gwt.application.common.client.application;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class ApplicationEventBus {

    private static final EventBus eventBus = new SimpleEventBus();

    public static EventBus getEventBus() {
        return eventBus;
    }

}
