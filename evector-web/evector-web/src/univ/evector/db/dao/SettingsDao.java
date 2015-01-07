package univ.evector.db.dao;

import org.springframework.stereotype.Repository;

import univ.evector.beans.Setting;

@Repository
public interface SettingsDao {

    String findSettingAsString(String guid, String name);

    Boolean findSettingAsBoolean(String guid, String name);

    Setting findByName(String name);

    Setting find(eu.itreegroup.spark.application.bean.Setting item);

    void save(Setting item);

    void delete(Setting item);

}
