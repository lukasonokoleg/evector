package lt.jmsys.spark.gwt.application.common.shared.messaging;

import java.io.Serializable;

import com.google.gwt.event.shared.EventBus;


public interface Message extends Serializable{
    
    /**
     * If message tag is not null, only latest message in queue with same message tag should be delivered. 
     * @return
     */
    String getMessageTag();
    void fireMessageEvent(EventBus eventBus);
}
