package eu.itreegroup.spark.application.service;

import java.math.BigDecimal;

import eu.itreegroup.spark.application.bean.Setting;

public interface SettingsService {

    /**
     * Returns value of String setting. Returns default value if setting does not exist.
     * @param setting
     * @param defaultValue
     * @return 
     */
    String getString(Setting setting, String defaultValue);

    /**
     * Returns value of String setting. Throws RuntimeException if setting does not exist.
     * @param setting
     * @param defaultValue
     * @return 
     */
    String getString(Setting setting);

    /**
     * Returns value of Long setting. Returns default value if setting does not exist.
     * @param setting
     * @param defaultValue
     * @return 
     */
    Long getLong(Setting setting, Long defaultValue);

    /**
     * Returns value of Long setting. Throws RuntimeException if setting does not exist.
     * @param setting
     * @param defaultValue
     * @return 
     */
    Long getLong(Setting setting);

    /**
     * Returns value of Boolean setting. Returns default value if setting does not exist.
     * @param setting
     * @param defaultValue
     * @return 
     */
    Boolean getBoolean(Setting setting, Boolean defaultValue);

    /**
     * Returns value of Boolean setting. Throws RuntimeException if setting does not exist.
     * @param setting
     * @param defaultValue
     * @return 
     */
    Boolean getBoolean(Setting setting);

    /**
     * Returns value of BigDecimal setting. Throws RuntimeException if setting does not exist.
     * @param setting
     * @return
     */
    BigDecimal getBigDecimal(Setting setting);
}
