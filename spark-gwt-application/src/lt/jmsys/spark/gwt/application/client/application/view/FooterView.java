package lt.jmsys.spark.gwt.application.client.application.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import eu.itreegroup.spark.application.shared.Version;

public class FooterView extends FlowPanel {

    public FooterView() {

        Label version = new Label(Version.getVersion());
        version.setStyleName("version-number");
        version.addStyleName("right");
        add(version);

        setWidth("100%");
        /*setCellWidth(leftSide, "100%");
        setCellHorizontalAlignment(leftSide, HasHorizontalAlignment.ALIGN_LEFT);
        setCellHorizontalAlignment(version, HasHorizontalAlignment.ALIGN_RIGHT);*/
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
