package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.Word;

@Repository
public interface WordDao {

    void saveWords(Long sntId, List<Word> words) throws SparkBusinessException;

    void saveWord(Long sntId, Word word) throws SparkBusinessException;

    void saveSentenceWords(Long bksId, List<Sentence> sentences) throws SparkBusinessException;

}
