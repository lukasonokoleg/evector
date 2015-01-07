package univ.evector.gwt.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.helper.ParagraphHelper;
import univ.evector.beans.browse.FindEmotionalParagraphs;
import univ.evector.beans.browse.FindNavaParagraphs;
import univ.evector.beans.browse.FindParagraphs;
import univ.evector.db.dao.ParagraphDao;
import univ.evector.gwt.client.service.ParagraphService;

@Service
@Transactional
public class ParagraphServiceImpl implements ParagraphService {

    @Autowired
    private ParagraphDao paragraphDao;

    @Override
    public QueryResult<FindParagraphs> findParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        QueryResult<FindParagraphs> retVal = paragraphDao.findParagraphs(bksId, query, queryMetaData);
        return retVal;
    }

    @Override
    public QueryResult<FindNavaParagraphs> findNavaParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        QueryResult<FindNavaParagraphs> retVal = paragraphDao.findNavaParagraphs(bksId, query, queryMetaData);
        return retVal;
    }

    @Override
    public QueryResult<FindEmotionalParagraphs> findEmotionalParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException {
        QueryResult<FindEmotionalParagraphs> retVal = paragraphDao.findEmotionalParagraphs(bksId, query, queryMetaData);
        return retVal;
    }

    @Override
    public void updateParagraphNavaWords(Long prgId) throws SparkBusinessException {
        Paragraph paragraph = paragraphDao.findParagraph(prgId);
        ParagraphHelper.cleanGwrdIds(paragraph);
        paragraphDao.saveParagraph(paragraph);
    }
}
