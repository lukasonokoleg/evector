package univ.evector.beans.helper;

import univ.evector.beans.Password;

public class PasswordHelper {

    public static boolean hasId(Password password) {
        boolean retVal = password != null && password.getPsw_id() != null;
        return retVal;
    }

    public static boolean hasHash(Password password) {
        boolean retVal = password != null && password.getPsw_hash() != null;
        return retVal;
    }

    public static boolean hasSalt(Password password) {
        boolean retVal = password != null && password.getPsw_salt() != null;
        return retVal;
    }

    public static boolean hasId(Password password, Long pswId) {
        boolean retVal = hasId(password) && password.getPsw_id().equals(pswId);
        return retVal;
    }

    public static boolean hasSalt(Password password, String psw_salt) {
        boolean retVal = hasSalt(password) && password.getPsw_salt().equals(psw_salt);
        return retVal;
    }

    public static boolean hasHash(Password password, String psw_hash) {
        boolean retVal = hasHash(password) && password.getPsw_hash().equals(psw_hash);
        return retVal;
    }

}
