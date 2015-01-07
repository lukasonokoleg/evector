package univ.evector.db.dao.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.Password;
import univ.evector.beans.User;
import univ.evector.beans.UserStatus;
import univ.evector.db.dao.PasswordDao;
import univ.evector.db.dao.UserDao;
import eu.itreegroup.spark.application.helper.SecureKeyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:war/WEB-INF/applicationContextTest.xml")
@Transactional
public class CommonDaoImplTest {

    public final static String TEST_PASSWORD = "p";

    public final static String TEST_USR1_EMAIL = "test.usr1.email@lan.test";
    public final static String TEST_USR1_FIRST_NAME = "test.usr1.first.name";
    public final static String TEST_USR1_LAST_NAME = "test.usr1.last.name";

    public final static String TEST_USR1_PSW1 = "psw1";

    protected static Long TEST_USR1_ID;

    protected static String TEST_USR1_PSW1_HASH;
    protected static String TEST_USR1_PSW1_SALT;
    protected static Long TEST_USR1_PSW1_ID;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected PasswordDao passwordDao;

    @Before
    public void runBefore() {
        initUsers();
        initUserPasswords();
    }

    protected void initUserPasswords() {
        Password password = createTestUser1Password1();
        passwordDao.save(password, TEST_USR1_ID);
        TEST_USR1_PSW1_HASH = password.getPsw_hash();
        TEST_USR1_PSW1_SALT = password.getPsw_salt();
        TEST_USR1_PSW1_ID = password.getPsw_id();
    }

    protected void initUsers() {
        User user = createTestUser1();
        userDao.saveUser(user);
        TEST_USR1_ID = user.getUsr_id();
    }

    protected Password createTestUser1Password1() {
        Password retVal = createTestPassword(TEST_USR1_PSW1);
        return retVal;
    }

    protected Password createTestPassword(String password) {
        Password retVal = new Password();

        String psw_salt = SecureKeyUtils.generateRanfomSalt128();
        String psw_hash = SecureKeyUtils.toSHA256DigestHexString(password, psw_salt);
        Date psw_created = new Date();

        retVal.setPsw_hash(psw_hash);
        retVal.setPsw_salt(psw_salt);
        retVal.setPsw_created(psw_created);

        return retVal;
    }

    protected User createTestUser( String firstName, String lastName, String email) {
        User retVal = new User();

   
        retVal.setUsr_first_name(firstName);
        retVal.setUsr_last_name(lastName);
        retVal.setUsr_email(email);

        retVal.setUsr_status(UserStatus.CREATED);

        return retVal;
    }

    private User createTestUser1() {
        return createTestUser( TEST_USR1_FIRST_NAME, TEST_USR1_LAST_NAME, TEST_USR1_EMAIL);
    }

}