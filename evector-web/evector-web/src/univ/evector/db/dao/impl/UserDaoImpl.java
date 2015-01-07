package univ.evector.db.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.User;
import univ.evector.beans.helper.UserHelper;
import univ.evector.beans.privileges.EvectorDocumentFunction;
import univ.evector.beans.privileges.EvectorDocumentType;
import univ.evector.db.dao.UserDao;
import eu.itreegroup.spark.application.shared.security.DocumentId;
import eu.itreegroup.spark.application.shared.security.DocumentPrivilege;
import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;

@Repository
public class UserDaoImpl extends CommonDaoImpl implements UserDao {

    private final static Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    public final static BeanPropertyRowMapper<User> USER_MAPPER = new BeanPropertyRowMapper<User>(User.class);

    public void saveUser(User user) {
        if (ConversionHelper.isNull(user)) {
            throw new IllegalArgumentException();
        }
        if (ConversionHelper.isNull(user.getUserId())) {
            insertUser(user);
        } else {
            updateUser(user);
        }

    }

    private void insertUser(User user) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        paramSource.registerSqlType("usr_status", Types.VARCHAR);

        String sql = "INSERT INTO evo_users "//
                + "( "//

                + "usr_first_name, "//
                + "usr_last_name, "//
                + "usr_email, " //
                + "usr_status "//
                + ") "//
                + "VALUES "//
                + "( "//

                + ":usr_first_name, "//
                + ":usr_last_name, "//
                + ":usr_email, " //
                + ":usr_status " //
                + ")"//
                + "";

        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long usrId = generatedKeyHolder.getKey().longValue();
        ensureLastInsertion(usrId);
        user.setUsr_id(usrId);
    }

    private void updateUser(User user) {
        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        paramSource.registerSqlType("usr_status", Types.VARCHAR);
        String sql = "UPDATE evo_users "//
                + "SET " + "usr_first_name =:usr_first_name, "//
                + "usr_last_name =:usr_last_name, "//
                + "WHERE "//
                + "usr_id = :usr_id "//
                + "";
        namedParamJdbcTemplate.update(sql, paramSource);

    }

    public User find(Long usrId) {

        String sql = "SELECT " //
                + "usr.* "//
                + "FROM "//
                + "evo_users usr "//
                + "WHERE " //
                + "usr.usr_id = :usr_id ";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("usr_id", usrId);

        return namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, USER_MAPPER);
    }

    public User findByEmail(String email) {
        String sql = "SELECT " //
                + "usr.usr_id "//
                + "FROM "//
                + "evo_users usr "//
                + "WHERE "//
                + "usr.usr_email = :usr_email " //
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("usr_email", email);

        Long usrId = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, Long.class);
        return find(usrId);
    }

    public User findUser(String id) {
        return null;
    }

    public User findUser(DocumentId id) {
        return null;
    }

    @Override
    public User defaultRegistrationUser() {
        // TODO Auto-generated method stub
        return new User();
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User find(DocumentId id) {
        User retVal = null;
        if (id != null) {
            find(id.getIdAsLong());
        }
        return retVal;
    }

    @Override
    public DocumentPrivileges findUserPrivileges(Long userId, Long currentUserId) throws SparkBusinessException {
        boolean hasViewPriv = hasViewPriv(userId, currentUserId);
        boolean hasEditPriv = false;
        boolean hasNewPriv = false;
        boolean hasDeletePriv = false;

        DocumentPrivileges retVal = new DocumentPrivileges();
        retVal.setDocumentId(userId);
        retVal.setDocumentType(EvectorDocumentType.USER);
        if (hasViewPriv) {
            User user = find(userId);

            hasNewPriv = hasNewPriv(user, currentUserId);
            hasEditPriv = hasEditPriv(user, currentUserId);
            hasDeletePriv = hasDeletePriv(user, currentUserId);
        }
        retVal.add(DocumentPrivilege.newInstance(EvectorDocumentFunction.VIEW, hasViewPriv));
        retVal.add(DocumentPrivilege.newInstance(EvectorDocumentFunction.NEW, hasNewPriv));
        retVal.add(DocumentPrivilege.newInstance(EvectorDocumentFunction.EDIT, hasEditPriv));
        retVal.add(DocumentPrivilege.newInstance(EvectorDocumentFunction.DELETE, hasDeletePriv));

        return retVal;
    }

    private boolean hasViewPriv(Long userId, Long currentUserId) {
        boolean retVal = false;
        if (userId != null) {
            retVal = userId.equals(currentUserId);
        } else if (userId == null && currentUserId == null) {
            retVal = true;
        }
        return retVal;
    }

    private static boolean hasNewPriv(User user, Long currentUserId) {
        boolean retVal = !UserHelper.hasId(user);
        return retVal;
    }

    private static boolean hasEditPriv(User user, Long currentUserId) {
        boolean retVal = !UserHelper.hasId(user, currentUserId);
        return retVal;
    }

    private static boolean hasDeletePriv(User user, Long currentUserId) {
        boolean retVal = !UserHelper.hasId(user, currentUserId);
        return retVal;
    }

    @Override
    public boolean hasUser(String email) {
        User user = findByEmail(email);
        boolean retVal = user != null ? true : false;
        return retVal;
    }

    @Override
    public List<User> findAllUsers() throws SparkBusinessException {

        String sql = "SELECT usr.* FROM evo_users usr ";

        List<User> retVal = namedParamJdbcTemplate.query(sql, USER_MAPPER);
        return retVal;
    }

}
