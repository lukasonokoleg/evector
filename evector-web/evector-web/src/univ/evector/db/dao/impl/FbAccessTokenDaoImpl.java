package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import univ.evector.beans.facebook.FbAccessToken;
import univ.evector.db.dao.FbAccessTokenDao;
import facebook4j.auth.AccessToken;

@Repository
public class FbAccessTokenDaoImpl extends CommonDaoImpl implements FbAccessTokenDao {

    public final static RowMapper<FbAccessToken> FB_ACCESS_TOKEN_ROW_MAPPER = new BeanPropertyRowMapper<>(FbAccessToken.class);

    @Override
    public void saveAccessToken(Long usrId, AccessToken accessToken) throws SparkBusinessException {

        String code = accessToken.getToken();

        String sql = "INSERT INTO fb_access_tokens "//
                + "( "//
                + "act_usr_id, "//
                + "act_code, "//
                + "act_date "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":act_usr_id, "//
                + ":code, "//
                + "now() "//
                + ") "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("act_usr_id", usrId);
        paramMap.put("code", code);

        namedParamJdbcTemplate.update(sql, paramMap);

    }

    @Override
    public FbAccessToken lastUserFbAccessToken(Long usrId) throws SparkBusinessException {
        if (usrId == null) {
            String message = "User ID must be specified.";
            throw new NullPointerException(message);
        }

        String sql = "SELECT "//
                + "* "//
                + "FROM "//
                + "fb_access_tokens act "//
                + "WHERE "//
                + "act.act_usr_id = :user_id "//
                + "LIMIT 0,1 "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", usrId);

        FbAccessToken retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, FB_ACCESS_TOKEN_ROW_MAPPER);
        return retVal;
    }
}
