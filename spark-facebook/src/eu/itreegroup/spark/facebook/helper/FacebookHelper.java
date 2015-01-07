package eu.itreegroup.spark.facebook.helper;

import eu.itreegroup.spark.facebook.application.FacebookSettingsProvider;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookHelper {

    public static Configuration getFacebookConfiguration(FacebookSettingsProvider settingsProvider) {
        String authAppId = settingsProvider.getAuthApplicationId();
        String authAppSecret = settingsProvider.getAuthApplicationSecret();
        String authPermissions = settingsProvider.getAuthPermissions();

        boolean isDebugEnabled = settingsProvider.isDebugEnabled();
        boolean isJsonStoreEnabled = settingsProvider.isJsonStoreEnabled();

        ConfigurationBuilder builder = new ConfigurationBuilder()//
                .setOAuthAppId(authAppId)//
                .setOAuthAppSecret(authAppSecret)//
                .setDebugEnabled(isDebugEnabled)//
                .setOAuthPermissions(authPermissions)//
                .setJSONStoreEnabled(isJsonStoreEnabled);

        return builder.build();
    }

    public static Facebook getFacebook(FacebookSettingsProvider settingsProvider) {
        Configuration configuration = FacebookHelper.getFacebookConfiguration(settingsProvider);
        Facebook retVal = new FacebookFactory(configuration).getInstance();
        return retVal;
    }

}
