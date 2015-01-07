package univ.evector.gwt.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.common.server.login.filter.TheRoleFilter;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;

import univ.evector.beans.UserSession;
import univ.evector.beans.UserSessionHolder;
import univ.evector.db.dao.FacebookDao;
import univ.evector.gwt.client.L001.bean.LoginType;
import univ.evector.gwt.client.service.LoginService;
import univ.evector.internal.service.UserService;
import eu.itreegroup.spark.application.service.SettingsService;
import eu.itreegroup.spark.gwt.rpc.AcceptsHttpServletContext;
import eu.itreegroup.spark.gwt.rpc.HttpServletContext;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.User;

@Service("loginService")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class LoginServiceImpl implements LoginService, ServletContextAware, AcceptsHttpServletContext {

    private static final Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

    private static final String KEEP_SESSION_FRESH_KEY = LoginServiceImpl.class.getName() + ".keep_session_fresh";
    private static final String SESSION_KEY = LoginServiceImpl.class.getName() + ".UserSession";

    private static final String COOKIE_PATH_SUFFIX = "/login/LoginService";

    private String homeUrl;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private FacebookDao facebookDao;

    private HttpServletContext httpServletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        homeUrl = servletContext.getInitParameter(TheRoleFilter.HOME_URL_PARAM);
    }

    @Override
    public LoginResult create_session_by_pass(String username, String password, String newPassword1, String newPassword2, boolean rememberLogin) throws SparkBusinessException {
        UserSession userSession = null;
        String ip = getUserIp();
        userSession = userService.authenticateByUserName(username, password, ip);
        if (!rememberLogin) {
            String loginKey = getCookie(LoginResult.COOKIE_LOGIN_KEY);
            userService.setLoginKeyExpired(loginKey);
            removeSessionCookie(LoginResult.COOKIE_LOGIN_KEY, COOKIE_PATH_SUFFIX);
            removeSessionCookie(LoginResult.COOKIE_WORKPLACE_UID, COOKIE_PATH_SUFFIX);
        }
        return finishLogin(userSession, rememberLogin);
    }

    @Override
    public LoginResult create_session_by_facebook() throws SparkBusinessException {
        UserSessionHolder userSessionHolder = context.getBean(UserSessionHolder.class);
        Facebook facebook = userSessionHolder.getFacebook();
        UserSession userSession = userService.authenticateByFacebook(facebook);
        return finishLogin(userSession, true);
    }

    private String getUserIp() {
        String ip = null;
        if (httpServletContext != null) {
            ip = httpServletContext.getClientAddress();
        }
        return ip;
    }

    @Override
    public LoginResult relogin(Double userId, String password, Double companyId) throws SparkBusinessException {

        return null;
    }

    @Override
    public LoginResult create_session_by_key() throws SparkBusinessException {

        return null;
    }

    @Override
    public void forgotPassword(String email) throws SparkBusinessException {

    }

    @Override
    public Boolean hasSessionKey() throws SparkBusinessException {
        return false;
    }

    public boolean hasFacebook() throws SparkBusinessException {
        boolean retVal = false;
        try {
            UserSessionHolder sessionHolder = context.getBean(UserSessionHolder.class);
            Facebook facebook = sessionHolder.getFacebook();
            if (facebook != null) {
                User user = facebook.getMe();
                retVal = user != null;
            }
        } catch (FacebookException e) {
            LOGGER.error("Cought FacebookException while verifying legal access.", e);
        }
        return retVal;
    }

    @Override
    public void setHttpServletContext(HttpServletContext httpServletContext) {
        this.httpServletContext = httpServletContext;
    }

    protected LoginResult finishLogin(UserSession userSession, boolean rememberLogin) throws SparkBusinessException {
        UserSessionHolder sessionHolder = context.getBean(UserSessionHolder.class);
        sessionHolder.setUserSession(userSession);
        if (null != userSession.getTimeout()) {
            httpServletContext.getSession(false).setMaxInactiveInterval((int) (userSession.getTimeout() * 60));
        }
        LoginResult loginResult = new LoginResult();

        String redirectUrl = getRedirectUrl(userSession);
        loginResult.setRedirectUrl(redirectUrl);
        initHttpSession(userSession, rememberLogin);
        if (rememberLogin) {
            try {
                String workplaceUid = generateWorkplaceUid();

                String sessionKey = userSession.getKey();

                String loginKey = userService.createLoginKey(sessionKey, workplaceUid);

                loginResult.setWorkplaceUid(workplaceUid);
                loginResult.setLoginKey(loginKey);

                addCookie(LoginResult.COOKIE_LOGIN_KEY, loginKey, COOKIE_PATH_SUFFIX);
                addCookie(LoginResult.COOKIE_WORKPLACE_UID, workplaceUid, COOKIE_PATH_SUFFIX);

            } catch (Exception ignore) {
                LOGGER.error("Failed to remember login", ignore);
            }
        }

        return loginResult;
    }

    private String generateWorkplaceUid() throws Exception {
        String retVal = null;
        if (httpServletContext != null) {
            retVal = httpServletContext.generateWorkplaceUid();
        }
        return retVal;
    }

    private void initHttpSession(UserSession dbSession, boolean keepSessionFresh) throws SparkBusinessException {
        if (httpServletContext != null) {
            HttpSession session = httpServletContext.getSession(true);
            session.setAttribute(SESSION_KEY, dbSession);
            if (keepSessionFresh) {
                session.setAttribute(KEEP_SESSION_FRESH_KEY, keepSessionFresh);
            }
        }
    }

    public String getRedirectUrl(UserSession session) {
        String retVal = httpServletContext.getRedirectUrl(homeUrl);
        return retVal;
    }

    private void addCookie(String cookieName, String cookieValue, String cookiePathSuffix) {
        if (httpServletContext != null) {
            httpServletContext.addCokie(cookieName, cookieValue, cookiePathSuffix);
        }
    }

    private String getCookie(String name) {
        String retVal = null;
        if (httpServletContext != null) {
            retVal = httpServletContext.getCookie(name);
        }
        return retVal;
    }

    private void removeSessionCookie(String cookieName, String cookiePathSuffix) {
        if (httpServletContext != null) {
            httpServletContext.removeSessionCookie(cookieName, cookiePathSuffix);
        }
    }

    @Override
    public String getFaceBookLoginUrl() throws SparkBusinessException {
        return facebookDao.getFaceBookLoginUrl();
    }

    @Override
    public LoginType getUserLoginType() throws SparkBusinessException {
        LoginType retVal = null;
        if (hasSessionKey()) {
            retVal = LoginType.LOGIN_BY_SESSION_KEY;
        } else if (hasFacebook()) {
            retVal = LoginType.LOGIN_BY_FACEBOOK;
        } else {
            retVal = LoginType.LOGIN_BY_PASS;
        }
        return retVal;
    }
}
