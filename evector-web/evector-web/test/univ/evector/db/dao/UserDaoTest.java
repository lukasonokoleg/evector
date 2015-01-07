package univ.evector.db.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.assertion.CommonAssertionHelper;
import univ.evector.assertion.UserAssertionHelper;
import univ.evector.beans.User;
import univ.evector.beans.UserStatus;
import univ.evector.db.dao.impl.CommonDaoImplTest;
import univ.evector.db.dao.impl.UserDaoImpl;

public class UserDaoTest extends CommonDaoImplTest {

    public final static String TEST_USR2_EMAIL = "test.usr2.email@lan.test";
    public final static String TEST_USR2_USER_NAME = "test.usr2.user.name";
    public final static String TEST_USR2_FIRST_NAME = "test.usr2.first.name";
    public final static String TEST_USR2_LAST_NAME = "test.usr2.last.name";

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Test
    public void testSaveUser() {
        User user = createTestUser2();
        userDaoImpl.saveUser(user);
        CommonAssertionHelper.assertValueIsNotNull(user);
        UserAssertionHelper.assertHasId(user);

        user = userDaoImpl.find(user.getUsr_id());
        CommonAssertionHelper.assertValueIsNotNull(user);
        UserAssertionHelper.assertHasFirstName(user, TEST_USR2_FIRST_NAME);
        UserAssertionHelper.assertHasLastName(user, TEST_USR2_LAST_NAME);
        UserAssertionHelper.assertHasEmail(user, TEST_USR2_EMAIL);
    }

    @Test
    public void testFindUser() {
        User user = userDaoImpl.find(TEST_USR1_ID);

        CommonAssertionHelper.assertValueIsNotNull(user);
        UserAssertionHelper.assertHasId(user);

        UserAssertionHelper.assertHasFirstName(user, TEST_USR1_FIRST_NAME);
        UserAssertionHelper.assertHasLastName(user, TEST_USR1_LAST_NAME);
        UserAssertionHelper.assertHasEmail(user, TEST_USR1_EMAIL);
    }

    @Test
    public void testFindByEmail() {
        User user = userDaoImpl.findByEmail(TEST_USR1_EMAIL);
        CommonAssertionHelper.assertValueIsNotNull(user);
        UserAssertionHelper.assertHasId(user);

        UserAssertionHelper.assertHasFirstName(user, TEST_USR1_FIRST_NAME);
        UserAssertionHelper.assertHasLastName(user, TEST_USR1_LAST_NAME);
        UserAssertionHelper.assertHasEmail(user, TEST_USR1_EMAIL);
    }

    private User createTestUser2() {
        User retVal = new User();

        retVal.setUsr_email(TEST_USR2_EMAIL);

        retVal.setUsr_first_name(TEST_USR2_FIRST_NAME);
        retVal.setUsr_last_name(TEST_USR2_LAST_NAME);
        retVal.setUsr_status(UserStatus.CREATED);

        return retVal;
    }

}
