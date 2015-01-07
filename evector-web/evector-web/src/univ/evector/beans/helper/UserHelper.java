package univ.evector.beans.helper;

import univ.evector.beans.User;

public class UserHelper {

    public static boolean hasId(User user) {
        boolean retVal = user != null && user.getUsr_id() != null;
        return retVal;
    }

    public static boolean hasId(User user, Long usrId) {
        boolean retVal = hasId(user) && user.getUsr_id().equals(usrId);
        return retVal;
    }

    public static boolean hasFirstName(User user) {
        boolean retVal = user != null && user.getUsr_first_name() != null;
        return retVal;
    }

    public static boolean hasLastName(User user) {
        boolean retVal = user != null && user.getUsr_last_name() != null;
        return retVal;
    }

    public static boolean hasEmail(User user) {
        boolean retVal = user != null && user.getUsr_email() != null;
        return retVal;
    }

    public static boolean hasFirstName(User user, String firstName) {
        boolean retVal = hasFirstName(user) && user.getUsr_first_name().equals(firstName);
        return retVal;
    }

    public static boolean hasLastName(User user, String lastName) {
        boolean retVal = hasLastName(user) && user.getUsr_last_name().equals(lastName);
        return retVal;
    }

    public static boolean hasEmail(User user, String email) {
        boolean retVal = hasEmail(user) && user.getUsr_email().equals(email);
        return retVal;
    }

}
