package univ.evector.db.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import univ.evector.beans.LoginKey;
import univ.evector.beans.User;
import univ.evector.db.dao.LoginKeyDao;
import eu.itreegroup.spark.application.helper.SecureKeyUtils;

@Repository
public class LoginKeyDaoImpl extends CommonDaoImpl implements LoginKeyDao {

    public final static RowMapper<LoginKey> LOGIN_KEY_MAPPER = new BeanPropertyRowMapper<LoginKey>(LoginKey.class) {

        @Override
        public LoginKey mapRow(java.sql.ResultSet arg0, int arg1) throws java.sql.SQLException {
            LoginKey retVal = super.mapRow(arg0, arg1);
            User user = UserDaoImpl.USER_MAPPER.mapRow(arg0, arg1);
            retVal.setUser(user);
            return retVal;
        };

    };

    public LoginKey findByValue(String value) {
        LoginKey retVal = null;
        return retVal;
    }

    public void setExpired(String loginKey, User user) {

    }

    public void save(LoginKey loginKey) {

    }

    public LoginKey createLoginKey(User user, String lgkWorkplace) {
        if (user == null || lgkWorkplace == null) {
            throw new IllegalArgumentException("Input variables has NULL value. user: " + user + ", lgkWorkplace: " + lgkWorkplace);
        }
        LoginKey retVal = null;
        if (hasActiveLoginKey(user, lgkWorkplace)) {
            retVal = findByUserAndWorkPlaceUid(user, lgkWorkplace);
        } else {
            retVal = new LoginKey();
            String lgkKey = SecureKeyUtils.generateRanfomSalt128();
            Date lgkCreated = new Date();

            retVal.setUser(user);
            retVal.setLgk_key(lgkKey);
            retVal.setLgk_workplace(lgkWorkplace);
            retVal.setLgk_created(lgkCreated);
        }
        return retVal;
    }

    private boolean hasActiveLoginKey(User user, String workPlaceUid) {
        boolean retVal = false;
        LoginKey loginKey = findByUserAndWorkPlaceUid(user, workPlaceUid);
        if (loginKey != null) {
            retVal = true;
        }
        return retVal;
    }

    private LoginKey findByUserAndWorkPlaceUid(User user, String workPlaceUid) {

        String sql = "SELECT "//
                + "lgk.*, "//
                + "usr.* "//
                + ""//
                + "FROM "//
                + "evo_login_keys lgk, "//
                + "evo_users usr "//
                + "WHERE "//
                + "usr.usr_id = lgk.lgk_usr_id "//
                + "AND lgk.lgk_workplace = :lgk_workplace "//
                + ""//
                + "";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lgk_workplace", workPlaceUid);

        LoginKey retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, LOGIN_KEY_MAPPER);
        return retVal;
    }

}
