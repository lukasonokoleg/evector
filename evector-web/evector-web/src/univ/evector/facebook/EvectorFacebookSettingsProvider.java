package univ.evector.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import univ.evector.db.dao.FacebookDao;
import eu.itreegroup.spark.facebook.application.FacebookActionHandler;
import eu.itreegroup.spark.facebook.application.FacebookSettingsProvider;

@SuppressWarnings("serial")
@Component
public class EvectorFacebookSettingsProvider implements FacebookSettingsProvider {

    @Autowired
    private FacebookDao facebookDao;

    @Autowired
    private EvectorFacebookActionHandler facebookActionHandler;

    public EvectorFacebookSettingsProvider() {

    }

    @Override
    public boolean isDebugEnabled() {
        boolean retVal = facebookDao.isDebugEnabled();
        return retVal;
    }

    @Override
    public boolean isJsonStoreEnabled() {
        boolean retVal = facebookDao.isJsonStoreEnabled();
        return retVal;
    }

    @Override
    public String getAuthApplicationId() {
        String retVal = facebookDao.getAuthApplicationId();
        return retVal;
    }

    @Override
    public String getAuthApplicationSecret() {
        String retVal = facebookDao.getAuthApplicationSecret();
        return retVal;
    }

    @Override
    public String getAuthPermissions() {
        String retVal = facebookDao.getAuthPermissions();
        return retVal;
    }

    @Override
    public FacebookActionHandler getFacebookActionHandler() {
        return facebookActionHandler;
    }

}
