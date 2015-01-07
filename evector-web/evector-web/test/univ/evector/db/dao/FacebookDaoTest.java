package univ.evector.db.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.assertion.CommonAssertionHelper;
import univ.evector.db.dao.impl.CommonDaoImplTest;

public class FacebookDaoTest extends CommonDaoImplTest {

    @Autowired
    private FacebookDao facebookDao;

    @Test
    public void testGetAuthApplicationId() {
        String value = facebookDao.getAuthApplicationId();
        CommonAssertionHelper.assertValueIsNotNull(value);
    }

    @Test
    public void testGetAuthApplicationSecret() {
        String value = facebookDao.getAuthApplicationSecret();
        CommonAssertionHelper.assertValueIsNotNull(value);
    }

    @Test
    public void testIsDebugEnabled() {
        Boolean value = facebookDao.isDebugEnabled();
        CommonAssertionHelper.assertValueIsNotNull(value);
    }

}
