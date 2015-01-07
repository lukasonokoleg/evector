package lt.jmsys.spark.gwt.application.common.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface EntityEventHandler<E> extends EventHandler {

    void afterCreate(EntityEvent<E> event);

    void afterUpdate(EntityEvent<E> event);

    void afterDelete(EntityEvent<E> event);

    void afterView(EntityEvent<E> event);
    
    void onActionRequest(EntityEvent<E> event);
}
