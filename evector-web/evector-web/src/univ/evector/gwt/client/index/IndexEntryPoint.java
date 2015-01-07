package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.client.BaseEntryPoint;
import lt.jmsys.spark.gwt.client.ui.message.MessagePanelGroup;

public class IndexEntryPoint extends BaseEntryPoint {

    public IndexEntryPoint() {
    }

    @Override
    protected void init() {
        super.init();
        initMessagePanel();
    }

    protected void initMessagePanel() {
        MessagePanelGroup.DEFAULT_OFFSET_HEIGHT = 103;
    }

}
