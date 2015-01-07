package lt.jmsys.spark.gwt.application.client.application.view;

import java.util.List;

import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter.BreadCrumbsDisplay;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;


public class BreadCrumbsView extends Composite implements BreadCrumbsDisplay{
    
    private HorizontalPanel panel = new HorizontalPanel();
    
    
    public BreadCrumbsView() {
        panel.setSpacing(5);
        initWidget(panel);
    }
    
    @Override
    public void setPlaces(List<ModulePlace> places) {
        panel.clear();
        if (null != places){
            for(int i=0;i<places.size();i++){
                if (i != 0){
                    panel.add(new Label(" > "));
                }
                panel.add(new Label(places.get(i).getName()));
            }
        }        
    }
}
