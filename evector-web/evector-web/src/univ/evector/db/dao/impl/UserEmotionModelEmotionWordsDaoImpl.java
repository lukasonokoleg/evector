package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;
import univ.evector.beans.emotion.EmotionWord;
import univ.evector.beans.helper.EmotionWordIdHelper;
import univ.evector.db.dao.UserEmotionModelEmotionWordsDao;

@Repository
public class UserEmotionModelEmotionWordsDaoImpl extends CommonDaoImpl implements UserEmotionModelEmotionWordsDao {

    @Override
    public void saveEmotionWords(Long emoId, List<EmotionWord> words) {
        if (emoId == null) {
            throw new NullPointerException();
        }
        if (words == null) {
            throw new NullPointerException();
        }

        deleteOldWords(emoId, words);

        for (EmotionWord word : words) {
            saveEmotionWord(emoId, word);
        }

    }

    private void deleteOldWords(Long emoId, List<EmotionWord> words) {
        List<Long> currentEmwIds = EmotionWordIdHelper.collectIds(words);

        if (currentEmwIds != null && currentEmwIds.size() > 0) {

            String sql = "DELETE FROM emo_emotion_words "//
                    + "WHERE emw_id not in (:currentEmwIds) "//
                    + "";

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("currentEmwIds", currentEmwIds);

            namedParamJdbcTemplate.update(sql, paramMap);

        }
    }

    private void saveEmotionWord(Long emoId, EmotionWord word) {
        if (emoId == null) {
            throw new NullPointerException();
        }
        if (word == null) {
            throw new NullPointerException();
        }

        if (EmotionWordIdHelper.hasId(word)) {
            updateEmotionWord(emoId, word);
        } else {
            insertEmotionWord(emoId, word);
        }

    }

    private void insertEmotionWord(Long emoId, EmotionWord word) {

        String sql = "INSERT INTO evo_emotion_words "//
                + "( "//
                + "emw_emo_id, "//
                + "emw_gwrd_id, "//
                + "emw_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":emw_emo_id, "//
                + ":emw_gwrd_id, "//
                + ":emw_value "//
                + ") "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(word);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long emwId = generatedKeyHolder.getKey().longValue();

        word.setEmw_id(emwId);

    }

    private void updateEmotionWord(Long emoId, EmotionWord word) {

        String sql = "UPDATE evo_emotion_words "//
                + "SET "//
                + "emw_gwrd_id = :emw_gwrd_id, "//
                + "emw_value = :emw_value "//
                + "WHERE "//
                + "emw_id = :emw_id "//
                + "AND emw_emo_id = :emw_emo_id "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(word);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);

        namedParamJdbcTemplate.update(sql, paramSource);

    }

}
