package eu.itreegroup.spark.application.service.mysql;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import lt.jmsys.spark.gwt.client.ui.browse.column.ColumnMetaData;
import lt.jmsys.spark.gwt.client.ui.browse.column.SortOrder;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class BrowseJdbcHelper {

    private final DataSource dataSource;

    private final EvectorNamedParameterJdbcTemplate namedParamJdbcTemplate;

    public BrowseJdbcHelper(DataSource dataSource, EvectorNamedParameterJdbcTemplate namedParamJdbcTemplate) {
        this.dataSource = dataSource;
        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public <E extends Serializable> QueryResult<E> browse(String selectClause, String fromClause, String whereClause, QueryMetaData queryMetaData, RowMapper<E> rowMapper,
            Map<String, ?> paramMap) {

        String sql = getBrowseSql(selectClause, fromClause, whereClause, queryMetaData);
        String countSql = getCountSql(fromClause, whereClause);

        List<E> result = namedParamJdbcTemplate.query(sql, paramMap, rowMapper);
        Integer count = namedParamJdbcTemplate.queryForObject(countSql, paramMap, Integer.class);

        return getQueryResult(queryMetaData, count, result);
    }

    private static String getBrowseSql(String select, String from, String where, QueryMetaData queryMetaData) {
        String retVal = select + " " + from + " " + where + BrowseJdbcHelper.getOrderAndPagingClause(queryMetaData);
        return retVal;
    }

    private static String getCountSql(String fromClause, String whereClause) {
        String retVal = "select count(*) " + fromClause + " " + whereClause;
        return retVal;
    }

    private static <E extends Serializable> QueryResult<E> getQueryResult(QueryMetaData queryMetaData, Integer count, List<E> result) {
        QueryResult<E> retVal = new QueryResult<>();
        retVal.setData(new LinkedList<>(result));
        retVal.setTotalCount(count);
        retVal.setPageNumber(queryMetaData.getPageNumber());
        retVal.setPageSize(queryMetaData.getPageSize());
        return retVal;
    }

    public <E extends Serializable> QueryResult<E> browse(String selectClause, String fromClause, String whereClause, SqlParameterSource paramSource, RowMapper<E> rowMapper,
            QueryMetaData queryMetaData) {
        String sql = selectClause + " " + fromClause + " " + whereClause + BrowseJdbcHelper.getOrderAndPagingClause(queryMetaData);

        List<E> sqlResult = namedParamJdbcTemplate.query(sql, paramSource, rowMapper);

        String countSql = "select count(*) " + fromClause + " " + whereClause;
        Integer count = namedParamJdbcTemplate.queryForObject(countSql, paramSource, Integer.class);

        QueryResult<E> result = new QueryResult<>();
        result.setData(new LinkedList<>(sqlResult));
        result.setTotalCount(count);
        result.setPageNumber(queryMetaData.getPageNumber());
        result.setPageSize(queryMetaData.getPageSize());

        return result;
    }

    public static String getOrderAndPagingClause(QueryMetaData queryMetaData) {

        StringBuilder builder = new StringBuilder();

        int skipRows = queryMetaData.getSkipRows();
        int pageSize = queryMetaData.getPageSize();
        ColumnMetaData sortColumn = queryMetaData.getSortColumn();

        if (sortColumn != null) {
            if (SortOrder.ASCENDING.equals(sortColumn.getSortOrder())) {
                builder.append(" order by ");
                builder.append(sortColumn.getName());
                builder.append(" asc");
            } else if (SortOrder.DESCENDING.equals(sortColumn.getSortOrder())) {
                builder.append(" order by ");
                builder.append(sortColumn.getName());
                builder.append(" desc");
            }
        }

        builder.append(" limit ");
        builder.append(skipRows);
        builder.append(",");
        builder.append(pageSize);

        return builder.toString();
    }
}
