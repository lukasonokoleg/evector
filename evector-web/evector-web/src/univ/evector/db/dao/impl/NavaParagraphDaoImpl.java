package univ.evector.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper.BaseFieldAccessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import univ.evector.beans.WordForm;
import univ.evector.beans.book.GramWord;
import univ.evector.beans.book.helper.GramWordHelper;
import univ.evector.db.dao.GramWordDao;
import univ.evector.db.dao.NavaParagraphDao;
import univ.evector.helper.GrammarHelper;

@Repository
public class NavaParagraphDaoImpl extends CommonDaoImpl implements NavaParagraphDao {

    @Autowired
    private GramWordDao gramWordDao;

    public static BaseFieldAccessor<String, WordForm> WORD_FORM_VALUE_ACCESSOR = new BaseFieldAccessor<String, WordForm>() {

        @Override
        public String getFieldValue(WordForm bean) {
            String retVal = null;
            if (bean != null) {
                retVal = bean.getValue();
            }
            return retVal;
        }
    };

    @Override
    public void updateBookNavaParagraph(Long prgId) throws SparkBusinessException {
        List<String> uqWords = findUniqueWords(prgId);
        List<GramWord> gramWords = gramWordDao.findGramWords(uqWords);
        List<WordForm> wordForms = GrammarHelper.getWordForms(uqWords);
        Map<String, List<WordForm>> wordFormsMapByValue = ConversionHelper.getListMapByFieldAccessor(wordForms, WORD_FORM_VALUE_ACCESSOR);
        Map<String, GramWord> gramWordsMapByValue = ConversionHelper.getMapByFieldAccessor(gramWords, GramWordHelper.GRAM_WORD_VALUE_ACCESSOR);
        for (String word : uqWords) {
            if (gramWordsMapByValue.containsKey(word)) {
                updateWordGramWord(prgId, word, gramWordsMapByValue.get(word));
            } else if (wordFormsMapByValue.containsKey(word)) {
                
            }
        }
    }
    

    private void updateWordGramWord(Long prgId, String word, GramWord gramWord) {
        if (gramWord == null) {
            return;
        }
        if (ConversionHelper.isEmpty(word)) {
            return;
        }
        if (prgId == null) {
            return;
        }

        String sql = "UPDATE evo_words wrd LEFT JOIN evo_sentences snt ON snt.snt_id = wrd.wrd_snt_id "//
                + "SET "//
                + "wrd.wrd_gwrd_id = :gwrd_id "//
                + "WHERE "//
                + "MATCH(wrd.wrd_value) AGAINST(:word IN NATURAL LANGUAGE MODE) "//
                + "AND snt.snt_prg_id = :prg_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gwrd_id", gramWord.getGwrd_id());
        paramMap.put("word", word);
        paramMap.put("prg_id", prgId);

        namedParamJdbcTemplate.update(sql, paramMap);

    }

    private List<String> findUniqueWords(Long prgId) {
        List<String> retVal = new ArrayList<>();

        String sql = "SELECT "//
                + "distinct wrd.wrd_value "//
                + "FROM "//
                + "evo_sentences snt, "//
                + "evo_words wrd "//
                + "WHERE "//
                + "snt.snt_prg_id = :prg_id "//
                + "AND snt.snt_id = wrd.wrd_snt_id "//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("prg_id", prgId);
        List<String> tmpRetVal = namedParamJdbcTemplate.queryForList(sql, paramMap, String.class);
        if (tmpRetVal != null && !tmpRetVal.isEmpty()) {
            retVal.addAll(tmpRetVal);
        }
        return retVal;
    }

}
