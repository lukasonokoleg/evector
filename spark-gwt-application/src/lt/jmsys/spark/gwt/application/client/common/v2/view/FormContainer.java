package lt.jmsys.spark.gwt.application.client.common.v2.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public interface FormContainer extends AcceptsOneWidget {

    HasClickHandlers getCloseClickSource();
    
    void onChildSizeChanged();

}
