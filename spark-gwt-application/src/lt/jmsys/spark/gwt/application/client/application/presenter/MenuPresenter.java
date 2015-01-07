package lt.jmsys.spark.gwt.application.client.application.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.MenuMapper;
import lt.jmsys.spark.gwt.application.client.application.resource.MenuConstants;
import lt.jmsys.spark.gwt.application.client.application.view.MenuView.MenuItem;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.IsWidget;

import eu.itreegroup.spark.application.shared.db.bean.User_menu;

public class MenuPresenter {

    private static final Logger log = Logger.getLogger(MenuPresenter.class.getName());

    private final MenuConstants C = createMenuConstants();

    public interface MenuDisplay {

        MenuItem addItem(String pid, String id, String name, Command command);

        IsWidget getSecondLevelMenuBar();

        IsWidget getFirstLevelMenuBar();

        void clear();

        void clearSelection();
    }

    private Map<String, MenuItem> menusByPlaces = new HashMap<String, MenuItem>();
    private ModulePlace firstPlace;
    private MenuDisplay view;
    private static MenuMapper menuMapper = GWT.create(MenuMapper.class);

    public MenuPresenter(MenuDisplay view) {
        this.view = view;
    }

    public void initMenus(List<User_menu> menu) {
        firstPlace = null;
        menusByPlaces.clear();
        view.clear();

        if (null != menu) {
            List<User_menu> menuCopy = new ArrayList<User_menu>(menu);
            Set<User_menu> processedItems = new HashSet<User_menu>();
            Set<String> processedIds = new HashSet<String>();

            while (menuCopy.size() != 0) {
                for (User_menu m : menuCopy) {
                    /*                if ("G".equals(m.getMenu_type())) {
                                        m.setParent_menu_code(null);
                                    }*/
                    String pid = m.getParent_menu_code();
                    if (null == pid || processedIds.contains(pid)) {
                        addMenuItem(m);
                        processedItems.add(m);
                        processedIds.add(m.getMenu_code());
                    }
                }
                if (processedItems.size() == 0) {
                    // throw new IllegalArgumentException("no parent root found for menus: " + menuCopy.toString());
                    log.severe("root parent not found for menus: " + menuCopy.toString());
                    return;
                }
                menuCopy.removeAll(processedItems);
            }
        }
    }

    private static ModulePlace getPlaceByMenuItem(User_menu item) {
        return menuMapper.getPlace(item);
    }

    private void addMenuItem(final User_menu item) {
        ModulePlace place = getPlaceByMenuItem(item);

        String pid = item.getParent_menu_code();
        String id = item.getMenu_code();
        String name = C.getMessage(item.getMenu_code());

        if (null == firstPlace) {
            firstPlace = place;
        }
        Command c = null;
        if (null != place) {
            c = new Command() {

                @Override
                public void execute() {
                    ModulePlace p = getPlaceByMenuItem(item);// renew state
                    ClientFactory.getInstance().getPlaceController().goTo(p);
                }
            };
        }
        MenuItem menuItem = view.addItem(pid, id, name, c);

        if (null != place) {
            menusByPlaces.put(place.getClass().getName(), menuItem);
            if (null != place.getMenuCode()) {
                menusByPlaces.put(place.getMenuCode(), menuItem);
            }
        }
    }

    public void updateMenu(ModulePlace place) {
        MenuItem item = null;
        if (null != place.getMenuCode()) {
            item = menusByPlaces.get(place.getMenuCode());
        }
        if (null == item) {
            item = menusByPlaces.get(place.getClass().getName());
        }
        if (null != item) {
            item.select();
        } else if (null != place.getMenuCode()){
            view.clearSelection();
        }
    }

    public ModulePlace getFirstPlace() {
        return firstPlace;
    }

    protected MenuConstants createMenuConstants() {
        return GWT.create(MenuConstants.class);
    }

}
