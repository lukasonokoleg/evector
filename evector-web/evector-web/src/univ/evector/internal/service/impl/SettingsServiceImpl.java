package univ.evector.internal.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.helper.SettingsHelper;
import univ.evector.db.dao.SettingsDao;
import eu.itreegroup.spark.application.bean.Setting;
import eu.itreegroup.spark.application.service.SettingsService;

@Service
@Transactional
public class SettingsServiceImpl implements SettingsService {

    // TODO: implement system settings caching and cache refreshing...

    @Autowired
    private SettingsDao settingsDao;

    @Override
    public String getString(Setting name, String defaultValue) {
        String retVal = getString(name);
        if (retVal == null) {
            retVal = defaultValue;
        }
        return retVal;
    }

    @Override
    public String getString(Setting name) {
        String retVal = null;
        univ.evector.beans.Setting retValAsSetting = settingsDao.find(name);
        if (retValAsSetting != null) {
            retVal = retValAsSetting.getSet_value();
        }
        return retVal;
    }

    @Override
    public Long getLong(Setting setting, Long defaultValue) {
        Long retVal = getLong(setting);
        if (retVal == null) {
            retVal = defaultValue;
        }
        return retVal;
    }

    @Override
    public Long getLong(Setting name) {
        univ.evector.beans.Setting retValAsSetting = settingsDao.find(name);
        return SettingsHelper.getValueAsLong(retValAsSetting);
    }

    @Override
    public Boolean getBoolean(Setting name, Boolean defaultValue) {
        Boolean retVal = getBoolean(name);
        if (retVal == null) {
            retVal = defaultValue;
        }
        return retVal;
    }

    @Override
    public Boolean getBoolean(Setting name) {
        univ.evector.beans.Setting retValAsSetting = settingsDao.find(name);
        return SettingsHelper.getValueAsBoolean(retValAsSetting);
    }

    @Override
    public BigDecimal getBigDecimal(Setting name) {
        univ.evector.beans.Setting retValAsSetting = settingsDao.find(name);
        return SettingsHelper.getValueAsBigDecimal(retValAsSetting);
    }
}
