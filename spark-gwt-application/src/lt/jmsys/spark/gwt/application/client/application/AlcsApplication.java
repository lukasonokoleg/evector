package lt.jmsys.spark.gwt.application.client.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.application.presenter.ReloginPresenter;
import lt.jmsys.spark.gwt.application.client.application.view.ReloginView;
import lt.jmsys.spark.gwt.application.client.common.dialog.ConfirmHelper;
import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.client.callback.FailureHandler;
import lt.jmsys.spark.gwt.client.ui.application.ApplicationImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class AlcsApplication extends ApplicationImpl {

    private static final Logger logger = Logger.getLogger(AlcsApplication.class.getName());

    private boolean debug;

    public AlcsApplication() {
        debug = "true".equals(Location.getParameter("debug"));
    }

    @Override
    public void showLogin(boolean reLogin, String pMessage) {
        showLogin(reLogin, pMessage, null);
    }

    public void showLogin(boolean reLogin, String pMessage, AsyncCallback<Void> afterReloginCallback) {
        if (reLogin && null != SessionHolder.getSessionInfo()) {
            ReloginPresenter presenter = new ReloginPresenter(new ReloginView());
            presenter.showReloginPopup(pMessage, afterReloginCallback);
        } else {
            final String url = GWT.getHostPageBaseURL() + "logout?expired=true";
            if (null != pMessage) {
                ConfirmHelper.inform(pMessage, new Command() {

                    @Override
                    public void execute() {
                        Location.assign(url);
                    }
                });
            }else{
                Location.assign(url);
            }
        }
    }

    private boolean isDebugEnabled() {
        return debug;
    }

    public static void addDebugName(Widget widget) {
        AlcsApplication instance = (AlcsApplication) getApplication();
        if (instance.isDebugEnabled()) {
            try {
                widget.getElement().setAttribute("_name", widget.getClass().getName());
            } catch (Exception ignore) {
            }
        }
        try {
            widget.ensureDebugId(widget.getClass().getName());
        } catch (Exception ignore) {
        }
    }

    @Override
    public void onUncaughtException(Throwable e) {
        if (e instanceof JavaScriptException) {
            String s = e.toString();
            if (s.contains("-2147467259")) {// workaround for ALCS-1527
                String uid = FailureHandler.generateUID();
                logger.log(Level.SEVERE, "onUncaughtException: internal error (ignored) " + uid, e);
                return;
            }
        }
        super.onUncaughtException(e);
    }

    @Override
    public String getUserName() {
        SessionInfo session = SessionHolder.getSessionInfo();
        if (null != session) {
            return session.getFirstName() + " " + session.getLastName();
        } else {
            return null;
        }
    }

}
