package univ.evector.db.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.helper.SentenceHelper;
import univ.evector.beans.book.helper.SentenceIdHelper;
import univ.evector.beans.book.helper.SentenceWordHelper;
import univ.evector.db.dao.SentenceDao;
import univ.evector.db.dao.WordDao;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class SentenceDaoImpl extends CommonDaoImpl implements SentenceDao {

    @Autowired
    private WordDao wordDao;

    private RowMapper<Sentence> SENTENCE_ROW_MAPPER = new BeanPropertyRowMapper<>(Sentence.class);

    @Override
    public void saveSentences(Long prgId, List<Sentence> sentences) throws SparkBusinessException {
        if (sentences == null) {
            throw new IllegalArgumentException("");
        }
        for (Sentence sentence : sentences) {
            saveSentence(prgId, sentence);
        }
    }

    @Override
    public void saveSentence(Long prgId, Sentence sentence) throws SparkBusinessException {
        if (sentence == null) {
            throw new IllegalArgumentException("");
        }
        if (SentenceHelper.hasSntId(sentence)) {
            updateSentence(prgId, sentence);
        } else {
            insertSentence(prgId, sentence);
        }
        wordDao.saveWords(sentence.getSnt_id(), sentence.getWords());
    }

    private void insertSentence(Long prgId, Sentence sentence) {
        String sql = "INSERT INTO evo_sentences " //
                + "( "//
                + "snt_prg_id, "//
                + "snt_seq, "//
                + "snt_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":snt_prg_id, "//
                + ":snt_seq, "//
                + ":snt_value "//
                + ") "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(sentence);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("snt_prg_id", prgId);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long sntId = generatedKeyHolder.getKey().longValue();
        sentence.setSnt_id(sntId);
    }

    private void updateSentence(Long prgId, Sentence sentence) {
        String sql = "UPDATE evo_sentences " //
                + "SET "//
                + "snt_prg_id = :snt_prg_id, "//
                + "snt_seq = :snt_seq, "//
                + "snt_value = :snt_value "//
                + "WHERE "//
                + "snt_id = :snt_id "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(sentence);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("snt_prg_id", prgId);

        namedParamJdbcTemplate.update(sql, paramSource);

    }

    @Override
    public List<Sentence> findSentences(Long prgId) throws SparkBusinessException {

        return null;
    }

    @Override
    public void saveParagraphSentences(Long bksId, List<Paragraph> paragraphs) throws SparkBusinessException {
        if (paragraphs == null) {
            throw new NullPointerException();
        }
        SentenceIdHelper.ensureHasPrgId(paragraphs);
        List<Sentence> sentences = SentenceHelper.getAllSentences(paragraphs);
        insertSentencesBatch(sentences);
        sentences = findBookSentences(bksId);
        SentenceWordHelper.splitSentenceIntoWords(sentences);
        wordDao.saveSentenceWords(bksId, sentences);
    }

    private List<Sentence> findBookSentences(Long bksId) {

        String sql = "SELECT "//
                + "snt.* "//
                + ""//
                + "FROM " + "evo_paragraphs prg, "//
                + "evo_sentences snt "//
                + ""//
                + "WHERER "//
                + "prg.prg_bks_id = :bks_id "//
                + "AND prg.prg_id = snt.snt_prg_id "//
                + ""//
                + ""//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bks_id", bksId);
        List<Sentence> retVal = namedParamJdbcTemplate.queryForList(sql, paramMap, Sentence.class);
        return retVal;
    }

    private void insertSentencesBatch(List<Sentence> sentences) {
        if (sentences == null) {
            throw new NullPointerException();
        }
        String sql = "INSERT INTO evo_sentences "//
                + "( "//
                + "snt_prg_id, "//
                + "snt_seq, "//
                + "snt_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":snt_prg_id, "//
                + ":snt_seq, "//
                + ":snt_value "//
                + ") "//
                + "";

        SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(sentences.toArray());

        namedParamJdbcTemplate.batchUpdate(sql, batchArgs);
    }

}
