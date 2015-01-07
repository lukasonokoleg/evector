package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;

@Repository
public interface SentenceDao {

    List<Sentence> findSentences(Long prgId) throws SparkBusinessException;

    void saveSentences(Long prgId, List<Sentence> sentences) throws SparkBusinessException;

    void saveSentence(Long prgId, Sentence sentence) throws SparkBusinessException;

    void saveParagraphSentences(Long bksId, List<Paragraph> paragraphs) throws SparkBusinessException;
}
