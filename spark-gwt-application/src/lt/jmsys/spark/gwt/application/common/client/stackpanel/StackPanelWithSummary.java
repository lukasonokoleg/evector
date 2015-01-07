package lt.jmsys.spark.gwt.application.common.client.stackpanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.HasBeforeSelectionHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.AnimatedLayout;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

public class StackPanelWithSummary extends ResizeComposite implements ProvidesResize, IndexedPanel.ForIsWidget, AnimatedLayout, HasBeforeSelectionHandlers<Integer>,
        HasSelectionHandlers<Integer> {

    private class Header extends Composite implements HasClickHandlers {

        private Grid grid;

        private Label headerText;

        private Anchor headerIcon;

        public Header(String text, Double fontSizePX) {
            VerticalPanel layout = new VerticalPanel();
            grid = new Grid(1, 2);
            grid.setWidth("100%");
            grid.getCellFormatter().setWidth(0, 0, "1");
            grid.getCellFormatter().setWidth(0, 1, "100%");
            headerText = new Label(text);
            headerText.getElement().getStyle().setFontSize(fontSizePX, Unit.PX);
            headerIcon = ButtonHelper.getInstance().createExpandIcon("");
            grid.setWidget(0, 1, headerText);
            grid.setWidget(0, 0, headerIcon);
            layout.add(grid);
            layout.setWidth("100%");
            initWidget(layout);
        }

        public HandlerRegistration addClickHandler(ClickHandler handler) {
            return this.addDomHandler(handler, ClickEvent.getType());
        }

        public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
            return this.addDomHandler(handler, MouseOutEvent.getType());
        }

        public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
            return this.addDomHandler(handler, MouseOverEvent.getType());
        }

        @Override
        protected Widget getWidget() {
            return super.getWidget();
        }

        public void setOpen(Boolean isOpen) {
            if (isOpen) {
                ButtonHelper.getInstance().makeCollapseIcon(headerIcon);
            } else {
                ButtonHelper.getInstance().makeExpandButton(headerIcon);
            }
        }

    }

    private static class LayoutData {

        public double headerSize;
        public double summaryHeight;
        public Header header;
        public StackPanelContentWidget widget;

        public LayoutData(StackPanelContentWidget widget, Header header, double headerSize) {
            this.widget = widget;
            this.header = header;
            this.headerSize = headerSize;
            summaryHeight = widget.getSummaryHeight();
        }

        public void setOpen(Boolean isOpen) {
            header.setOpen(isOpen);
            widget.setOpen(isOpen);
        }
    }

    private class CustomLayoutPanel extends LayoutPanel {

        @Override
        protected WidgetCollection getChildren() {
            return super.getChildren();
        }
    }

    private static final String WIDGET_STYLE = "gwt-StackLayoutPanel";
    private static final String CONTENT_STYLE = "gwt-StackLayoutPanelContent";
    private static final String HEADER_STYLE = "gwt-StackLayoutPanelHeader";
    private static final String HEADER_STYLE_HOVERING = "gwt-StackLayoutPanelHeader-hovering";

    private static final int ANIMATION_TIME = 250;

    private int animationDuration = ANIMATION_TIME;
    private CustomLayoutPanel layoutPanel;
    private final Unit unit = Unit.PX;
    private final ArrayList<LayoutData> layoutData = new ArrayList<LayoutData>();
    private int selectedIndex = -1;

    public StackPanelWithSummary() {
        initWidget(layoutPanel = new CustomLayoutPanel());
        setStyleName(WIDGET_STYLE);
    }

    public void add(final StackPanelContentWidget widget, String headerText, double headerTextSize, double headerSize) {
        insert(widget, headerText, headerTextSize, headerSize, getWidgetCount());
    }

    public HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler<Integer> handler) {
        return addHandler(handler, BeforeSelectionEvent.getType());
    }

    public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }

    public void animate(int duration) {
        animate(duration, null);
    }

    public void animate(int duration, AnimationCallback callback) {
        // Don't try to animate zero widgets.
        if (layoutData.size() == 0) {
            if (callback != null) {
                callback.onAnimationComplete();
            }
            return;
        }

        double top = 0, bottom = 0;
        int i = 0;
        for (; i < layoutData.size(); ++i) {
            LayoutData data = layoutData.get(i);
            layoutPanel.setWidgetTopHeight(data.header, top, unit, data.headerSize, unit);

            top += data.headerSize + data.summaryHeight;

            layoutPanel.setWidgetTopHeight(data.widget, top - data.summaryHeight, unit, data.summaryHeight, unit);

            if (i == selectedIndex) {
                data.setOpen(true);
                int k = i;
                k++;
                for (; k < layoutData.size(); ++k) {
                    data = layoutData.get(k);
                    data.setOpen(false);
                }
                break;
            } else {
                data.setOpen(false);
            }
        }

        for (int j = layoutData.size() - 1; j > i; --j) {
            LayoutData data = layoutData.get(j);
            layoutPanel.setWidgetBottomHeight(data.header, bottom + data.summaryHeight, unit, data.headerSize, unit);
            layoutPanel.setWidgetBottomHeight(data.widget, bottom, unit, data.summaryHeight, unit);
            bottom += data.headerSize + data.summaryHeight;
        }

        LayoutData data = layoutData.get(selectedIndex);
        layoutPanel.setWidgetTopBottom(data.widget, top - data.summaryHeight, unit, bottom, unit);

        layoutPanel.animate(duration, callback);
    }

    public void clear() {
        layoutPanel.clear();
        layoutData.clear();
        selectedIndex = -1;
    }

    public void forceLayout() {
        layoutPanel.forceLayout();
    }

    public int getVisibleIndex() {
        return selectedIndex;
    }

    public Widget getVisibleWidget() {
        if (selectedIndex == -1) {
            return null;
        }
        return getWidget(selectedIndex);
    }

    public Widget getWidget(int index) {
        return layoutPanel.getWidget(index * 2 + 1);
    }

    public int getWidgetCount() {
        return layoutPanel.getWidgetCount() / 2;
    }

    public int getWidgetIndex(IsWidget child) {
        return getWidgetIndex(asWidgetOrNull(child));
    }

    public int getWidgetIndex(Widget child) {
        int index = layoutPanel.getWidgetIndex(child);
        if (index == -1) {
            return index;
        }
        return (index - 1) / 2;
    }

    public void insert(StackPanelContentWidget child, String headerText, double headerTextSize, double headerSize, int beforeIndex) {
        insert(child, new Header(headerText, headerTextSize), headerSize, beforeIndex);
    }

    public Iterator<Widget> iterator() {
        return new Iterator<Widget>() {

            int i = 0, last = -1;

            public boolean hasNext() {
                return i < layoutData.size();
            }

            public Widget next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return layoutData.get(last = i++).widget;
            }

            public void remove() {
                if (last < 0) {
                    throw new IllegalStateException();
                }

                StackPanelWithSummary.this.remove(layoutData.get(last).widget);
                i = last;
                last = -1;
            }
        };
    }

    @Override
    public void onResize() {
        layoutPanel.onResize();
    }

    public boolean remove(int index) {
        return remove(getWidget(index));
    }

    public boolean remove(Widget child) {
        if (child.getParent() != layoutPanel) {
            return false;
        }

        for (int i = 0; i < layoutData.size(); ++i) {
            LayoutData data = layoutData.get(i);
            if (data.widget == child) {
                layoutPanel.remove(data.header);
                layoutPanel.remove(data.widget);

                data.header.removeStyleName(HEADER_STYLE);
                data.widget.removeStyleName(CONTENT_STYLE);

                layoutData.remove(i);

                if (selectedIndex == i) {
                    selectedIndex = -1;
                    if (layoutData.size() > 0) {
                        showWidget(layoutData.get(0).widget);
                    }
                } else {
                    if (i <= selectedIndex) {
                        selectedIndex--;
                    }
                    animate(animationDuration);
                }
                return true;
            }
        }

        return false;
    }

    public void setAnimationDuration(int duration) {
        this.animationDuration = duration;
    }

    public void setHeaderHTML(int index, String html) {
        checkIndex(index);
        LayoutData data = layoutData.get(index);

        Widget headerWidget = data.header.getWidget();
        assert headerWidget instanceof HasHTML : "Header widget does not implement HasHTML";
        ((HasHTML) headerWidget).setHTML(html);
    }

    public void setHeaderHTML(int index, SafeHtml html) {
        setHeaderHTML(index, html.asString());
    }

    public void setHeaderText(int index, String text) {
        checkIndex(index);
        LayoutData data = layoutData.get(index);

        Widget headerWidget = data.header.getWidget();
        assert headerWidget instanceof HasText : "Header widget does not implement HasText";
        ((HasText) headerWidget).setText(text);
    }

    public void showWidget(int index) {
        showWidget(index, true);
    }

    public void showWidget(int index, boolean fireEvents) {
        checkIndex(index);
        showWidget(index, animationDuration, fireEvents);
    }

    public void showWidget(Widget child) {
        showWidget(getWidgetIndex(child));
    }

    public void showWidget(Widget child, boolean fireEvents) {
        showWidget(getWidgetIndex(child), animationDuration, fireEvents);
    }

    @Override
    protected void onLoad() {
        // When the widget becomes attached, update its layout.
        animate(0);
    }

    private void checkIndex(int index) {
        assert (index >= 0) && (index < getWidgetCount()) : "Index out of bounds";
    }

    private void insert(final StackPanelContentWidget child, final Header header, double headerSize, int beforeIndex) {
        assert (beforeIndex >= 0) && (beforeIndex <= getWidgetCount()) : "beforeIndex out of bounds";

        int idx = getWidgetIndex(child);
        if (idx != -1) {
            remove(child);
            if (idx < beforeIndex) {
                beforeIndex--;
            }
        }

        int widgetIndex = beforeIndex * 2;
        layoutPanel.insert(child, widgetIndex);
        layoutPanel.insert(header, widgetIndex);

        layoutPanel.setWidgetLeftRight(header, 0, Unit.PX, 0, Unit.PX);
        layoutPanel.setWidgetLeftRight(child, 0, Unit.PX, 0, Unit.PX);

        LayoutData data = new LayoutData(child, header, headerSize);
        layoutData.add(beforeIndex, data);

        header.addStyleName(HEADER_STYLE);
        child.addStyleName(CONTENT_STYLE);

        header.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                showWidget(child);
            }
        });

        header.addMouseOutHandler(new MouseOutHandler() {

            public void onMouseOut(MouseOutEvent event) {
                header.removeStyleName(HEADER_STYLE_HOVERING);
            }
        });

        header.addMouseOverHandler(new MouseOverHandler() {

            public void onMouseOver(MouseOverEvent event) {
                header.addStyleName(HEADER_STYLE_HOVERING);
            }
        });

        if (selectedIndex == -1) {
            showWidget(0);
        } else if (beforeIndex <= selectedIndex) {
            selectedIndex++;
        }

        if (isAttached()) {
            animate(animationDuration);
        }
    }

    private void showWidget(int index, final int duration, boolean fireEvents) {
        checkIndex(index);
        if (index == selectedIndex) {
            return;
        }

        if (fireEvents) {
            BeforeSelectionEvent<Integer> event = BeforeSelectionEvent.fire(this, index);
            if ((event != null) && event.isCanceled()) {
                return;
            }
        }

        selectedIndex = index;

        if (isAttached()) {
            animate(duration);
        }
        if (fireEvents) {
            SelectionEvent.fire(this, index);
        }
    }
}