package univ.evector.db.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.assertion.CommonAssertionHelper;
import univ.evector.assertion.PasswordAssertionHelper;
import univ.evector.beans.Password;
import univ.evector.db.dao.impl.CommonDaoImplTest;

public class PasswordDaoTest extends CommonDaoImplTest {

    public final static String TEST_USR1_PSW2 = "psw2";

    @Autowired
    private PasswordDao passwordDao;

    @Test
    public void testFind() {
        Password password = passwordDao.find(TEST_USR1_PSW1_ID);

        CommonAssertionHelper.assertValueIsNotNull(password);
        PasswordAssertionHelper.assertHasId(password, TEST_USR1_PSW1_ID);
        PasswordAssertionHelper.assertHasHash(password, TEST_USR1_PSW1_HASH);
        PasswordAssertionHelper.assertHasSalt(password, TEST_USR1_PSW1_SALT);
    }

    @Test
    public void testFindActiveByUsrId() {
        Password password = passwordDao.findByUsrId(TEST_USR1_ID);

        CommonAssertionHelper.assertValueIsNotNull(password);
        PasswordAssertionHelper.assertHasId(password, TEST_USR1_PSW1_ID);
        PasswordAssertionHelper.assertHasHash(password, TEST_USR1_PSW1_HASH);
        PasswordAssertionHelper.assertHasSalt(password, TEST_USR1_PSW1_SALT);
    }

    @Test
    public void testFindByEmail() {
        Password password = passwordDao.findByEmail(TEST_USR1_EMAIL);
        CommonAssertionHelper.assertValueIsNotNull(password);
        PasswordAssertionHelper.assertHasId(password, TEST_USR1_PSW1_ID);
        PasswordAssertionHelper.assertHasHash(password, TEST_USR1_PSW1_HASH);
        PasswordAssertionHelper.assertHasSalt(password, TEST_USR1_PSW1_SALT);
    }

    @Test
    public void testSavePassword() {
        Password newPassword = createTestUser1Password2();
        passwordDao.save(newPassword, TEST_USR1_ID);
        Password activePassword = passwordDao.findByUsrId(TEST_USR1_ID);
        CommonAssertionHelper.assertValueIsNotNull(activePassword);
        PasswordAssertionHelper.assertHasId(activePassword, newPassword.getPsw_id());
        PasswordAssertionHelper.assertHasHash(activePassword, newPassword.getPsw_hash());
        PasswordAssertionHelper.assertHasSalt(activePassword, newPassword.getPsw_salt());
    }

    protected Password createTestUser1Password2() {
        Password retVal = createTestPassword(TEST_USR1_PSW2);
        return retVal;
    }

}