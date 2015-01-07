package univ.evector.db.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import univ.evector.db.dao.FacebookDao;
import univ.evector.db.dao.SettingsDao;
import univ.evector.db.dao.helper.FacebookSettingName;

@Repository
public class FacebookDaoImpl extends CommonDaoImpl implements FacebookDao {

    @Autowired
    private SettingsDao settingDao;

    @Override
    public String getFaceBookLoginUrl() {
        String guid = FacebookSettingName.GUID;
        String name = FacebookSettingName.FACEBOOK_LOGIN_URL;
        String retVal = settingDao.findSettingAsString(guid, name);
        return retVal;
    }

    @Override
    public boolean isDebugEnabled() {
        String guid = FacebookSettingName.GUID;
        String name = FacebookSettingName.IS_DEBUG_ENABLED;
        Boolean retVal = settingDao.findSettingAsBoolean(guid, name);
        retVal = retVal != null ? retVal : false;
        return retVal;
    }

    @Override
    public String getAuthApplicationId() {
        String guid = FacebookSettingName.GUID;
        String name = FacebookSettingName.APPLICATION_AUTH_ID;
        String retVal = settingDao.findSettingAsString(guid, name);
        return retVal;
    }

    @Override
    public String getAuthApplicationSecret() {
        String guid = FacebookSettingName.GUID;
        String name = FacebookSettingName.APPLICATION_AUTH_SECRET;
        String retVal = settingDao.findSettingAsString(guid, name);
        return retVal;
    }

    @Override
    public boolean isJsonStoreEnabled() {
        String guid = FacebookSettingName.GUID;
        String name = FacebookSettingName.IS_JSON_STORE_ENABLED;
        Boolean retVal = settingDao.findSettingAsBoolean(guid, name);
        retVal = retVal != null ? retVal : false;
        return retVal;
    }

    @Override
    public String getAuthPermissions() {
        String guid = FacebookSettingName.GUID;
        String name = FacebookSettingName.APPLICATION_REQUIRED_PERMISSIONS;
        String retVal = settingDao.findSettingAsString(guid, name);
        return retVal;
    }

}
