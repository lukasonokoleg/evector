package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.User;
import univ.evector.beans.UserSession;
import univ.evector.db.dao.UserDao;
import univ.evector.db.dao.UserSessionDao;

@Repository
public class UserSessionDaoImpl extends CommonDaoImpl implements UserSessionDao {

    private static final BeanPropertyRowMapper<UserSession> USER_SESSION_MAPPER = new BeanPropertyRowMapper<UserSession>(UserSession.class) {

        @Override
        public UserSession mapRow(java.sql.ResultSet arg0, int arg1) throws java.sql.SQLException {
            UserSession retVal = super.mapRow(arg0, arg1);
            User user = UserDaoImpl.USER_MAPPER.mapRow(arg0, arg1);
            retVal.setUser(user);
            return retVal;
        };

    };

    @Autowired
    private UserDao userDao;

    public void closeSession(UserSession session) {

    }

    public void insertNewSession(UserSession session) {
        if (session == null) {
            throw new IllegalArgumentException();
        }

        String sql = "INSERT INTO evo_sessions_open " //
                + "( "//
                + "ses_usr_id, " //
                + "ses_key  " //
                + ") " //
                + "VALUES " //
                + "( " //
                + ":user.usr_id, "//
                + ":ses_key  " //
                + ") ";

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(session);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        Long ses_id = generatedKeyHolder.getKey().longValue();
        session.setSes_id(ses_id);

    }

    public void updateLastActionTime(UserSession session) {

    }

    public UserSession findByKey(String key) {

        String sql = "SELECT "//
                + "ses.* "//
                + "FROM "//
                + "evo_sessions_open ses "//
                + "WHERE "//
                + "ses.ses_key =:ses_key ";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(":ses_key", key);
        UserSession retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, USER_SESSION_MAPPER);
        return retVal;
    }

    public UserSession findOpenUserSessionByKey(String key) {

        String sql = "SELECT "//
                + "ses.*, "//
                + "usr.* "//
                + "FROM "//
                + "evo_sessions_open ses, "//
                + "evo_users usr "//
                + "WHERE "//
                + "ses.ses_key =:ses_key "//
                + "AND ses.ses_usr_id = usr.usr_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ses_key", key);
        UserSession retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, USER_SESSION_MAPPER);
        return retVal;
    }

}
