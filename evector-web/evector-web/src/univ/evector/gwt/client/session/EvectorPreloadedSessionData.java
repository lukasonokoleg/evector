package univ.evector.gwt.client.session;

import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import univ.evector.beans.UserSession;

@SuppressWarnings("serial")
public class EvectorPreloadedSessionData extends PreloadedSessionData {

    private UserSession userSession;

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

}
