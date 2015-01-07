package eu.itreegroup.spark.application.service.mysql;

import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class VvsisSqlParameterSource extends AbstractSqlParameterSource {

    private final SqlParameterSource sqlParameterSource;
    private MapSqlParameterSource mapParamSqlSource = new MapSqlParameterSource();

    public VvsisSqlParameterSource(SqlParameterSource sqlParameterSource) {
        if (sqlParameterSource == null) {
            throw new IllegalArgumentException("Input variable sqlParameterSource can not have null value.");
        }
        this.sqlParameterSource = sqlParameterSource;
    }

    public void addValue(String paramName, Object value) {
        mapParamSqlSource.addValue(paramName, value);
    }

    public boolean hasValue(String parameter) {
        boolean retVal = sqlParameterSource.hasValue(parameter) || mapParamSqlSource.hasValue(parameter);
        return retVal;
    }

    public Object getValue(String parameter) {
        Object retVal = sqlParameterSource.hasValue(parameter) ? sqlParameterSource.getValue(parameter) : mapParamSqlSource.getValue(parameter);
        return retVal;
    }

    @Override
    public void registerSqlType(String paramName, int sqlType) {
        if (sqlParameterSource.hasValue(paramName)) {
            super.registerSqlType(paramName, sqlType);
        } else {
            mapParamSqlSource.registerSqlType(paramName, sqlType);
        }
    }

    public int getSqlType(String parameter) {
        int retVal;
        if (sqlParameterSource.hasValue(parameter)) {
            retVal = super.getSqlType(parameter);
        } else {
            retVal = mapParamSqlSource.getSqlType(parameter);
        }
        return retVal;
    }

}