package univ.evector.assertion;

import org.junit.Assert;

import univ.evector.beans.Password;
import univ.evector.beans.helper.PasswordHelper;

public class PasswordAssertionHelper {

    public static void assertHasId(Password password, Long pswId) {
        if (!PasswordHelper.hasId(password, pswId)) {
            Assert.fail("Password does not have id.id:" + pswId);
        }
    }

    public static void assertHasHash(Password password, String hash) {
        if (!PasswordHelper.hasHash(password, hash)) {
            Assert.fail("Password does not have hash. hash:" + hash);
        }
    }

    public static void assertHasSalt(Password password, String salt) {
        if (!PasswordHelper.hasSalt(password, salt)) {
            Assert.fail("Password does not have salt. salt:" + salt);
        }
    }

}
