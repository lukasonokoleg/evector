package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import univ.evector.beans.WordForm;
import univ.evector.db.dao.WordFormDao;
import univ.evector.helper.WordFormHelper;

@Repository
public class WordFormDaoImpl extends CommonDaoImpl implements WordFormDao {

    private final static Logger LOGGER = Logger.getLogger(WordFormDaoImpl.class);

    @Override
    public void saveWordForms(List<WordForm> wordForms) throws SparkBusinessException {
        if (wordForms == null) {
            throw new NullPointerException();
        }

        for (WordForm wordForm : wordForms) {
            if (wordForm != null) {
                saveWordForm(wordForm);
            }
        }
    }

    @Override
    public void saveWordForm(WordForm wordForm) throws SparkBusinessException {
        if (wordForm == null) {
            throw new NullPointerException();
        }

        String origin = null;
        String value = null;
        String gwrd_v = null;
        Boolean is_nava_word = null;
        String partOfSpeechCode = null;

        if (wordForm != null) {
            origin = wordForm.getOrigin();
            value = wordForm.getValue();
            gwrd_v = null;
            partOfSpeechCode = WordFormHelper.getPartOfSpeechCode(wordForm);
            is_nava_word = WordFormHelper.isNavaWordForm(wordForm);
        }

        saveWordForm(origin, value, gwrd_v, is_nava_word, partOfSpeechCode);

    }

    @Override
    public void saveWordForm(String origin, String value, String gwrd_v, Boolean is_nava_word, String partOfSpeechCode) throws SparkBusinessException {
        String sql = "INSERT INTO evo_gram_words "//
                + "( "//
                + "gwrd_origin, "//
                + "gwrd_value, "//
                + "gwrd_v, "//
                + "gwrd_is_nava_word, "//
                + "gwrd_part_of_speech "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":gwrd_origin, "//
                + ":gwrd_value, "//
                + ":gwrd_v, "//
                + ":gwrd_is_nava_word, "//
                + ":gwrd_part_of_speech "//
                + ") "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gwrd_origin", origin);
        paramMap.put("gwrd_value", value);
        paramMap.put("gwrd_v", gwrd_v);
        paramMap.put("gwrd_is_nava_word", is_nava_word);
        paramMap.put("gwrd_part_of_speech", partOfSpeechCode);
        namedParamJdbcTemplate.update(sql, paramMap);
        LOGGER.info("Saved new word form");

    }
}
