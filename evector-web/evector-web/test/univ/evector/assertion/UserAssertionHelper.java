package univ.evector.assertion;

import org.junit.Assert;

import univ.evector.beans.User;
import univ.evector.beans.helper.UserHelper;

public class UserAssertionHelper {

    public static void assertHasId(User user) {
        if (!UserHelper.hasId(user)) {
            Assert.fail("User does not have id.");
        }
    }


    public static void assertHasFirstName(User user) {
        if (!UserHelper.hasFirstName(user)) {
            Assert.fail("User does not have first name.");
        }
    }

    public static void assertHasLastName(User user) {
        if (!UserHelper.hasLastName(user)) {
            Assert.fail("User does not have last name.");
        }
    }

    public static void assertHasEmail(User user) {
        if (!UserHelper.hasEmail(user)) {
            Assert.fail("User does not have email.");
        }
    }


    public static void assertHasFirstName(User user, String firstName) {
        if (!UserHelper.hasFirstName(user, firstName)) {
            Assert.fail("User does not have first name. First name: " + firstName);
        }
    }

    public static void assertHasLastName(User user, String lastName) {
        if (!UserHelper.hasLastName(user, lastName)) {
            Assert.fail("User does not have last name. Last name: " + lastName);
        }
    }

    public static void assertHasEmail(User user, String email) {
        if (!UserHelper.hasEmail(user, email)) {
            Assert.fail("User does not have email. Email: " + email);
        }
    }
}
