package lt.jmsys.spark.gwt.application.common.client.widget;

import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.Field;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VerticalFieldsPanel extends Composite {

    private FlowPanel panel = new FlowPanel();

    public VerticalFieldsPanel() {
        initWidget(panel);
        StyleHelper.setVerticalFieldsPanel(this);
    }

    public void add(Field<?, ?> field, Widget... widgets) {
        if (null == widgets || widgets.length == 0) {
            panel.add(field.getLabelWidget());
            panel.add(field);
        } else {
            FlowPanel fp = new FlowPanel();
            StyleHelper.setVerticalFieldsPanelFieldGroup(fp);
            fp.add(field.getLabelWidget());
            fp.add(field);
            for (Widget w : widgets) {
                fp.add(w);
            }
            panel.add(fp);
        }
    }

    public void addWidget(Widget w) {
        FlowPanel fp = new FlowPanel();
        StyleHelper.setVerticalFieldsPanelFieldGroup(fp);
        fp.add(w);
        panel.add(fp);
    }

}
