package lt.jmsys.spark.gwt.application.client.application.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderView extends FlowPanel {

    public HeaderView() {
    }

    /**
     * Add widget to panel with float direction setting
     * @param w - widget
     * @param align - float direction
     */
    public void add(Widget widget, String align) {
        super.add(widget);
        widget.addStyleName(align);
    }
}
