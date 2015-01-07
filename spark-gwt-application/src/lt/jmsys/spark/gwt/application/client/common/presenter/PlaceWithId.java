package lt.jmsys.spark.gwt.application.client.common.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lt.jmsys.spark.gwt.application.client.helper.State;
import lt.jmsys.spark.gwt.application.client.helper.StateManager;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import com.google.gwt.place.shared.Place;

public class PlaceWithId extends ModulePlace {

    public static final String MODE_DETAILS = "D";
    public static final String MODE_MASTER_DETAILS = "MD";

    private static final String PROP_ID = "id";
    private static final String PROP_STATE_ID = "state";
    private static final String PROP_MODE = "mode";
    private static long _id_gen = System.currentTimeMillis();

    private static final List<String> propNames = new ArrayList<String>();
    static {
        propNames.add(PROP_ID);
        propNames.add(PROP_STATE_ID);
        propNames.add(PROP_MODE);
    }

    public PlaceWithId(Number id) {
        this(null, null, id);
    }

    public PlaceWithId(Module module, String menuCode, Number id) {
        this(module, menuCode, id, null);
    }

    public PlaceWithId(Module module, String menuCode, Number id, String mode) {
        super(module, menuCode);
        setId(id);
        setStateId(Long.toString(_id_gen++));
        setMode(mode);
    }

    public Double getId() {
        return getDoublePropValue(PROP_ID);
    }

    public Long getIdLong() {
        Double id = getId();
        return null != id ? id.longValue() : null;
    }

    public void setId(Number id) {
        setDoubleProperty(PROP_ID, id != null ? id.doubleValue() : null);
    }

    public String getStateId() {
        return getStringProperty(PROP_STATE_ID);
    }

    public void setStateId(String stateId) {
        setStringProperty(PROP_STATE_ID, stateId);
    }

    public String getMode() {
        return getStringProperty(PROP_MODE);
    }

    public void setMode(String mode) {
        setStringProperty(PROP_MODE, mode);
    }

    public boolean isDetailsMode() {
        return MODE_DETAILS.equals(getMode());
    }

    public abstract static class Tokenizer<E extends PlaceWithId> {

        private static StateManager<Map<String, String>> stateManager = new StateManager<Map<String, String>>();

        private static StateManager<Map<String, Object>> objectPropertiesManager = new StateManager<Map<String, Object>>();

        public final static String DELIMETER = ".";

        private final static String DELIMETER_REGEX = "\\.";

        private final static String VALUE_DELIMETER = "=";

        private static final String ESCAPE_SYMBOL = "\\";

        private static final String SLASH = "\\";

        private static final String SLASH_ESCAPE = ESCAPE_SYMBOL + SLASH;

        private static final String DOT = ".";

        private static final String DOT_ESCAPE = ESCAPE_SYMBOL + DOT;

        private static final String EQUALS_SIGN = "=";

        private static final String EQUALS_SIGN_ESCAPE = ESCAPE_SYMBOL + EQUALS_SIGN;

        private static final int ZERO_INDEX = 0;

        private static final String escapeString(String value) {
            if (value != null) {
                value = value.replace(SLASH, SLASH_ESCAPE);
                value = value.replace(DOT, DOT_ESCAPE);
                value = value.replace(EQUALS_SIGN, EQUALS_SIGN_ESCAPE);
            }
            return value;
        }

        public String getToken(E place) {
            StringBuilder builder = new StringBuilder();
            Map<String, String> properties = place.getProperties();
            int size = properties.size();
            if (size > 0) {
                boolean first = true;
                for (String key : properties.keySet()) {
                    String value = properties.get(key);
                    if (value != null) {
                        if (!first) {
                            builder.append(DELIMETER);
                        } else {
                            first = false;
                        }
                        builder.append(escapeString(key));
                        builder.append(VALUE_DELIMETER);
                        builder.append(escapeString(value));
                    }
                }
            }
            stateManager.updateState(place.getStateId(), null, place.getProperties());
            objectPropertiesManager.updateState(place.getStateId(), null, place.getObjectProperties());
            return builder.toString();
        }

        private static boolean hasEscapeSuffix(String value, String escapeSymbol) {
            int escapeAmount = 0;
            if (value != null && escapeSymbol != null) {
                String checkString = escapeSymbol;
                while (value.endsWith(checkString)) {
                    escapeAmount++;
                    checkString = checkString + escapeSymbol;
                }
            }
            return escapeAmount > 0 && escapeAmount % 2 != 0;
        }

        private static String[] splitWithinEcaped(String value, String splitRegex, String splitSymbol, String escapeSymbol) {
            List<String> retVal = new ArrayList<String>();
            if (value != null && value.length() > 0) {
                String[] array = value.split(splitRegex);
                String tmpValue = "";
                if (array != null) {
                    int arraySize = array.length;
                    if (arraySize > 0) {
                        for (int index = 0; index < arraySize; index++) {
                            String item = array[index];
                            if (item != null) {
                                int itemLength = item.length();
                                if (itemLength > 0 && item.endsWith(escapeSymbol) && hasEscapeSuffix(item, escapeSymbol) && index < arraySize - 1) {
                                    int escSymLen = ESCAPE_SYMBOL.length();
                                    tmpValue = tmpValue + item.substring(ZERO_INDEX, itemLength - escSymLen) + splitSymbol;
                                } else {
                                    tmpValue = tmpValue + item;
                                    retVal.add(tmpValue);
                                    tmpValue = "";
                                }
                            }
                        }
                    }
                }
            }
            return retVal.toArray(new String[retVal.size()]);
        }

        private void parseProperty(String argument, E place) {
            String[] propStrings = splitWithinEcaped(argument, EQUALS_SIGN, EQUALS_SIGN, ESCAPE_SYMBOL);
            if (propStrings != null && propStrings.length > 1) {
                String tmpKey = propStrings[0];
                String tmpValue = propStrings[1];
                String key = tmpKey.replace(SLASH_ESCAPE, SLASH);
                String value = tmpValue.replace(SLASH_ESCAPE, SLASH);
                place.setStringProperty(key, value);
            }
        }

        public E getPlace(String token) {
            E place = getPlace();
            String[] propsAsStrings = splitWithinEcaped(token, DELIMETER_REGEX, DELIMETER, ESCAPE_SYMBOL);
            for (String propString : propsAsStrings) {
                parseProperty(propString, place);
            }
            setPropertiesFromState(place, stateManager.getState(place.getStateId()));
            Map<String, Object> objectProperties = null;
            State<Map<String, Object>> objectPropertiesState = objectPropertiesManager.getState(place.getStateId());
            if (null != objectPropertiesState) {
                objectProperties = objectPropertiesState.getItem();
            }
            place.setObjectProperties(null != objectProperties ? objectProperties : new HashMap<String, Object>());
            return place;
        }

        public abstract E getPlace();

        protected void setPropertiesFromState(E place, State<Map<String, String>> state) {
            if (null != state) {
                Map<String, String> stateProperties = state.getItem();
                for (Entry<String, String> prop : stateProperties.entrySet()) {
                    if (ConversionHelper.isNotEmpty(prop.getValue()) && !propNames.contains(prop.getKey())) {
                        place.setStringProperty(prop.getKey(), prop.getValue());
                    }
                }
            }
        }
    }

    public static Double getId(Place place) {
        if (place instanceof PlaceWithId) {
            return ((PlaceWithId) place).getId();
        } else {
            return null;
        }
    }
}
