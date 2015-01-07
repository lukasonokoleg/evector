package lt.jmsys.spark.gwt.client.ui.message;

import lt.jmsys.spark.gwt.client.ui.animation.AnimationHelper;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;
import lt.jmsys.spark.gwt.client.ui.css.SparkCssResource;
import lt.jmsys.spark.gwt.client.ui.css.SparkResourcesFactory;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MessagePanelGroup extends Composite {

    // private Logger log = Logger.getLogger(getClass().getName());

    public static int DEFAULT_OFFSET_HEIGHT = 66;

    private static final SparkCssResource _CSS = SparkResourcesFactory.create().css();

    private final static String SPARK_MSG_PANEL_CAPTION = _CSS.sparkMsgPanelCaption();

    private final static String SPARK_MSG_PANEL_BODY = _CSS.sparkMsgPanelBody();

    private final static String SPARK_MSG_PANEL_RESIZEABLE = _CSS.sparkMsgPanelResizeable();

    private final static String SPARK_MSG_PANEL_CLOSE = _CSS.sparkMsgPanelClose();

    private final static String SPARK_MSG_PANEL_FOOTER = _CSS.sparkMsgPanelFooter();

    private final static String CSS_PANEL_PANE = _CSS.sparkMessagePanelPane();

    private final static String SPARK_MSG_GROUP = _CSS.sparkMsgGroup();

    private Anchor btnClose = null;

    private FlowPanel layoutPanel = new FlowPanel();

    private FlowPanel captionPanel = new FlowPanel();

    private ScrollPanel scrollPanel = new ScrollPanel();

    private FlowPanel footerPanel = new FlowPanel();

    private FlexTable messagesTable = new FlexTable();

    private AnimationHelper animation = new AnimationHelper(layoutPanel);

    public MessagePanelGroup() {
        btnClose = ButtonHelper.getInstance().createCloseIcon("");
        btnClose.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                MessagePanelGroup.this.close();
            }
        });
        // set style names
        messagesTable.setStyleName(SPARK_MSG_GROUP, true);
        btnClose.addStyleName(SPARK_MSG_PANEL_CLOSE);
        captionPanel.setStyleName(SPARK_MSG_PANEL_CAPTION);
        scrollPanel.setStyleName(SPARK_MSG_PANEL_BODY);
        footerPanel.setStyleName(SPARK_MSG_PANEL_FOOTER);
        layoutPanel.getElement().addClassName(CSS_PANEL_PANE);
        // create layout
        captionPanel.add(btnClose);
        scrollPanel.add(messagesTable);
        layoutPanel.add(captionPanel);
        layoutPanel.add(scrollPanel);
        layoutPanel.add(footerPanel);
        initWidget(layoutPanel);
    }

    public FlexTable getTable() {
        return messagesTable;
    }

    public FlowPanel getFooter() {
        return footerPanel;
    }

    public void show() {
        animation.show();
        setDefaultSize();
    }

    public void close() {
        animation.hide(new Command() {

            @Override
            public void execute() {
                onClosed();
            }
        });
    }

    public void clear() {
        animation.cancel();
        for (int i = getTable().getRowCount() - 1; i >= 0; i--) {
            messagesTable.removeRow(i);
            footerPanel.clear();
        }
        setVisible(false);
    }

    protected void onClosed() {
    }

    public void setClosable(boolean closable) {
        if (closable) {
            btnClose.sinkEvents(Event.MOUSEEVENTS);
            btnClose.sinkEvents(Event.ONCLICK);
        } else {
            btnClose.unsinkEvents(Event.MOUSEEVENTS);
            btnClose.unsinkEvents(Event.ONCLICK);
        }
        this.btnClose.setVisible(closable);
    }

    public void removeDefaultSize() {
        scrollPanel.removeStyleName(SPARK_MSG_PANEL_RESIZEABLE);
        scrollPanel.setHeight("auto");
        scrollPanel.getElement().getStyle().setProperty("maxHeight", "none");
    }

    public void setDefaultSize() {
        if (messagesTable.getOffsetHeight() > DEFAULT_OFFSET_HEIGHT) {
            String maxHeight = messagesTable.getOffsetHeight() + Unit.PX.getType();
            String height = DEFAULT_OFFSET_HEIGHT + Unit.PX.getType();
            scrollPanel.addStyleName(SPARK_MSG_PANEL_RESIZEABLE);
            scrollPanel.setHeight(height);
            scrollPanel.getElement().getStyle().setProperty("maxHeight", maxHeight);
        } else {
            removeDefaultSize();
        }
    }
}
