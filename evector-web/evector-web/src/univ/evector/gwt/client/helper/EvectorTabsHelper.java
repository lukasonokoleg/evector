package univ.evector.gwt.client.helper;

import lt.jmsys.spark.gwt.client.ui.animation.AnimationHelper;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Widget;

public class EvectorTabsHelper {

    public static DecoratedTabPanel createTabPanel() {
        final DecoratedTabPanel tabPanel = new DecoratedTabPanel();
        tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {

            @Override
            public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
                Widget widget = tabPanel.getWidget(event.getItem());
                if (widget != null) {
                    widget.getElement().getStyle().setOpacity(0);
                    AnimationHelper.fade(widget, 600, 1.0, null);
                }
            }
        });
        return tabPanel;
    }
}