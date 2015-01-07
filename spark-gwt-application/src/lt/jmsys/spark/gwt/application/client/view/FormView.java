package lt.jmsys.spark.gwt.application.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import lt.jmsys.spark.gwt.application.client.application.AlcsApplication;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessagePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FormView extends Composite {

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);

    private VerticalPanel _bodyContainer = new VerticalPanel();
    private FormButtonBar _buttonBar = new FormButtonBar();
    private MessageContainer _messageContainer = new MessagePanel();
    private CaptionPanel _bodyContainerCaption = new CaptionPanel();
    private SimplePanel _listContainer = new SimplePanel();

    private VerticalPanel panel = new VerticalPanel();

    public interface MessagePanelDisplay {

    }

    public FormView() {
        this(false, true);
    }

    public FormView(boolean addCaption, boolean addList) {

        addMessageContainer();

        panel.add(_bodyContainer);
        panel.add(getButtonBar());
        panel.setCellHorizontalAlignment(getButtonBar(), HasHorizontalAlignment.ALIGN_RIGHT);

        Widget body;
        if (addCaption) {
            _bodyContainerCaption.add(panel);
            body = _bodyContainerCaption;
        } else {
            body = panel;
        }

        if (addList) {
            HorizontalPanel hp = new HorizontalPanel();
            hp.setSpacing(5);
            hp.add(_listContainer);
            hp.add(body);
            initWidget(hp);
        } else {
            initWidget(body);
        }

        StyleHelper.fullWidth(panel);
        StyleHelper.setBodyContainer(_bodyContainer);
        StyleHelper.setListContainer(_listContainer);
        AlcsApplication.addDebugName(this);
    }

    protected void addMessageContainer() {
        if (getMessageContainer() instanceof MessagePanel) {
            MessagePanel tmpMsgPanel = (MessagePanel) getMessageContainer();
            panel.add(tmpMsgPanel);
        }
    }

    public VerticalPanel getBodyContainer() {
        return _bodyContainer;
    }

    public void setCaption(String caption) {
        _bodyContainerCaption.setCaptionText(caption);
    }

    public ButtonBar getButtonBar() {
        return _buttonBar;
    }

    public MessagePanel getMessageContainer() {
        if (_messageContainer instanceof MessagePanel) {
            return (MessagePanel) _messageContainer;
        } else {
            return new MessagePanel();
        }
    }

    public AcceptsOneWidget getListContainer() {
        return _listContainer;
    }

    public void showEmptyPlaceholder(String message) {
        getBodyContainer().setVisible(false);
        panel.setVisible(false);
        _bodyContainerCaption.setVisible(false);
    }

    public void hideEmptyPlaceholder() {
        panel.setVisible(true);
        _bodyContainerCaption.setVisible(true);
        getBodyContainer().setVisible(true);
    }

    public interface ButtonBar extends IsWidget {

        void add(IsWidget widget);

        void addLeft(IsWidget widget);

        void addRight(IsWidget widget);

        // void addRightToLeft(IsWidget widget);

        void insertBefore(IsWidget widget, IsWidget beforeWidget);

        Collection<IsWidget> buttons();
    }

    public static class FormButtonBar implements ButtonBar {

        private FlowPanel leftPanel = new FlowPanel();
        private FlowPanel rightPanel = new FlowPanel();
        private HorizontalPanel panel = new HorizontalPanel();

        public FormButtonBar() {

            StyleHelper.setButtonBar(panel);
            panel.add(leftPanel);
            panel.add(rightPanel);
            panel.setCellHorizontalAlignment(leftPanel, HasHorizontalAlignment.ALIGN_LEFT);
            panel.setCellHorizontalAlignment(rightPanel, HasHorizontalAlignment.ALIGN_RIGHT);
        }

        @Override
        public Widget asWidget() {
            return panel;
        }

        @Override
        public void add(IsWidget widget) {
            addRight(widget);
        }

        @Override
        public void addLeft(IsWidget widget) {
            leftPanel.add(widget);
        }

        @Override
        public void addRight(IsWidget widget) {
            rightPanel.add(widget);
        }

        @Override
        public Collection<IsWidget> buttons() {
            List<IsWidget> buttons = new ArrayList<IsWidget>();
            Iterator<Widget> i = leftPanel.iterator();
            while (i.hasNext()) {
                buttons.add(i.next());
            }
            i = rightPanel.iterator();
            while (i.hasNext()) {
                buttons.add(i.next());
            }
            return buttons;
        }

        /*        @Override
                public void addRightToLeft(IsWidget widget) {
                    int c = rightPanel.getWidgetCount();
                    if (c > 0) {
                        rightPanel.insert(widget, c -1);
                    } else {
                        rightPanel.add(widget);
                    }
                }*/

        @Override
        public void insertBefore(IsWidget widget, IsWidget beforeWidget) {

            int beforeIndex = rightPanel.getWidgetIndex(beforeWidget);
            if (beforeIndex != -1) {
                rightPanel.insert(widget, beforeIndex);
            } else {
                beforeIndex = leftPanel.getWidgetIndex(beforeWidget);
                leftPanel.insert(widget, beforeIndex);
            }
        }

    }

    protected static class ButtonBarProxy implements ButtonBar {

        private ButtonBar bar;

        public ButtonBarProxy(ButtonBar bar) {
            this.bar = bar;
        }

        public Widget asWidget() {
            return bar.asWidget();
        }

        public void add(IsWidget widget) {
            bar.add(widget);
        }

        public void addLeft(IsWidget widget) {
            bar.addLeft(widget);
        }

        public void addRight(IsWidget widget) {
            bar.addRight(widget);
        }

        /*        public void addRightToLeft(IsWidget widget) {
                    bar.addRightToLeft(widget);
                }*/

        public void insertBefore(IsWidget widget, IsWidget beforeWidget) {
            bar.insertBefore(widget, beforeWidget);
        }

        @Override
        public Collection<IsWidget> buttons() {
            return bar.buttons();
        }

    }

}
