package lt.jmsys.spark.gwt.application.common.client.widget;

import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;


public class FeedbackMessage {
    
    private static FeedbackPopup instance;
    
    public static void showMessage(UIObject relativeTo, String message){
        showMessage(relativeTo, message, 3000);
    }
    
    public static void showMessage(UIObject relativeTo, String message, int delayTime) {
        if (null == instance) {
            instance = new FeedbackPopup();
        }
        instance.show(relativeTo, message, delayTime);
    }

    private static class FeedbackPopup extends Composite{
        
        private Label messageLabel = new Label();
        private PopupPanel popup = new PopupPanel(true);
        
        
        public FeedbackPopup() {
            initWidget(messageLabel);
            StyleHelper.setFeedbackPopup(popup);
            popup.setAnimationEnabled(true);
            popup.setAutoHideOnHistoryEventsEnabled(false);
            popup.add(this);

        }
        
        public void show(UIObject relativeTo, String message, int delayTime) {
            messageLabel.getElement().setInnerHTML(message);
            if (null != relativeTo){
                popup.showRelativeTo(relativeTo);
            }else{
                popup.center();
            }
            Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
                
                @Override
                public boolean execute() {
                    popup.hide();
                    return false;
                }
            }, delayTime);
        }
        
    }
    
}
