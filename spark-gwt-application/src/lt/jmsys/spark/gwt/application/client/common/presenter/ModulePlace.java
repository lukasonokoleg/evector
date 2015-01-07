package lt.jmsys.spark.gwt.application.client.common.presenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lt.jmsys.spark.gwt.application.client.application.resource.BreadCrumbsConstants;
import lt.jmsys.spark.gwt.application.client.application.resource.MenuConstants;
import lt.jmsys.spark.gwt.application.common.client.application.ApplicationPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class ModulePlace extends Place implements ApplicationPlace {

    /**
     * MenuCode - property name;
     */
    protected static final String PROP_MENU_CODE = "menu";
    private final static String MENU_DELIMETER = "_";

    private final static DateTimeFormat defaultDateFormat = DateTimeFormat.getFormat("yyyy.MM.dd");

    private static RegExp RE = RegExp.compile("([A-Z]*[0-9]*).*");

    public static enum Module {
        ALL, CR, CM, MF, SYS, HOME, PUBLIC;
    }

    protected Map<String, String> properties = new HashMap<String, String>();
    protected Map<String, Object> objectProperties = new HashMap<String, Object>();
    private Module module;

    public ModulePlace(Module module, String menuCode) {
        setModule(module);
        setMenuCode(menuCode);
    }

    private void setModule(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    @Override
    public final String getMenuCode() {
        String retVal = decodeMenuCode(properties.get(PROP_MENU_CODE));
        return retVal;
    }

    private final static String codeMenuCode(String menuCode) {
        if (menuCode != null) {
            String retVal = menuCode.replace(PlaceWithId.Tokenizer.DELIMETER, MENU_DELIMETER);
            return retVal;
        }
        return null;
    }

    private final static String decodeMenuCode(String menuCode) {
        if (menuCode != null) {
            String retVal = menuCode.replace(MENU_DELIMETER, PlaceWithId.Tokenizer.DELIMETER);
            return retVal;
        }
        return null;
    }

    @Override
    public final void setMenuCode(String menuCode) {
        setStringProperty(PROP_MENU_CODE, codeMenuCode(menuCode));
    }

    public void setStringProperty(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Input variable key is NULL!");
        }
        properties.put(key, value);
    }

    public void setDoubleProperty(String key, Double value) {
        setStringProperty(key, doubleToString(value));
    }

    public Double getDoublePropValue(String key) {
        String propVal = getStringProperty(key);
        if (propVal != null && !propVal.isEmpty()) {
            return Double.valueOf(propVal);
        }
        return null;
    }

    public void setLongProperty(String key, Long value) {
        setStringProperty(key, longToString(value));
    }

    public Long getLongPropValue(String key) {
        String propVal = getStringProperty(key);
        if (propVal != null && !propVal.isEmpty()) {
            return Long.valueOf(propVal);
        }
        return null;
    }

    public void setObjectProperty(String key, Object value) {
        objectProperties.put(key, value);
    }

    public Object getObjectProperty(String key) {
        return objectProperties.get(key);
    }

    private static String doubleToString(Double id) {
        if (id != null) {
            return Long.toString(id.longValue());
        }
        return null;
    }

    private static String longToString(Long id) {
        if (id != null) {
            return id.toString();
        }
        return null;
    }

    public void setBooleanProperty(String key, Boolean value) {
        setStringProperty(key, value.toString());
    }

    public Boolean getBooleanProperty(String key) {
        String propVal = getStringProperty(key);
        if (propVal != null && !propVal.isEmpty()) {
            return Boolean.valueOf(propVal);
        }
        return null;
    }

    public String getStringProperty(String key) {
        if (key != null) {
            return properties.get(key);
        }
        return null;
    }

    public Date getDateProperty(String key) {
        String keyPropValue = getStringProperty(key);
        Date retVal = defaultDateFormat.parseStrict(keyPropValue);
        return retVal;
    }

    public void setDateProperty(String key, Date date) {
        String dateStr = null;
        if (date != null) {
            dateStr = defaultDateFormat.format(date);
        }
        setStringProperty(key, dateStr);
    }

    protected Map<String, String> getProperties() {
        return properties;
    }

    protected Map<String, Object> getObjectProperties() {
        return objectProperties;
    }

    protected void setObjectProperties(Map<String, Object> objectProperties) {
        this.objectProperties = objectProperties;
    }

    private static MenuConstants MENU_CONSTS;

    private final static MenuConstants getMenuConsts() {
        if (MENU_CONSTS == null) {
            MENU_CONSTS = GWT.create(MenuConstants.class);
        }
        return MENU_CONSTS;
    }

    private static BreadCrumbsConstants BREAD_CR_CONSTS;

    private final static BreadCrumbsConstants getBreadCrumbsConsts() {
        if (BREAD_CR_CONSTS == null) {
            BREAD_CR_CONSTS = GWT.create(BreadCrumbsConstants.class);
        }
        return BREAD_CR_CONSTS;
    }

    public String getName() {
        String name = null;
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        name = getBreadCrumbsConsts().getString(className);
        if (name != null && name.trim().isEmpty()) {
            name = null;
        }
        if (name == null && null != getMenuCode()) {
            name = getMenuConsts().getMessage(getMenuCode());
        }
        if (null == name) {
            name = className;
        }
        return name;
    }

    public String getHelpId() {
        String helpId = getClass().getName();
        helpId = helpId.substring(helpId.lastIndexOf('.') + 1);
        MatchResult m = RE.exec(helpId);
        if (null != m) {
            helpId = m.getGroup(1);
        }
        return helpId;
    }

}
