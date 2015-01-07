package univ.evector.db.dao;

import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.assertion.CommonAssertionHelper;
import univ.evector.beans.book.GramWord;
import univ.evector.db.dao.impl.CommonDaoImplTest;

public class GramWordDaoTest extends CommonDaoImplTest {

    public static final String TEST_WORD1 = "didelis";
    public static final String TEST_WORD2 = "neabėcėlinę";

    public static final Long TEST_PRG_ID = 6125L;

    @Autowired
    private GramWordDao gramWordDao;

    @Test
    public void testFindGramWord1() throws SparkBusinessException {
        GramWord gWord = gramWordDao.findGramWord(TEST_WORD1);
        CommonAssertionHelper.assertValueIsNotNull(gWord);
    }

    @Autowired
    private WordDao wordDao;

    @Test
    public void testFindGramWord2() throws SparkBusinessException {
        GramWord gWord = gramWordDao.findGramWord(TEST_WORD2);
        CommonAssertionHelper.assertValueIsNotNull(gWord);
        CommonAssertionHelper.assertValueIsNotNull(gWord.getGwrd_id());
    }

    @Test
    public void testFindGramWords1() throws SparkBusinessException {
        List<String> words = new ArrayList<>();
        words.add(TEST_WORD1);
        words.add(TEST_WORD2);
        List<GramWord> gWords = gramWordDao.findGramWords(words);
        CommonAssertionHelper.assertHasMoreThanOneItem(gWords);
    }

    @Test
    public void findGramWordsByPrgId() throws SparkBusinessException {
        List<GramWord> words = gramWordDao.findGramWords(TEST_PRG_ID);
    }

}
