package lt.jmsys.spark.gwt.application.client.application.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.application.client.application.presenter.MenuPresenter.MenuDisplay;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;

import com.google.gwt.user.client.Command;

public class MenuView implements MenuDisplay {

    private static final String CSS_MENU_ITEM = "spark-MenuItem";

    private static final String CSS_MENU_POPUP = "spark-MenuPopup";

    private static final String CSS_MENU_LEVE1 = "spark-MenuLevel1";

    private static final String CSS_MENU_LEVEL2 = "spark-MenuLevel2";

    private static final String CSS_MENU_LEVEL2_PANEL = "spark-MenuLevel2Panel";

    private List<MenuItem> items = new ArrayList<MenuItem>();

    private Map<String, MenuItem> menuItemMap = new HashMap<String, MenuItem>();

    private MenuItem selected;

    private MenuBar secondLevelMenuBar = new MenuBar();
    private MenuBar firstLevelMenuBar = new MenuBar() {

        protected void showMenuPopup(com.google.gwt.user.client.ui.MenuItem item) {
            super.showMenuPopup(item);
            if (selected != null) {
                StyleHelper.selected(selected, false);
                String selectedPid = getFirstLevelMenuBarSelectedId();
                if (selectedPid == null || !selected.getPid().contentEquals(selectedPid)) {
                    ((MenuItem) item).getSubMenu().clearPressedMenu();
                }
            }
        };

        protected void onMenuPopupClose() {
            if (selected != null) {
                StyleHelper.selected(selected, true);
            }
        };
    };

    private String getFirstLevelMenuBarSelectedId() {
        MenuItem selected = (MenuItem) firstLevelMenuBar.getSelectedItem();
        return selected != null ? selected.getId() : null;
    }

    public MenuView() {
        firstLevelMenuBar.setStyleName(CSS_MENU_LEVE1);
        secondLevelMenuBar.setStyleName(CSS_MENU_LEVEL2);
        firstLevelMenuBar.setAutoOpen(false);
    }

    @Override
    public MenuItem addItem(String pid, String id, String name, Command command) {
        if (null == pid) {
            MenuItem item = new MenuItem(null, id, name, command);
            item.ensureDebugId(id);
            items.add(item);
            menuItemMap.put(id, item);
            firstLevelMenuBar.addItem(item);
            return item;
        } else {
            MenuItem parentItem = menuItemMap.get(pid);
            if (null == parentItem) {
                throw new IllegalArgumentException("Cannot add menu item " + id + ", parent menu " + pid + " not found");
            }
            MenuItem item = parentItem.addChildItem(pid, id, name, command);
            menuItemMap.put(id, item);
            return item;
        }
    }

    @Override
    public void clear() {
        items.clear();
        menuItemMap.clear();
        firstLevelMenuBar.clearItems();
        secondLevelMenuBar.clearItems();
    }

    @Override
    public void clearSelection() {
        firstLevelMenuBar.clearPressedMenu();
        secondLevelMenuBar.clearItems();
        selected = null;
    }

    public void selectItem(MenuItem item) {
        _selectItem(item);
        secondLevelMenuBar.clearItems();
        MenuBar subMenu = (MenuBar) item.getParentMenu();
        if (null != subMenu) {
            MenuItem selectedItem = null;
            for (com.google.gwt.user.client.ui.MenuItem i : subMenu.getItems()) {
                MenuItem mi = (MenuItem) i;
                MenuItem secondLevelItem = new MenuItem(mi.getPid(), mi.getPid(), mi.getText(), mi.getCommand());
                secondLevelItem.ensureDebugId(mi.getPid() + "_" + mi.getId());
                if (mi.isSelected()) {
                    secondLevelItem.setSelected(true);
                    selectedItem = secondLevelItem;
                }
                secondLevelMenuBar.addItem(secondLevelItem);
            }
            if (null != selectedItem) {
                secondLevelMenuBar.setActiveItem(selectedItem);
                selected = selectedItem;
            }
        }
    }

