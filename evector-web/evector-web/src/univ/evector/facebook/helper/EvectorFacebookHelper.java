package univ.evector.facebook.helper;

import univ.evector.beans.User;
import univ.evector.beans.UserStatus;

public class EvectorFacebookHelper {

    public static User createByFaceBookUser(facebook4j.User facebookUser) {
        User retVal = new User();

        if (facebookUser != null) {

            String email = facebookUser.getEmail();
            String usr_first_name = facebookUser.getFirstName();
            String usr_last_name = facebookUser.getLastName();
            UserStatus usr_status = UserStatus.CREATED;

            retVal.setUsr_email(email);
            retVal.setUsr_first_name(usr_first_name);
            retVal.setUsr_last_name(usr_last_name);
            retVal.setUsr_status(usr_status);

        }

        return retVal;
    }

}