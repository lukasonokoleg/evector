package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.book.GramWord;

@Repository
public interface GramWordDao {

    GramWord findGramWord(String word) throws SparkBusinessException;

    List<GramWord> findGramWords(List<String> words) throws SparkBusinessException;

    List<GramWord> findGramWords(Long prgId) throws SparkBusinessException;

}
