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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import univ.evector.beans.book.Book;
import univ.evector.beans.book.GramWord;
import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.helper.GramWordHelper;
import univ.evector.beans.book.helper.ParagraphHelper;
import univ.evector.beans.book.helper.ParagraphIdHelper;
import univ.evector.beans.book.helper.ParagraphSentenceHelper;
import univ.evector.beans.browse.FindEmotionalParagraphs;
import univ.evector.beans.browse.FindNavaParagraphs;
import univ.evector.beans.browse.FindParagraphs;
import univ.evector.db.dao.GramWordDao;
import univ.evector.db.dao.ParagraphDao;
import univ.evector.db.dao.SentenceDao;
import eu.itreegroup.spark.application.service.mysql.VvsisSqlParameterSource;

@Repository
public class ParagraphDaoImpl extends CommonDaoImpl implements ParagraphDao {

    private RowMapper<Paragraph> PARAGRAPH_ROW_MAPPER = new BeanPropertyRowMapper<>(Paragraph.class);

    private RowMapper<FindParagraphs> FIND_PARAGRAPHS_ROW_MAPPER = new BeanPropertyRowMapper<>(FindParagraphs.class);

    private RowMapper<FindNavaParagraphs> FIND_NAVA_PARAGRAPHS_ROW_MAPPER = new BeanPropertyRowMapper<FindNavaParagraphs>(FindNavaParagraphs.class) {

        @Override
        public FindNavaParagraphs mapRow(java.sql.ResultSet rs, int rowNumber) throws java.sql.SQLException {
            FindNavaParagraphs retVal = super.mapRow(rs, rowNumber);
            String navaValue = null;
            try {
                List<GramWord> prgGWords = gramWordDao.findGramWords(retVal.getPrg_id());
                navaValue = GramWordHelper.getGramWordsOriginAsString(prgGWords);
            } catch (SparkBusinessException e) {
                navaValue = "Cought exception.";
            }
            retVal.setNava_value(navaValue);
            return retVal;
        };

    };

    private RowMapper<FindEmotionalParagraphs> FIND_EMOTIONAL_PARAGRAPHS_ROW_MAPPER = new BeanPropertyRowMapper<>(FindEmotionalParagraphs.class);

    @Autowired
    private SentenceDao sentenceDao;

    @Autowired
    private GramWordDao gramWordDao;

    @Autowired
    private NavaParagraphDaoImpl navaParagraphDaoImpl;

    @Override
    public void saveParagraphs(Long bksId, List<Paragraph> paragraphs) throws SparkBusinessException {
        if (paragraphs == null) {
            throw new IllegalArgumentException("");
        }
        for (Paragraph paragraph : paragraphs) {
            saveParagraph(bksId, paragraph);
            navaParagraphDaoImpl.updateBookNavaParagraph(paragraph.getPrg_id());
        }
    }

    @Override
    public void saveParagraph(Long bksId, Paragraph paragraph) throws SparkBusinessException {
        if (paragraph == null) {
            throw new IllegalArgumentException("");
        }
        if (ParagraphHelper.hasPrgId(paragraph)) {
            updateParagraph(bksId, paragraph);
        } else {
            insertParagraph(bksId, paragraph);
        }
        sentenceDao.saveSentences(paragraph.getPrg_id(), paragraph.getSentences());
    }

    private void insertParagraph(Long bksId, Paragraph paragraph) {
        String sql = "INSERT INTO evo_paragraphs "//
                + "( "//
                + "prg_bks_id, "//
                + "prg_seq, "//
                + "prg_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":prg_bks_id, "//
                + ":prg_seq, "//
                + ":prg_value "//
                + ") "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(paragraph);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("prg_bks_id", bksId);

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParamJdbcTemplate.update(sql, paramSource, generatedKeyHolder);

        Long prgId = generatedKeyHolder.getKey().longValue();
        paragraph.setPrg_id(prgId);
    }

    private void updateParagraph(Long bksId, Paragraph paragraph) {
        String sql = "UPDATE evo_paragraphs "//
                + "SET "//
                + "prg_bks_id = :prg_bks_id, "//
                + "prg_seq = :prg_seq, "//
                + "prg_value = :prg_value "//
                + "WHERE "//
                + "prg_id = :prg_id "//
                + "";

        BeanPropertySqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(paragraph);
        VvsisSqlParameterSource paramSource = new VvsisSqlParameterSource(beanParamSource);
        paramSource.addValue("prg_bks_id", bksId);

        namedParamJdbcTemplate.update(sql, paramSource);
    }

