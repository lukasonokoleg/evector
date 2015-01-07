package univ.evector.db.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;
import univ.evector.beans.User;
import univ.evector.beans.facebook.FbPost;
import univ.evector.beans.facebook.helper.FbPostIdHelper;
import univ.evector.db.dao.FbPostDao;

@Repository
public class FbPostDaoImpl extends CommonDaoImpl implements FbPostDao {

    @Override
    public void saveFbPosts(User user, List<FbPost> posts) throws SparkBusinessException {
        if (user == null) {
            throw new NullPointerException();
        }
        Long usrId = user.getUsr_id();
        saveFbPosts(usrId, posts);
    }

    @Override
    public void saveFbPosts(Long usrId, List<FbPost> posts) throws SparkBusinessException {
        if (posts == null) {
            throw new NullPointerException();
        }
        if (usrId == null) {
            throw new NullPointerException();
        }
        for (FbPost post : posts) {
            saveFbPost(usrId, post);
        }
    }

    private void saveFbPost(Long usrId, FbPost post) {
        if (usrId == null) {
            throw new NullPointerException();
        }
        if (post == null) {
            throw new NullPointerException();
        }
        if (FbPostIdHelper.hasId(post)) {
            updateFbPost(usrId, post);
        } else {
            insertFbPost(usrId, post);
        }
    }

    private void insertFbPost(Long usrId, FbPost post) {
        String sql = "INSERT INTO "//
                + "fb_posts "//
                + "( "//
                + "pst_usr_id, "//
                + "pst_fb_id, "//
                + "pst_date, "//
                + "pst_message "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":pst_usr_id, "//
                + ":pst_fb_id, "//
                + ":pst_date, "//
                + ":pst_message "//
                + ") "//
                + "";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        BeanPropertySqlParameterSource beanPropertySqlSource = new BeanPropertySqlParameterSource(post);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanPropertySqlSource);
        paramSource.addValue("pst_usr_id", usrId);

        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

    }

    private void updateFbPost(Long usrId, FbPost post) {

        String sql = "UPDATE fb_posts "//
                + "SET "//
                + "pst_fb_id = :pst_fb_id, "//
                + "pst_date = :pst_date, "//
                + "pst_message = :pst_message "//
                + "WHERE "//
                + "pst_id = :pst_id "//
                + "AND pst_usr_id = :pst_usr_id "//
                + "";

        BeanPropertySqlParameterSource beanPropertySqlSource = new BeanPropertySqlParameterSource(post);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanPropertySqlSource);
        paramSource.addValue("pst_usr_id", usrId);

        namedParamJdbcTemplate.update(sql, paramSource);

    }

    @Override
    public Date lastUserFbPostDate(Long usrId) throws SparkBusinessException {
        if (usrId == null) {
            throw new NullPointerException();
        }
        String sql = "SELECT "//
                + "pst.pst_date "//
                + "FROM fb_posts pst "//
                + "WHERE "//
                + "pst.pst_usr_id = :user_id "//
                + "ORDER BY "//
                + "pst.pst_date DESC "//
                + "LIMIT 0,1 "//
                + "";//

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", usrId);

        Date retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, Date.class);
        return retVal;
    }

    @Override
    public List<FbPost> getUserFbPosts(Long usrId) throws SparkBusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date lastUserFbPostDate(User user) throws SparkBusinessException {
        if (user == null) {
            throw new NullPointerException();
        }
        Long usrId = user.getUsr_id();
        Date retVal = lastUserFbPostDate(usrId);
        return retVal;
    }

}