    private void _selectItem(MenuItem item) {
        if (null != item.getPid()) {
            MenuItem parentItem = menuItemMap.get(item.getPid());
            MenuBar subMenu = parentItem.getSubMenu();
            for (com.google.gwt.user.client.ui.MenuItem i : subMenu.getItems()) {
                MenuItem mi = (MenuItem) i;
                mi.setSelected(mi.equals(item));
            }
            _selectItem(parentItem);
        } else {
            firstLevelMenuBar.setActiveItem(item);
        }

    }

    @Override
    public MenuBar getSecondLevelMenuBar() {
        return secondLevelMenuBar;
    }

    @Override
    public MenuBar getFirstLevelMenuBar() {
        return firstLevelMenuBar;
    }

    private static class MenuItemCommand implements Command {

        private MenuItem menuItem;

        @Override
        public void execute() {
            if (null != menuItem) {
                menuItem.click();
            }
        }
    }

    public class MenuItem extends com.google.gwt.user.client.ui.MenuItem {

        private boolean selected;
        private Command command;
        private String pid;
        private String id;

        public MenuItem(String pid, String id, String name, Command command) {
            this(pid, id, name, new MenuItemCommand(), command);
        }

        private MenuItem(String pid, String id, String name, MenuItemCommand menuItemCommand, Command command) {
            super(name, command);
            menuItemCommand.menuItem = this;
            super.setScheduledCommand(menuItemCommand);
            this.pid = pid;
            this.id = id;
            setStyleName(CSS_MENU_ITEM);
            if (null == command) {
                command = new Command() {

                    @Override
                    public void execute() {
                        clickSelectedOrFirstChild();
                    }
                };
            }
            setCommand(command);
        }

        public String getPid() {
            return pid;
        }

        public String getId() {
            return id;
        }

        @Override
        public void setCommand(Command command) {
            this.command = command;
        }

        @Override
        public Command getCommand() {
            return command;
        }

        protected void setSelected(boolean selected) {
            this.selected = selected;
            StyleHelper.selected(this, selected);
        }

        public boolean isSelected() {
            return selected;
        }

        public void click() {
            if (null != command) {
                // select();
                command.execute();
            }
        }

        public void select() {
            selectItem(this);
        }

        public MenuItem addChildItem(String pid, String id, String name, Command command) {
            MenuBar subMenu = getSubMenu();
            if (null == subMenu) {
                subMenu = new MenuBar();
                subMenu.setStyleName(CSS_MENU_POPUP);
                subMenu.addStyleName(CSS_MENU_LEVEL2);
                subMenu.addStyleName(CSS_MENU_LEVEL2_PANEL);
                setSubMenu(subMenu);
            }
            MenuItem item = new MenuItem(pid, id, name, command);
            item.ensureDebugId(pid + "_" + id);
            subMenu.addItem(item);
            return item;
        }

        @Override
        public MenuBar getSubMenu() {
            return (MenuBar) super.getSubMenu();
        }

        @Override
        public void setSubMenu(com.google.gwt.user.client.ui.MenuBar subMenu) {
            if (subMenu instanceof MenuBar) {
                super.setSubMenu(subMenu);
            } else {
                throw new IllegalArgumentException("subMenu should be instanceof " + MenuBar.class.getName());
            }
        }

        public void clickSelectedOrFirstChild() {
            MenuBar subMenu = getSubMenu();
            if (null != subMenu && subMenu.getItemsCount() != 0) {
                for (com.google.gwt.user.client.ui.MenuItem i : subMenu.getItems()) {
                    MenuItem mi = (MenuItem) i;
                    if (mi.isSelected()) {
                        mi.click();
                        return;
                    }
                }
                ((MenuItem) subMenu.getItems().get(0)).click();
            }
        }

    }

}
