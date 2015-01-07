package univ.evector.db.dao;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.db.dao.impl.CommonDaoImplTest;

public class NavaParagraphDaoTest extends CommonDaoImplTest {

    private final static Long TEST_PARAGRAPH1_ID = 6125L;

    @Autowired
    private NavaParagraphDao navaParagraphDao;

    @Test
    public void testUpdateBookNavaParagraph() throws SparkBusinessException {
        navaParagraphDao.updateBookNavaParagraph(TEST_PARAGRAPH1_ID);
    }

}
