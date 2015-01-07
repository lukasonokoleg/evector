package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import univ.evector.beans.Setting;
import univ.evector.db.dao.SettingsDao;

@Repository
public class SettingsDaoImpl extends CommonDaoImpl implements SettingsDao {

    @Override
    public Setting findByName(String name) {
        Setting retVal = null;

        if (name != null) {
            /*    List<Setting> items = settingsReposistory.findByName(name);
                retVal = RepositoryHelper.getFirst(items);*/
        }

        return retVal;
    }

    @Override
    public void save(Setting item) {

    }

    @Override
    public void delete(Setting item) {

    }

    @Override
    public Setting find(eu.itreegroup.spark.application.bean.Setting name) {

        return null;
    }

    @Override
    public String findSettingAsString(String guid, String name) {
        String sql = "SELECT "//
                + "sett.set_value "//
                + ""//
                + "FROM "//
                + "evo_settings sett "//
                + ""//
                + "WHERE "//
                + "sett.set_name = :set_name "//
                + "AND sett.set_guid = :set_guid "//
                + "";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("set_name", name);
        paramMap.put("set_guid", guid);

        String retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, String.class);
        return retVal;
    }

    @Override
    public Boolean findSettingAsBoolean(String guid, String name) {
        String retValAsString = findSettingAsString(guid, name);
        Boolean retVal = Boolean.valueOf(retValAsString);
        return retVal;
    }
}
