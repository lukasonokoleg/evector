package lt.jmsys.spark.gwt.application.common.client.stackpanel;

import com.google.gwt.user.client.ui.SimplePanel;

public abstract class StackPanelContentWidget extends SimplePanel {

    protected SimplePanel content;

    protected SimplePanel summary;

    protected Double summaryHeight;

    public Double getSummaryHeight() {
        return summaryHeight;
    }

    public SimplePanel getContent() {
        return content;
    }

    public SimplePanel getSummary() {
        return summary;
    }

    public void setOpen(Boolean isOpen) {
        setWidget(isOpen ? content : summary);
    }

}
