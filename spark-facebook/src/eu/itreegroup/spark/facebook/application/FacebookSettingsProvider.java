package eu.itreegroup.spark.facebook.application;

import java.io.Serializable;

public interface FacebookSettingsProvider extends Serializable {

    boolean isDebugEnabled();

    boolean isJsonStoreEnabled();

    String getAuthApplicationId();

    String getAuthApplicationSecret();

    String getAuthPermissions();

    FacebookActionHandler getFacebookActionHandler();

}
