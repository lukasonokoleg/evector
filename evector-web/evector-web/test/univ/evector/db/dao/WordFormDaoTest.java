package univ.evector.db.dao;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.db.dao.impl.CommonDaoImplTest;

public class WordFormDaoTest extends CommonDaoImplTest {

    @Autowired
    private WordFormDao wordFormDao;

    @Test
    public void testSaveWordForm() throws SparkBusinessException {
        wordFormDao.saveWordForm("testuojam", "testuojam", null, false, "test");
    }

}
