package univ.evector.gwt.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lt.jmsys.spark.gwt.application.common.server.login.filter.TheRoleFilter;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import eu.itreegroup.spark.application.bean.LogoutCause;
import eu.itreegroup.spark.application.bean.SparkLogoutCause;
import eu.itreegroup.spark.application.bean.UserSession;
import eu.itreegroup.spark.application.service.SessionService;
import eu.itreegroup.spark.gwt.rpc.HttpServletHelper;

public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(LogoutServlet.class);

    private String LOGIN_URL;
    private WebApplicationContext springContext;

    @Override
    public void init() throws ServletException {
        super.init();
        LOGIN_URL = getServletContext().getInitParameter(TheRoleFilter.LOGIN_URL_PARAM);
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean expired = "true".equals(req.getParameter("expired"));
        logout(req, resp, expired);
        resp.sendRedirect(req.getContextPath() + LOGIN_URL);
    }

    private void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean expired) {
        SessionService sessionService = (SessionService) springContext.getBean("userService");
        LogoutCause logoutCause = expired ? SparkLogoutCause.EXPIRED : SparkLogoutCause.CLOSED_BY_USER;
        if (sessionService.hasCurrentSession()) {
            try {
                UserSession session = sessionService.currentSession();
                sessionService.closeSession(session.getKey(), logoutCause);
                httpServletRequest.getSession().invalidate();
            } catch (Exception e) {
                log.error("Error on logout", e);
            } finally {
                String cookiePathSuffix = "/login/LoginService";
                HttpServletHelper.removeSessionCookie(httpServletRequest, httpServletResponse, LoginResult.COOKIE_LOGIN_KEY, cookiePathSuffix);
                HttpServletHelper.removeSessionCookie(httpServletRequest, httpServletResponse, LoginResult.COOKIE_WORKPLACE_UID, cookiePathSuffix);
            }
        }
    }

}