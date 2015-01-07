package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;


public class AlcsBrowseFormPresenter extends AbstractActivity{
    
    public interface AlcsBrowseFormDisplay extends IsWidget{
        void refresh();
        void clear();
        MessageContainer getMessageContainer();
    }
    
    private AlcsBrowseFormDisplay view;
        
    public AlcsBrowseFormPresenter(AlcsBrowseFormDisplay view) {
        this.view = view;
    }
    
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view.refresh();
        panel.setWidget(view);
    }

}
