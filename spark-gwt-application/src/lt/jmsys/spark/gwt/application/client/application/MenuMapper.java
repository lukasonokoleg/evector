package lt.jmsys.spark.gwt.application.client.application;

import eu.itreegroup.spark.application.shared.db.bean.User_menu;
import lt.jmsys.spark.gwt.application.client.application.presenter.NotImplPlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;

public class MenuMapper {

    public ModulePlace getPlace(User_menu item) {
        if (null != item.getFunction_code()){
            String menuCode = item.getMenu_code();
            return new NotImplPlace(menuCode + ":" + item.getFunction_code());
        }else{
            return null;
        }
    }
}