    @Override
    public QueryResult<FindParagraphs> findParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException {

        String tmpQuery = query;

        String select = "SELECT prg.* "//
                + "";
        String from = "FROM evo_paragraphs prg "//
                + "";
        String where = "WHERE "//
                + "prg.prg_bks_id = :bks_id "//
                + "";

        if (query != null) {
            where = where + "AND prg.prg_value LIKE :query ";
            tmpQuery = "%" + tmpQuery + "%";
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bks_id", bksId);
        paramMap.put("query", tmpQuery);

        return browseJdbcHelper.browse(select, from, where, queryMetaData, FIND_PARAGRAPHS_ROW_MAPPER, paramMap);
    }

    @Override
    public QueryResult<FindNavaParagraphs> findNavaParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        String tmpQuery = query;

        String select = "SELECT prg.* "//
                + "";
        String from = "FROM evo_paragraphs prg "//
                + "";
        String where = "WHERE "//
                + "prg.prg_bks_id = :bks_id "//
                + "";

        if (query != null) {
            where = where + "AND prg.prg_value LIKE :query ";
            tmpQuery = "%" + tmpQuery + "%";
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bks_id", bksId);
        paramMap.put("query", tmpQuery);

        return browseJdbcHelper.browse(select, from, where, queryMetaData, FIND_NAVA_PARAGRAPHS_ROW_MAPPER, paramMap);
    }

    @Override
    public QueryResult<FindEmotionalParagraphs> findEmotionalParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        String tmpQuery = query;

        String select = "SELECT prg.* "//
                + "";
        String from = "FROM evo_paragraphs prg "//
                + "";
        String where = "WHERE "//
                + "prg.prg_bks_id = :bks_id "//
                + "";

        if (query != null) {
            where = where + "AND prg.prg_value LIKE :query ";
            tmpQuery = "%" + tmpQuery + "%";
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bks_id", bksId);
        paramMap.put("query", tmpQuery);

        return browseJdbcHelper.browse(select, from, where, queryMetaData, FIND_EMOTIONAL_PARAGRAPHS_ROW_MAPPER, paramMap);
    }

    @Override
    public void saveParagraph(Paragraph paragraph) throws SparkBusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public Paragraph findParagraph(Long prgId) throws SparkBusinessException {
        String sql = "";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pgrd_id", prgId);
        Paragraph retVal = namedParamJdbcTemplate.queryForObjectNullable(sql, paramMap, PARAGRAPH_ROW_MAPPER);

        List<Sentence> sentences = sentenceDao.findSentences(prgId);
        if (retVal != null) {
            retVal.setSentences(sentences);
        }
        return retVal;
    }

    @Override
    public void saveParagraphs(Book book) throws SparkBusinessException {
        if (book == null) {
            throw new NullPointerException();
        }
        ParagraphIdHelper.ensureParagraphHasBksId(book);
        insertParagraphsBatch(book.getParagraphs());
        List<Paragraph> paragraphs = findBookParagraphs(book.getBks_id());
        ParagraphSentenceHelper.splitParagraphValueIntoSentence(paragraphs);
        sentenceDao.saveParagraphSentences(book.getBks_id(), paragraphs);
    }

    @Override
    public List<Paragraph> findBookParagraphs(Long bksId) throws SparkBusinessException {

        String sql = "SELECT "//
                + "prg.* "//
                + ""//
                + "FROM "//
                + "evo_paragraphs prg "//
                + ""//
                + "WHERE "//
                + "prg.prg_bks_id = :bks_id "//
                + ""//
                + ""//
                + "";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bks_id", bksId);

        List<Paragraph> retVal = namedParamJdbcTemplate.queryForList(sql, paramMap, Paragraph.class);
        return retVal;
    }

    private void insertParagraphsBatch(List<Paragraph> paragraphs) {
        if (paragraphs == null) {
            throw new NullPointerException();
        }
        String sql = "INSERT INTO evo_paragraphs "//
                + "( "//
                + "prg_bks_id, "//
                + "prg_seq, "//
                + "prg_value "//
                + ") "//
                + "VALUES "//
                + "( "//
                + ":prg_bks_id, "//
                + ":prg_seq, "//
                + ":prg_value "//
                + ") "//
                + "";

        SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(paragraphs.toArray());

        namedParamJdbcTemplate.batchUpdate(sql, batchArgs);
    }

}
