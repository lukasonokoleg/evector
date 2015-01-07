package lt.jmsys.spark.gwt.application.client.application.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.application.resource.ApplicationConstants;
import lt.jmsys.spark.gwt.application.client.common.service.SessionService;
import lt.jmsys.spark.gwt.application.client.common.service.SessionServiceAsync;
import lt.jmsys.spark.gwt.application.common.client.service.CommonServiceProxy;
import lt.jmsys.spark.gwt.application.common.client.service.CommonServiceProxy.SessionMonitorCallback;
import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.client.ui.application.Application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SessionMonitor {

    private static final ApplicationConstants C = GWT.create(ApplicationConstants.class);
    private static final Logger log = Logger.getLogger(ApplicationPresenter.class.getName());

    private static boolean wasStarted;

    public static void start() {
        CommonServiceProxy.startSessionMonitor(new SessionMonitorCb());
        wasStarted = true;
    }

    public static boolean startOnceIfHasSession() {
        if (!wasStarted && getSessionExpiresAfter() > 0) {
            start();
            return true;
        }
        return false;
    }

    public static long getSessionExpiresAfter() {
        return CommonServiceProxy.getSessionExpiresAfter();
    }

    private static class SessionMonitorCb implements SessionMonitorCallback {

        @Override
        public void afterExpire() {
            Application.getApplication().showLogin(true, C.msgSessionExpired());
        }

        @Override
        public void beforeExpire() {
            SessionInfo session = SessionHolder.getSessionInfo();
            if (null != session && session.isKeepFresh()) {
                SessionServiceAsync service = GWT.create(SessionService.class);
                AsyncCallback<Void> callback = new AsyncCallback<Void>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "failed to touch session", caught);
                    }

                    @Override
                    public void onSuccess(Void result) {
                    }
                };

                service.touchSession(callback);
            }
        }
    }
}
