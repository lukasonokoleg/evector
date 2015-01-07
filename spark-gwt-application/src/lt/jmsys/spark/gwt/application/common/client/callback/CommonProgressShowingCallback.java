package lt.jmsys.spark.gwt.application.common.client.callback;

import com.google.gwt.core.client.GWT;

import lt.jmsys.spark.gwt.application.common.client.CommonFailureHandler;
import lt.jmsys.spark.gwt.application.common.client.CommonMessageDataProvider;
import lt.jmsys.spark.gwt.client.callback.FailureHandler;
import lt.jmsys.spark.gwt.client.callback.CommonProgressShowingCallback.CommonProcessIndicator;
import lt.jmsys.spark.gwt.client.callback.CommonProgressShowingCallback.CommonProcessIndicatorPresenter;
import lt.jmsys.spark.gwt.client.messages.MessagesByCode;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.user.client.ui.ProcessIndicatorPresenter;
import lt.jmsys.spark.gwt.user.client.ui.core.validator.MessageDataProvider;

public abstract class CommonProgressShowingCallback<T> extends lt.jmsys.spark.gwt.client.callback.CommonProgressShowingCallback<T> {

    public CommonProgressShowingCallback(MessageContainer pMsgc, MessagesByCode ... customMessages) {
        this((CommonMessageDataProvider)GWT.create(CommonMessageDataProvider.class), pMsgc, customMessages);
        
    }
    
    public CommonProgressShowingCallback(MessageContainer pMsgc) {
        this((CommonMessageDataProvider)GWT.create(CommonMessageDataProvider.class), pMsgc);
    }
    
    public CommonProgressShowingCallback(MessageDataProvider messageDataProvider, MessageContainer pMsgc) {
        super(messageDataProvider, pMsgc);
    }
    
    private CommonProgressShowingCallback(CommonMessageDataProvider messageDataProvider, MessageContainer pMsgc, MessagesByCode ... customMessages){
        this(messageDataProvider, pMsgc);
        messageDataProvider.setCustomMessages(customMessages);
    }

    @Override
    protected FailureHandler createFailureHandler(MessageDataProvider pMdp, MessageContainer pMsgc) {
        return new CommonFailureHandler(pMdp, pMsgc, getMsgPresenter());
    }
    
    @Override
    public void perform() {
        if (null != msgc){
            msgc.clear();
        }
        super.perform();
    }

//    public class CommonProcessIndicatorPresenter extends ProcessIndicatorPresenter {
//        public CommonProcessIndicatorPresenter() {
//            super(new CommonProcessIndicator() {
//                @Override
//                public void hide(){
//                    //dialog.hide();  
//                }
//            });
//        }
//    }    
//    protected ProcessIndicatorPresenter createProcessIndicator() {
//        return new CommonProcessIndicatorPresenter() {
//            
//        };
//    }    
}
