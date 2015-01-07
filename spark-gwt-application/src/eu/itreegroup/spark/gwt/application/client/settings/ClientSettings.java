package eu.itreegroup.spark.gwt.application.client.settings;

import java.util.Map;

import eu.itreegroup.spark.application.bean.Setting;

public class ClientSettings {

    private static Map<Setting, String> settings;

    public static void init(Map<Setting, String> settings) {
        ClientSettings.settings = settings;
    }

    public static String getSetting(Setting setting) {
        if (null == settings) {
            throw new IllegalStateException("Please initialize settings before getSetting");
        }
        return settings.get(setting);
    }
}
