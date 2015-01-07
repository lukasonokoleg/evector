package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.browse.FindUserEmotionModels;
import univ.evector.beans.browse.FindUserEmotions;
import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionModel;
import univ.evector.beans.helper.EmotionModelIdHelper;
import univ.evector.db.dao.UserEmotionModelDao;
import univ.evector.db.dao.UserEmotionModelEmotionsDao;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class UserEmotionDaoModelImpl extends CommonDaoImpl implements UserEmotionModelDao {

    public final static RowMapper<FindUserEmotions> FIND_USER_EMOTIONS = new BeanPropertyRowMapper<FindUserEmotions>(FindUserEmotions.class);

    public final static RowMapper<FindUserEmotionModels> FIND_USER_EMOTION_MODELS = new BeanPropertyRowMapper<FindUserEmotionModels>(FindUserEmotionModels.class);

    @Autowired
    private UserEmotionModelEmotionsDao userEmotionModelEmotionsDao;

    @Override
    public QueryResult<FindUserEmotions> findUserEmotions(Long userId, QueryMetaData queryMetaData) throws SparkBusinessException {

        String select = "SELECT "//
                + ""//
                + "";
        String from = "FROM "//
                + ""//
                + "";
        String where = "WHERE "//
                + ""//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", userId);

        QueryResult<FindUserEmotions> retVal = browseJdbcHelper.browse(select, from, where, queryMetaData, FIND_USER_EMOTIONS, paramMap);
        return retVal;
    }

    @Override
    public QueryResult<FindUserEmotionModels> findUserEmotionModels(Long userId, QueryMetaData queryMetaData) throws SparkBusinessException {

        String select = "SELECT "//
                + "emm_id, "//
                + "emm_name, "//
                + "emm_created "//
                + "";
        String from = "FROM "//
                + "evo_emotion_models emm "//
                + "";
        String where = "WHERE "//
                + "emm.emm_usr_id = :user_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", userId);

        QueryResult<FindUserEmotionModels> retVal = browseJdbcHelper.browse(select, from, where, queryMetaData, FIND_USER_EMOTION_MODELS, paramMap);
        return retVal;
    }

    @Override
    public void saveUserEmotionModel(Long userId, EmotionModel model) throws SparkBusinessException {
        if (userId == null) {
            throw new NullPointerException();
        }
        if (model == null) {
            throw new NullPointerException();
        }
        if (EmotionModelIdHelper.hasId(model)) {
            updateEmotionModel(userId, model);
        } else {
            insertEmotionModel(userId, model);
        }
        saveEmotions(model);
    }

    private void insertEmotionModel(Long userId, EmotionModel model) {

        String sql = "INSERT INTO evo_emotion_models "//
                + "( "//
                + "emm_usr_id, "//
                + "emm_name, "//
                + "emm_created "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":emm_usr_id, "//
                + ":emm_name, "//
                + "now() "//
                + ") "//
                + "";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(model);

        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("emm_usr_id", userId);

        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long emmId = generatedKeyHolder.getKey().longValue();
        model.setEmm_id(emmId);

    }

    private void updateEmotionModel(Long userId, EmotionModel model) {

        String sql = "UPDATE evo_emotion_models "//
                + "SET "//
                + "emm_name = :emm_name "//
                + ""//
                + "WHERE  "//
                + "emm_id = :emm_id " + "AND emm_usr_id = :emm_usr_id "//

                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(model);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("emm_usr_id", userId);

        namedParamJdbcTemplate.update(sql, paramSource);

    }

    private void saveEmotions(EmotionModel model) {
        Long emmId = model.getEmm_id();
        List<Emotion> emotions = model.getEmotions();

        userEmotionModelEmotionsDao.saveEmotions(emmId, emotions);

    }

}