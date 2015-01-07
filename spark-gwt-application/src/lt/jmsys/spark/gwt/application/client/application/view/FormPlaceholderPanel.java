package lt.jmsys.spark.gwt.application.client.application.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FormPlaceholderPanel extends SimplePanel {

    private RootPanel containerPanel;
    private IsWidget widget;
    private boolean _indicatorIsShowing;
    private boolean _disableIndicatorWhileSetWidget;
    private boolean useIndicator;
    private Widget emptyWidget = new Label();

    public FormPlaceholderPanel() {
        this(true, null);
    }

    public FormPlaceholderPanel(boolean useIndicator, RootPanel containerPanel) {
        this.containerPanel = containerPanel;
        this.useIndicator = useIndicator;
        Window.addResizeHandler(new ResizeHandler() {

            @Override
            public void onResize(ResizeEvent event) {
                resizeWidget(event.getWidth(), event.getHeight());
            }
        });
        setWidget(null);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (useIndicator && getWidget() == null) {
            //CommonProgressShowingCallback.getIndicatorStatic().show();
            _indicatorIsShowing = true;
            setVisible(false);
        }
    }

    @Override
    public void setWidget(Widget w) {
        if (null != w) {
            _disableIndicatorWhileSetWidget = true;
        }
        try {
            this.widget = w;
            resizeWidget(Window.getClientWidth(), Window.getClientHeight());
            if (useIndicator && null == w) {
                super.setWidget(emptyWidget);
                setVisible(false);
            } else {
                super.setWidget(w);
                setVisible(true);
            }
            if (useIndicator && _indicatorIsShowing && null != w) {
                //CommonProgressShowingCallback.getIndicatorStatic().hide();
            }
        } finally {
            _disableIndicatorWhileSetWidget = false;
        }
    }

    protected void resizeWidget(int width, int height) {
        if (widget instanceof AcceptsResize) {
            width = Math.max(800, width - 50);
            height = Math.max(600, height - 170);
            widget.asWidget().setWidth(width + "px");
            widget.asWidget().setHeight(height + "px");
            ((AcceptsResize) widget).resize(width, height);
        }
    }

    @Override
    public boolean remove(Widget w) {
        if (useIndicator && !_disableIndicatorWhileSetWidget) {
            //CommonProgressShowingCallback.getIndicatorStatic().show();
            _indicatorIsShowing = true;
        }
        return super.remove(w);
    }

    @Override
    public void setVisible(boolean visible) {

        Element loadingPanel = useIndicator ? DOM.getElementById("spark-loading") : null;
        if (null != loadingPanel) {
            loadingPanel.getStyle().setDisplay(visible ? Display.NONE : Display.BLOCK);
        }
        if (null != containerPanel) {
            containerPanel.setVisible(visible);
        }
        super.setVisible(visible);
    }

}
