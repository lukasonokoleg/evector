package eu.itreegroup.spark.facebook.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import eu.itreegroup.spark.facebook.action.FacebookActionFactory;
import eu.itreegroup.spark.facebook.action.FacebookCallbackAction;
import eu.itreegroup.spark.facebook.action.FacebookCallbackImpl;
import eu.itreegroup.spark.facebook.action.FacebookLoginAction;
import eu.itreegroup.spark.facebook.action.FacebookLoginImpl;
import eu.itreegroup.spark.facebook.action.FacebookLogoutAction;
import eu.itreegroup.spark.facebook.action.FacebookLogoutImpl;
import eu.itreegroup.spark.facebook.application.FacebookActionHandler;
import eu.itreegroup.spark.facebook.application.FacebookSettingsProvider;
import eu.itreegroup.spark.facebook.helper.ClassHelper;
import eu.itreegroup.spark.facebook.helper.ErrorMessageHelper;
import eu.itreegroup.spark.facebook.helper.FacebookHelper;
import eu.itreegroup.spark.facebook.helper.StringHelper;
import facebook4j.Facebook;

@SuppressWarnings("serial")
public class FacebookServlet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(FacebookServlet.class);

    public final static String ACTION_PARAMETER = "action";

    public final static String LOGIN_ACTION_CODE = "login";
    public final static String CALLBACK_ACTION_CODE = "callback";
    public final static String LOGOUT_ACTION_CODE = "logout";

    private final static String SERVLET_EXCEPTION_MESSAGE_PREFFIX = ErrorMessageHelper.getServletInitException(FacebookServlet.class.getSimpleName());

    public final static String HTTP_SERVLET_SESSION_ATTR_FACEBOOK = "facebook";
    public final static String HTTP_SERVLET_REQUEST_ATTR_CODE = "code";

    public final static String INIT_PARAM_FACEBOOK_SETTINGS_PROVIDER = "facebookSettingsProviderClassName";

    protected FacebookSettingsProvider fbSettingsProvider;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        processInit(config);
    }

    protected void processInit(ServletConfig config) throws ServletException {
        initFacebookSettingsProvider(config);
    }

    protected void initFacebookSettingsProvider(ServletConfig config) throws ServletException {
        String className = config.getInitParameter(INIT_PARAM_FACEBOOK_SETTINGS_PROVIDER);
        LOGGER.debug("Initializing FacebookPropertyProvider.");
        if (fbSettingsProvider == null) {
            try {
                fbSettingsProvider = ClassHelper.createObjectByClassName(className, FacebookSettingsProvider.class);
            } catch (Exception e) {
                throw new ServletException(SERVLET_EXCEPTION_MESSAGE_PREFFIX, e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUrl = request.getRequestURI();
            if (!StringHelper.isEmpty(requestUrl)) {
                if (requestUrl.contains(LOGIN_ACTION_CODE)) {
                    onLoginAction(request, response);
                } else if (requestUrl.contains(CALLBACK_ACTION_CODE)) {
                    onCallbackAction(request, response);
                } else if (requestUrl.contains(LOGOUT_ACTION_CODE)) {
                    onLogoutAction(request, response);
                } else {
                    onWrongActionCode(request, response);
                }
            } else {
                onWrongActionCode(request, response);
            }
        } catch (Exception e) {
            onExceptionHappened(e, request, response);
        }
    }

    protected void onExceptionHappened(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        onWrongActionCode(request, response);
    }

    protected void onLoginAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Facebook facebook = FacebookHelper.getFacebook(fbSettingsProvider);
        HttpSession session = request.getSession(false);
        try {
            if (null != session) {
                session.setAttribute(HTTP_SERVLET_SESSION_ATTR_FACEBOOK, facebook);
                FacebookLoginAction action = FacebookActionFactory.getAction(FacebookLoginImpl.class, this, request, response);
                FacebookActionHandler actionHandler = fbSettingsProvider.getFacebookActionHandler();
                actionHandler.onFacebookLoginAction(action);
            }
        } catch (Throwable e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Http session is missing.");
        }
    }

    protected void onCallbackAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FacebookCallbackAction action = FacebookActionFactory.getAction(FacebookCallbackImpl.class, this, request, response);

        String accessToken = request.getParameter(HTTP_SERVLET_REQUEST_ATTR_CODE);
        action.setAccessToken(accessToken);

        FacebookActionHandler actionHandler = fbSettingsProvider.getFacebookActionHandler();
        actionHandler.onFacebookCallbackAction(action);
    }

    protected void onLogoutAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FacebookLogoutAction action = FacebookActionFactory.getAction(FacebookLogoutImpl.class, this, request, response);

        FacebookActionHandler actionHandler = fbSettingsProvider.getFacebookActionHandler();
        actionHandler.onFacebookLogoutAction(action);
    }

    protected void onWrongActionCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action code was not recognized.");
    }

}