package univ.evector.db.dao.impl;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.WordForm;
import univ.evector.beans.book.GramWord;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.Word;
import univ.evector.beans.book.helper.WordHelper;
import univ.evector.beans.book.helper.WordIdHelper;
import univ.evector.db.dao.GramWordDao;
import univ.evector.db.dao.WordDao;
import univ.evector.db.dao.WordFormDao;
import univ.evector.helper.GrammarHelper;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class WordDaoImpl extends CommonDaoImpl implements WordDao {

    @Autowired
    private GramWordDao gramWordDao;

    @Autowired
    private WordFormDao wordFormDao;

    @Override
    public void saveWords(Long sntId, List<Word> words) throws SparkBusinessException {
        if (words == null) {
            throw new IllegalArgumentException("");
        }
        for (Word word : words) {
            saveWord(sntId, word);
        }
    }

    @Override
    public void saveWord(Long sntId, Word word) throws SparkBusinessException {
        if (word == null) {
            throw new IllegalArgumentException("");
        }
        if (!WordHelper.hasGwrdId(word)) {
            setGwrdIdToWord(word);
        }
        if (WordHelper.hasWrdId(word)) {
            update(sntId, word);
        } else {
            insert(sntId, word);
        }
    }

    private void setGwrdIdToWord(Word word) throws SparkBusinessException {
        if (word != null) {
            GramWord gramWord = gramWordDao.findGramWord(word.getWrd_value());
            if (gramWord == null) {
                updateGramWords(word);
                gramWord = gramWordDao.findGramWord(word.getWrd_value());
            }
            WordHelper.setGwrdIdToWord(word, gramWord);
        }
    }

    private void updateGramWords(Word word) throws SparkBusinessException {
        List<WordForm> wordForms = GrammarHelper.getWordFormsByMaxSuffixMatched(word.getWrd_value());
        if (!ConversionHelper.isEmpty(wordForms)) {
            wordFormDao.saveWordForms(wordForms);
        }
    }

    private void insert(Long sntId, Word word) {
        String sql = "INSERT INTO evo_words "//
                + "( "//
                + "wrd_snt_id, "//
                + "wrd_seq, "//
                + "wrd_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":wrd_snt_id, "//
                + ":wrd_seq, "//
                + ":wrd_value "//
                + ") "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(word);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("wrd_snt_id", sntId);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long wrdId = generatedKeyHolder.getKey().longValue();
        word.setWrd_id(wrdId);
    }

    private void update(Long sntId, Word word) {
        String sql = "UPDATE evo_words "//
                + "SET "//
                + "wrd_snt_id = :wrd_snt_id, "//
                + "wrd_seq = :wrd_seq, "//
                + "wrd_value =:wrd_value "//
                + "WHERE "//
                + "wrd_id = :wrd_id "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(word);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("wrd_snt_id", sntId);

        namedParamJdbcTemplate.update(sql, paramSource);

    }

    @Override
    public void saveSentenceWords(Long bksId, List<Sentence> sentences) throws SparkBusinessException {
        if (sentences == null) {
            throw new NullPointerException();
        }
        WordIdHelper.ensureHasSntId(sentences);
        List<Word> words = WordHelper.getAllWords(sentences);
        insertWordsBatch(words);
    }

    private void insertWordsBatch(List<Word> words) throws SparkBusinessException {
        if (words == null) {
            throw new NullPointerException();
        }
        String sql = "INSERT INTO evo_words "//
                + "( "//
                + "wrd_snt_id, "//
                + "wrd_seq, "//
                + "wrd_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":wrd_snt_id, "//
                + ":wrd_seq, "//
                + ":wrd_value "//
                + ") "//
                + "";
        
        SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(words.toArray());

        namedParamJdbcTemplate.batchUpdate(sql, batchArgs);

    }

}
