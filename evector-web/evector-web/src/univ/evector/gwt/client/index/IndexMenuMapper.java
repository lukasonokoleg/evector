package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.client.application.MenuMapper;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;
import univ.evector.gwt.client.F001.presenter.F001Place;
import univ.evector.gwt.client.F002.presenter.F002Place;
import univ.evector.gwt.client.F003.presenter.F003Place;
import univ.evector.gwt.client.F004.presenter.F004Place;
import univ.evector.gwt.client.F005.presenter.F005Place;
import eu.itreegroup.spark.application.shared.db.bean.User_menu;

public class IndexMenuMapper extends MenuMapper {

    public interface F {

        String NOT_IN_MENU = "nim";
        String F001 = "F001";
        String F002 = "F002";

        String F003 = "F003";

        String F004 = "F004";
        String F005 = "F005";

        String F007 = "F007";
    }

    public IndexMenuMapper() {

    }

    @Override
    public ModulePlace getPlace(User_menu item) {
        String function = item.getFunction_code();
        if (F.F001.equals(function)) {
            return new F001Place();
        } else if (F.F002.equals(function)) {
            return new F002Place();
        } else if (F.F003.equals(function)) {
            return new F003Place();
        } else if (F.F004.equals(function)) {
            return new F004Place();
        } else if (F.F005.equals(function)) {
            return new F005Place();
        } else {
            return super.getPlace(item);
        }
    }
}
