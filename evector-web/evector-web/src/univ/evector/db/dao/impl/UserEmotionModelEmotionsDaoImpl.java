package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionWord;
import univ.evector.beans.helper.EmotionIdHelper;
import univ.evector.db.dao.UserEmotionModelEmotionWordsDao;
import univ.evector.db.dao.UserEmotionModelEmotionsDao;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class UserEmotionModelEmotionsDaoImpl extends CommonDaoImpl implements UserEmotionModelEmotionsDao {

    @Autowired
    private UserEmotionModelEmotionWordsDao userEmotionModelEmotionWordsDao;

    @Override
    public void saveEmotions(Long emmId, List<Emotion> emotions) {
        if (emmId == null) {
            throw new NullPointerException();
        }
        deleteOldEmotions(emmId, emotions);
        if (emotions != null) {
            for (Emotion emotion : emotions) {
                saveEmotion(emmId, emotion);
            }
        }
    }

    private void deleteOldEmotions(Long emmId, List<Emotion> emotions) {
        List<Long> currentEmmIds = EmotionIdHelper.collectIds(emotions);

        if (currentEmmIds != null && currentEmmIds.size() > 0) {
            String sql = "DELETE FROM evo_emotions "//
                    + "WHERE emo_id not in (:currentEmmIds) "//
                    + "";

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("currentEmmIds", currentEmmIds);

            namedParamJdbcTemplate.update(sql, paramMap);

        }

    }

    private void saveEmotion(Long emmId, Emotion emotion) {
        if (emmId == null) {
            throw new NullPointerException();
        }
        if (emotion == null) {
            throw new NullPointerException();
        }
        if (EmotionIdHelper.hasId(emotion)) {
            updateEmotionModelEmotion(emmId, emotion);
        } else {
            insertEmotionModelEmotion(emmId, emotion);
        }

        saveEmotionWords(emotion);

    }

    private void saveEmotionWords(Emotion emotion) {

        Long emoId = emotion.getEmo_id();
        List<EmotionWord> words = emotion.getWords();

        userEmotionModelEmotionWordsDao.saveEmotionWords(emoId, words);
    }

    private void insertEmotionModelEmotion(Long emmId, Emotion emotion) {

        String sql = "INSERT INTO evo_emotions "//
                + "( "//
                + "emo_emm_id, "//
                + "emo_name "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":emo_emm_id, "//
                + ":emo_name "//
                + ") "//
                + "";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(emotion);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("emo_emm_id", emmId);

        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long emoId = generatedKeyHolder.getKey().longValue();

        emotion.setEmo_id(emoId);

    }

    private void updateEmotionModelEmotion(Long emmId, Emotion emotion) {

        String sql = "UPDATE evo_emotions "//
                + "SET "//
                + "emo_name = emo_name "//
                + "WHERE  "//
                + "emo_id = :emo_id"//
                + "AND emo_emm_id = :emo_emm_id "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(emotion);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("emo_emm_id", emmId);

        namedParamJdbcTemplate.update(sql, paramSource);

    }

}