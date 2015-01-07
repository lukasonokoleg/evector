package univ.evector.beans.helper;

import java.math.BigDecimal;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import org.apache.log4j.Logger;

import univ.evector.beans.Setting;

public class SettingsHelper {

    private final static Logger LOGGER = Logger.getLogger(SettingsHelper.class);

    public static boolean hasName(Setting setting, String name) {
        boolean retVal = false;
        if (setting != null && setting.getSet_name() != null) {
            retVal = setting.getSet_name().equals(name);
        }
        return retVal;
    }

    public static boolean hasValue(Setting setting, String value) {
        boolean retVal = false;
        if (setting != null && setting.getSet_name() != null) {
            retVal = setting.getSet_value().equals(value);
        }
        return retVal;
    }

    public static boolean hasValue(Setting setting, Double value) {
        boolean retVal = false;
        if (setting != null && getValueAsDouble(setting) != null) {
            retVal = getValueAsDouble(setting).equals(value);
        }
        return retVal;
    }

    public static boolean hasValue(Setting setting, Integer value) {
        boolean retVal = false;
        if (setting != null && getValueAsInteger(setting) != null) {
            retVal = getValueAsInteger(setting).equals(value);
        }
        return retVal;
    }

    public static boolean hasValue(Setting setting, Long value) {
        boolean retVal = false;
        if (setting != null && getValueAsLong(setting) != null) {
            retVal = getValueAsLong(setting).equals(value);
        }
        return retVal;
    }

    public static boolean hasValue(Setting setting, Boolean value) {
        boolean retVal = false;
        if (setting != null && getValueAsBoolean(setting) != null) {
            retVal = getValueAsBoolean(setting).equals(value);
        }
        return retVal;
    }

    public static boolean hasValue(Setting setting, BigDecimal value) {
        boolean retVal = false;
        if (setting != null && getValueAsBigDecimal(setting) != null) {
            retVal = getValueAsBigDecimal(setting).equals(value);
        }
        return retVal;
    }

    public static Long getValueAsLong(Setting value) {
        Long retVal = null;
        if (value != null) {
            String retValAsString = value.getSet_value();
            if (!ConversionHelper.isEmpty(retValAsString)) {
                try {
                    retVal = Long.valueOf(retValAsString);
                } catch (NumberFormatException e) {
                    LOGGER.error("Cought NumberFormatException, while converting value: " + retValAsString, e);
                }
            }
        }
        return retVal;
    }

    public static Double getValueAsDouble(Setting value) {
        Double retVal = null;
        if (value != null) {
            String retValAsString = value.getSet_value();
            if (!ConversionHelper.isEmpty(retValAsString)) {
                try {
                    retVal = Double.valueOf(retValAsString);
                } catch (NumberFormatException e) {
                    LOGGER.error("Cought NumberFormatException, while converting value: " + retValAsString, e);
                }
            }
        }
        return retVal;
    }

    public static Integer getValueAsInteger(Setting value) {
        Integer retVal = null;
        if (value != null) {
            String retValAsString = value.getSet_value();
            if (!ConversionHelper.isEmpty(retValAsString)) {
                try {
                    retVal = Integer.valueOf(retValAsString);
                } catch (NumberFormatException e) {
                    LOGGER.error("Cought NumberFormatException, while converting value: " + retValAsString, e);
                }
            }
        }
        return retVal;
    }

    public static BigDecimal getValueAsBigDecimal(Setting value) {
        BigDecimal retVal = null;
        if (value != null) {
            String retValAsString = value.getSet_value();
            if (!ConversionHelper.isEmpty(retValAsString)) {
                try {
                    retVal = new BigDecimal(retValAsString);
                } catch (NumberFormatException e) {
                    LOGGER.error("Cought NumberFormatException, while converting value: " + retValAsString, e);
                }
            }
        }
        return retVal;
    }

    public static Boolean getValueAsBoolean(Setting value) {
        Boolean retVal = null;
        if (value != null) {
            String retValAsString = value.getSet_value();
            if (!ConversionHelper.isEmpty(retValAsString)) {
                try {
                    retVal = Boolean.valueOf(retValAsString);
                } catch (NumberFormatException e) {
                    LOGGER.error("Cought NumberFormatException, while converting value: " + retValAsString, e);
                }
            }
        }
        return retVal;
    }

}
