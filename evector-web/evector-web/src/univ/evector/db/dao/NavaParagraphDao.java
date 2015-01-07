package univ.evector.db.dao;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

public interface NavaParagraphDao {

    void updateBookNavaParagraph(Long prgId) throws SparkBusinessException;

}
