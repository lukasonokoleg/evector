package eu.itreegroup.spark.application.service.mysql;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;

import univ.evector.beans.UserSession;
import univ.evector.internal.service.UserService;

public class EvectorNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {

    private final static Logger LOGGER = Logger.getLogger(EvectorNamedParameterJdbcTemplate.class);

    private final static int INT_0 = 0;

    public EvectorNamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public EvectorNamedParameterJdbcTemplate(JdbcOperations classicJdbcTemplate) {
        super(classicJdbcTemplate);
    }

    public <T> T queryForObjectNullable(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws DataAccessException {
        return queryForObjectNullable(sql, paramMap, new SingleColumnRowMapper<T>(requiredType));
    }

    public <T> T queryForObjectNullable(String sql, SqlParameterSource paramSource, Class<T> requiredType) throws DataAccessException {
        return queryForObjectNullable(sql, paramSource, new SingleColumnRowMapper<T>(requiredType));
    }

    public <T> T queryForObjectNullable(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws DataAccessException {
        return queryForObjectNullable(sql, new MapSqlParameterSource(paramMap), rowMapper);
    }

    public <T> T queryForObjectNullable(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = getJdbcOperations().query(getPreparedStatementCreator(sql, paramSource), rowMapper);
        T retVal = null;
        if (results != null && results.size() > INT_0) {
            retVal = results.get(INT_0);
        }
        return retVal;
    }

}
