package lt.jmsys.spark.gwt.application.common.client;

import lt.jmsys.spark.gwt.client.messages.MessagesByCode;


public class CommonMessageDataProvider extends lt.jmsys.spark.gwt.client.callback.CommonMessageDataProvider{
    public CommonMessageDataProvider(){
        super();
    }
    
    public CommonMessageDataProvider(MessagesByCode ... customMessages){
        super(customMessages);
    }
    
    public void setCustomMessages(MessagesByCode[] customMessages){
        this.customMessages = customMessages;
    }
}
