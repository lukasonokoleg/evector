package univ.evector.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import univ.evector.beans.book.GramWord;
import univ.evector.db.dao.GramWordDao;

@Repository
public class GramWordDaoImpl extends CommonDaoImpl implements GramWordDao {

    public final static BeanPropertyRowMapper<GramWord> GRAM_WORD_ROW_MAPPER = new BeanPropertyRowMapper<>(GramWord.class);

    public final static String SELECT_GRAM_WORD_BY_STRING = "SELECT "//
            + "gwrd.* "//
            + "FROM "//
            + "evo_gram_words gwrd "//
            + "WHERE "//
            + "MATCH (gwrd.gwrd_value) AGAINST (:word IN NATURAL LANGUAGE MODE) "//
            + "AND BINARY gwrd.gwrd_value like BINARY :word ";

    public final static String SELECT_GRAM_WORDS_BY_STRING = "SELECT "//
            + "gwrd.* "//
            + "FROM "//
            + "evo_gram_words gwrd "//
            + "WHERE "//
            + "MATCH (gwrd.gwrd_value) AGAINST (:wordsAsString IN NATURAL LANGUAGE MODE) "//
            + "AND BINARY gwrd.gwrd_value in  (:words) ";

    public final static String SELECT_GRAM_WORDS_BY_PRG_ID = "SELECT "//
            + "gwrd.* "//
            + "FROM "//
            + "evo_gram_words gwrd, " + "evo_words wrd, " + "evo_sentences snt "//
            + "WHERE "//
            + "snt.snt_prg_id = :prg_id "//
            + "AND snt.snt_id = wrd.wrd_snt_id "//
            + "AND wrd.wrd_gwrd_id = gwrd.gwrd_id "//
            + "ORDER BY wrd.wrd_seq asc "//
            + "";

    @Override
    public GramWord findGramWord(String word) throws SparkBusinessException {
        if (ConversionHelper.isEmpty(word)) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("word", word.toLowerCase());
        GramWord retVal = namedParamJdbcTemplate.queryForObjectNullable(SELECT_GRAM_WORD_BY_STRING, paramMap, GRAM_WORD_ROW_MAPPER);
        return retVal;
    }

    @Override
    public List<GramWord> findGramWords(List<String> words) throws SparkBusinessException {
        List<GramWord> retVal = new ArrayList<>();
        if (!ConversionHelper.isEmpty(words)) {
            String wordsAsString = words.toString();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("wordsAsString", wordsAsString.toLowerCase());
            paramMap.put("words", words);
            List<GramWord> tmpRetVal = namedParamJdbcTemplate.query(SELECT_GRAM_WORDS_BY_STRING, paramMap, GRAM_WORD_ROW_MAPPER);
            if (!ConversionHelper.isEmpty(tmpRetVal)) {
                retVal.addAll(tmpRetVal);
            }
        }
        return retVal;
    }

    @Override
    public List<GramWord> findGramWords(Long prgId) throws SparkBusinessException {
        List<GramWord> retVal = new ArrayList<>();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("prg_id", prgId);

        List<GramWord> tmpRetVal = namedParamJdbcTemplate.query(SELECT_GRAM_WORDS_BY_PRG_ID, paramMap, GRAM_WORD_ROW_MAPPER);

        if (!ConversionHelper.isEmpty(tmpRetVal)) {
            retVal.addAll(tmpRetVal);
        }

        return retVal;
    }
}