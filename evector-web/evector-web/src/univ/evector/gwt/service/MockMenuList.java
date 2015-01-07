package univ.evector.gwt.service;

import java.util.ArrayList;

import univ.evector.gwt.client.index.IndexMenuMapper;
import eu.itreegroup.spark.application.shared.db.bean.User_menu;

public class MockMenuList {

    public static ArrayList<User_menu> getMenu() {
        ArrayList<User_menu> menu = new ArrayList<User_menu>();
        double id = 1d;
        menu.add(new User_menu("menu.f001", "I", "Files", null, IndexMenuMapper.F.F001, id++));
        menu.add(new User_menu("menu.f002", "I", "Books", null, IndexMenuMapper.F.F002, id++));

        menu.add(new User_menu("menu.f003", "I", "Paragraphs", null, IndexMenuMapper.F.F003, id++));
        menu.add(new User_menu("menu.f003.f004", "I", "Paragraphs nava", "menu.f003", IndexMenuMapper.F.F004, id++));

        menu.add(new User_menu("menu.f004", "I", "Books", null, IndexMenuMapper.F.F004, id++));
        menu.add(new User_menu("menu.f005", "I", "Books", null, IndexMenuMapper.F.F005, id++));

        return menu;
    }
}
