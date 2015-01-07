package univ.evector.gwt.client.session;

import lt.jmsys.spark.gwt.client.helper.ValueChangeSource;
import univ.evector.beans.User;
import univ.evector.beans.UserSession;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class UserSessionHolder {

    private static UserSessionHolder instance;

    private ValueChangeSource<UserSession> sessionChangeSource = new ValueChangeSource<UserSession>();
    private UserSession session;

    private static UserSessionHolder getInstance() {
        if (null == instance) {
            instance = new UserSessionHolder();
        }
        return instance;
    }

    public static UserSession getUserSession() {
        return getInstance().session;
    }

    public static User getUser() {
        return getInstance().session.getUser();
    }

    public static void setUserSession(UserSession session) {
        getInstance().session = session;
        ValueChangeEvent.fire(getInstance().sessionChangeSource, session);
    }

    public static ValueChangeSource<UserSession> getSessionChangeSource() {
        return getInstance().sessionChangeSource;
    }
}
