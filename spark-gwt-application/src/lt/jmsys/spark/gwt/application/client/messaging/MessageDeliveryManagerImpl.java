package lt.jmsys.spark.gwt.application.client.messaging;

import java.util.ArrayList;

import lt.jmsys.spark.gwt.application.client.common.service.SessionService;
import lt.jmsys.spark.gwt.application.client.common.service.SessionServiceAsync;
import lt.jmsys.spark.gwt.application.common.client.messaging.MessageDeliveryManager;
import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class MessageDeliveryManagerImpl implements MessageDeliveryManager{
    
    private EventBus eventBus;
    private boolean delivering;
    
    public MessageDeliveryManagerImpl(EventBus eventBus){
        this.eventBus = eventBus;
    }
    
    @Override
    public void deliverMessages(){
        if (!delivering){
            delivering = true;
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                
                @Override
                public void execute() {
                    SessionServiceAsync service = GWT.create(SessionService.class);
                    service.receiveMessages(new AsyncCallback<ArrayList<Message>>() {
                        
                        @Override
                        public void onSuccess(ArrayList<Message> result) {
                            try{
                                if (null != result){
                                    for(Message message : result){
                                        message.fireMessageEvent(eventBus);
                                    }
                                }
                            }finally{
                                delivering = false;
                            }
                        }
                        
                        @Override
                        public void onFailure(Throwable caught) {
                            delivering = false;
                        }
                    });                    
                }
            });
        }
    }
}
