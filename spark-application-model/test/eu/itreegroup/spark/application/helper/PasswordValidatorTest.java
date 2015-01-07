package eu.itreegroup.spark.application.helper;

import org.junit.Assert;
import org.junit.Test;

public class PasswordValidatorTest {

    @Test
    public void TestValidateąčęėįšųū9AA() {
        String password = "asdfwert9AA";
        PasswordValidator validator = new PasswordValidator();
        if (!validator.validate(password)) {
            Assert.fail("Password should be correct.");
        }
    }

    @Test
    public void TestPasswordTest1Test1() {
        String password = "Test1Test1";
        PasswordValidator validator = new PasswordValidator();
        if (!validator.validate(password)) {
            Assert.fail("Password should be correct.");
        }
    }

    @Test
    public void TestPasswordtEST1tEST1() {
        String password = "tEST1tEST1";
        PasswordValidator validator = new PasswordValidator();
        if (!validator.validate(password)) {
            Assert.fail("Password should be correct.");
        }
    }
}
