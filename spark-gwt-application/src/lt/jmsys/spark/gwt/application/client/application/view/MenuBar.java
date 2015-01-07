package lt.jmsys.spark.gwt.application.client.application.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;

class MenuBar extends com.google.gwt.user.client.ui.MenuBar {

    private Map<MenuItem, com.google.gwt.user.client.ui.MenuBar> activeItemSubmenu = new HashMap<MenuItem, com.google.gwt.user.client.ui.MenuBar>();

    protected static MenuItem timerItem;
    protected static MenuItem popupMenuItem;
    private MenuBar parentMenu;

    // http://code.google.com/p/google-web-toolkit/issues/detail?id=374
    // http://blog.zont.eu/2009/07/workaround-for-gwt-menubar-issue-374.html
    private Timer timer = new Timer() {

        @Override
        public void run() {
            if (null != timerItem) {
                MenuBar.this.setSelectionStyle(timerItem, false);
            }
        }
    };

    public MenuBar() {

    }

    public MenuBar(boolean vertical) {
        super(vertical);
    }

    public MenuItem getSelectedItem() {
        return super.getSelectedItem();
    }

    public MenuItem addItem(MenuItem item) {
        item = super.addItem(item);
        if (item.getSubMenu() instanceof MenuBar) {
            ((MenuBar) item.getSubMenu()).parentMenu = this;
        }
        return item;
    }

    @Override
    protected PopupPanel getPopup() {
        return super.getPopup();
    }

    public void clearPressedMenu() {
        for (MenuItem menuItem : getItems()) {
            StyleHelper.selected(menuItem, false);
            com.google.gwt.user.client.ui.MenuBar subMenu = activeItemSubmenu.get(menuItem);
            if (null != subMenu) {
                menuItem.setSubMenu(subMenu);
            }
        }
        activeItemSubmenu.clear();
    }

    public void setActiveItem(MenuItem menuItem) {
        clearPressedMenu();
        StyleHelper.selected(menuItem, true);
    }

    public int getItemsCount() {
        return getItems().size();
    }

    @Override
    public List<MenuItem> getItems() {
        return super.getItems();
    }

    // private static PopupPanel currentPopup;

    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        Element target = DOM.eventGetTarget(event);
        MenuItem item = myFindItem(target);
        if (item == null) {
            PopupPanel popup = popupMenuItem != null ? ((MenuBar) popupMenuItem.getParentMenu()).getPopup() : null;
            if (popup != null && popup.getElement().isOrHasChild(target)) {
                item = popupMenuItem;
            } else {
                return;
            }
        }

        if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
            timerItem = popupMenuItem;
            timer.schedule(100);
        } else if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
            timerItem = null;
            this.setSelectionStyle(item, true);
        }

    }

    private void setSelectionStyle(MenuItem item, boolean selected) {
        if (selected) {
            item.addStyleDependentName("selected");
        } else {
            closeAllChildren(false);// since gwt 2.1
            //onPopupClosed(null, false);
            if (null != item.getParentMenu()) {
                item.getParentMenu().closeAllChildren(false);
                item.getParentMenu().selectItem(null);
            }
            if (null != parentMenu) {
                parentMenu.closeAllChildren(false);// since gwt 2.1
                //parentMenu.onPopupClosed(null, false);
                parentMenu.selectItem(null);
            }

        }
    }

    private MenuItem myFindItem(Element hItem) {
        for (MenuItem item : getItems()) {
            if (DOM.isOrHasChild(item.getElement(), hItem))
                return item;
        }
        return null;
    }

    private HandlerRegistration popupCloseHandler;

    @Override
    protected void showMenuPopup(final MenuItem item) {
        popupMenuItem = item;
        // Show the popup, ensuring that the menubar's event preview remains on top
        // of the popup's.
        getPopup().setPopupPositionAndShow(new PopupPanel.PositionCallback() {

            public void setPosition(int offsetWidth, int offsetHeight) {
                getPopup().setPopupPosition(0, MenuBar.this.getAbsoluteTop() + MenuBar.this.getOffsetHeight());
                getPopup().getWidget().getElement().getStyle().setPaddingLeft(20, Unit.PX);
                getPopup().setWidth("600px");
            }
        });
        if (popupCloseHandler != null) {
            popupCloseHandler.removeHandler();
        }
        popupCloseHandler = getPopup().addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(CloseEvent<PopupPanel> event) {
                onMenuPopupClose();
            }
        });
    }

    protected void onMenuPopupClose() {

    }
}
