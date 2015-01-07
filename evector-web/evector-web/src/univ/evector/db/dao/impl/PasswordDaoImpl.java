package univ.evector.db.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.Password;
import univ.evector.beans.PasswordStatus;
import univ.evector.beans.helper.PasswordHelper;
import univ.evector.db.dao.PasswordDao;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class PasswordDaoImpl extends CommonDaoImpl implements PasswordDao {

    private final static BeanPropertyRowMapper<Password> ROW_MAPPER = new BeanPropertyRowMapper<>(Password.class);

    @Override
    public Password findByEmail(String email) {

        String sql = "SELECT "//
                + "usr.usr_id "//
                + "FROM " //
                + "evo_users usr "//
                + "WHERE "//

                + "usr.usr_email = :usr_email ";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("usr_email", email);

        Long usrId = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, Long.class);

        return findByUsrId(usrId);
    }

    @Override
    public Password findByUsrId(Long usrId) {
        String sql = "SELECT "//
                + "psw.psw_id "//
                + "FROM " //
                + "evo_passwords psw "//
                + "WHERE "//
                + "psw.psw_usr_id = :psw_usr_id "//
                + "AND psw.psw_status = :psw_status";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("psw_usr_id", usrId);
        paramMap.put("psw_status", PasswordStatus.CREATED.name());

        Long pswId = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, Long.class);
        return find(pswId);
    }

    @Override
    public void save(Password password, Long usrId) {
        if (usrId == null) {
            throw new IllegalArgumentException();
        }
        deleteCurrentPassword(usrId);
        insertPassword(password, usrId);
    }

    private void insertPassword(Password password, Long usrId) {

        password.setPsw_id(null);
        password.setPsw_status(PasswordStatus.CREATED);

        String sql = "INSERT INTO evo_passwords "//
                + "( "//
                + "psw_usr_id, "//
                + "psw_hash, "//
                + "psw_salt, "//
                + "psw_status, " //
                + "psw_created "//
                + ") "//
                + "values "//
                + "( " //
                + ":psw_usr_id, "// F
                + ":psw_hash, " //
                + ":psw_salt, " //
                + ":psw_status,"//
                + ":psw_created "//
                + ") "//
                + "";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(password);
        

        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.registerSqlType("psw_status", Types.VARCHAR);
        paramSource.addValue("psw_usr_id", usrId);

        namedParamJdbcTemplate.update(sql, paramSource, keyHolder);

        Long psw_id = keyHolder.getKey().longValue();
        ensureLastInsertion(usrId);
        password.setPsw_id(psw_id);
    }

    private void deleteCurrentPassword(Long usrId) {
        Password password = findByUsrId(usrId);
        deletePassword(password);
    }

    private void deletePassword(Password password) {
        if (PasswordHelper.hasId(password)) {
            deletePassword(password.getPsw_id());
        }
    }

    private void deletePassword(Long psw_id) {

        String sql = "UPDATE evo_passwords " //
                + "SET " + //
                "psw_status = :psw_status " //
                + "WHERE psw_id =:psw_id ";

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("psw_status", PasswordStatus.DELETED);
        paramSource.addValue("psw_id", psw_id);
        paramSource.registerSqlType("psw_status", Types.VARCHAR);

        namedParamJdbcTemplate.update(sql, paramSource);
    }

    @Override
    public Password find(Long pswId) {
        String sql = "SELECT "//
                + "psw.* "//
                + "FROM "//
                + "evo_passwords psw "//
                + "WHERE "//
                + "psw.psw_id = :psw_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("psw_id", pswId);

        return namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, ROW_MAPPER);
    }

}
