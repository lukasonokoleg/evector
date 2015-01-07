package lt.jmsys.spark.gwt.application.common.client.session;

import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.client.helper.ValueChangeSource;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class SessionHolder {

    private static SessionHolder instance;

    private ValueChangeSource<SessionInfo> sessionChangeSource = new ValueChangeSource<SessionInfo>();
    private SessionInfo sessionInfo;

    private static SessionHolder getInstance() {
        if (null == instance) {
            instance = new SessionHolder();
        }
        return instance;
    }

    public static SessionInfo getSessionInfo() {
        return getInstance().sessionInfo;
    }

    public static void setSessionInfo(SessionInfo sessionInfo) {
        getInstance().sessionInfo = sessionInfo;
        ValueChangeEvent.fire(getInstance().sessionChangeSource, sessionInfo);
    }

    public static ValueChangeSource<SessionInfo> getSessionChangeSource() {
        return getInstance().sessionChangeSource;
    }

}
