package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.WordForm;

@Repository
public interface WordFormDao {

    void saveWordForms(List<WordForm> wordForms) throws SparkBusinessException;

    void saveWordForm(WordForm wordForm) throws SparkBusinessException;

    void saveWordForm(String origin, String value, String gwrd_v, Boolean is_nava_word, String partOfSpeechCode) throws SparkBusinessException;
}