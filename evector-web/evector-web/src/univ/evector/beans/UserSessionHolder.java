package univ.evector.beans;

import java.io.Serializable;

import facebook4j.Facebook;

@SuppressWarnings("serial")
public class UserSessionHolder implements Serializable {

    private UserSession userSession;

    private Facebook facebook;

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

}