package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.book.Book;
import univ.evector.beans.book.Paragraph;
import univ.evector.beans.browse.FindEmotionalParagraphs;
import univ.evector.beans.browse.FindNavaParagraphs;
import univ.evector.beans.browse.FindParagraphs;

public interface ParagraphDao {

    QueryResult<FindParagraphs> findParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    QueryResult<FindNavaParagraphs> findNavaParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    QueryResult<FindEmotionalParagraphs> findEmotionalParagraphs(Long bksId, String query, QueryMetaData queryMetaData) throws SparkBusinessException;

    void saveParagraphs(Book book) throws SparkBusinessException;

    void saveParagraphs(Long bksId, List<Paragraph> paragraphs) throws SparkBusinessException;

    void saveParagraph(Paragraph paragraph) throws SparkBusinessException;

    void saveParagraph(Long bksId, Paragraph paragraph) throws SparkBusinessException;

    Paragraph findParagraph(Long prgId) throws SparkBusinessException;

    List<Paragraph> findBookParagraphs(Long bksId) throws SparkBusinessException;
}