package univ.evector.db.dao.impl;

import javax.sql.DataSource;

import lt.jmsys.spark.gwt.suggest.oracle.EvectorTransliterationHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import eu.itreegroup.spark.application.service.mysql.BrowseJdbcHelper;
import eu.itreegroup.spark.application.service.mysql.EvectorNamedParameterJdbcTemplate;

@Repository
public class CommonDaoImpl {

    protected final static int INT_0 = 0;

    protected JdbcTemplate jdbcTemplate;

    protected EvectorNamedParameterJdbcTemplate namedParamJdbcTemplate;

    protected BrowseJdbcHelper browseJdbcHelper;

    @Autowired
    protected EvectorTransliterationHelper transliterationHelper;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParamJdbcTemplate = new EvectorNamedParameterJdbcTemplate(dataSource);
        browseJdbcHelper = new BrowseJdbcHelper(dataSource, namedParamJdbcTemplate);
    }

    protected static void checkForNotNullId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Input variable id has NULL value.");
        }
    }

    protected void ensureLastInsertion(Long lastInsertedId) {
        if (lastInsertedId == null) {
            throw new RuntimeException("Database data error: select LAST_INSERT_ID() returned NULL value for last inserted id.");
        } else if (lastInsertedId.longValue() == 0) {
            throw new RuntimeException("Database data error: select LAST_INSERT_ID() returned 0 value for last inserted id.");
        }
    }

    protected static String formatQuery(String query) {
        return "%" + query + "%";
    }

}