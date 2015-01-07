package univ.evector.gwt.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import univ.evector.gwt.client.login.view.EvectorLoginView;
import univ.evector.internal.service.UserService;

public class EmailValidationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(EmailValidationServlet.class);

    public static final String URL_PARAM_ID = "id";
    public static final String URL_PARAM_TOKEN = "token";
    public static final String URL_PARAM_EMAIL_CONFIRM = "emailconfirm";

    private WebApplicationContext springContext;

    @Override
    public void init() throws ServletException {
        super.init();
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userId = req.getParameter(URL_PARAM_ID);
            String token = req.getParameter(URL_PARAM_TOKEN);
            String isEmailConfirm = req.getParameter(URL_PARAM_EMAIL_CONFIRM);

            if (null == userId || null == token || token.isEmpty()) {
                throw new IllegalArgumentException("both user id and token are required; userId = " + userId + ", token = " + token);
            }

            UserService userService = springContext.getBean(UserService.class);
            userService.validateUserEmail(userId, token);
            if (null == isEmailConfirm) {
                redirect2AfterRegistrationConfirm(req, resp);
            } else {
                redirect2Error(req, resp);
            }
        } catch (Exception e) {
            redirect2Error(req, resp, e);
        }

    }

    protected void redirect2AfterRegistrationConfirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/login.html?" + EvectorLoginView.MSG_PARAM_WELCOME);
    }

    protected void redirect2Error(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirect2Error(req, resp, null);
    }

    protected void redirect2Error(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        if (e != null) {
            log.error("Email validation error", e);
        } else {
            log.error("Email validation error");
        }
        resp.sendRedirect(req.getContextPath() + "/error.html");
    }

}