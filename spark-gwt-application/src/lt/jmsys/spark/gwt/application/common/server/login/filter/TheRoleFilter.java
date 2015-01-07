package lt.jmsys.spark.gwt.application.common.server.login.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import eu.itreegroup.spark.application.bean.UserSession;
import eu.itreegroup.spark.application.service.SessionService;

public class TheRoleFilter implements Filter {

    protected static final String ROLE_CODE_MOCK_ROLE = "MOCK_ROLE";

    public static final String LOGIN_URL_PARAM = "loginUrl";
    public static final String HOME_URL_PARAM = "homeUrl";

    public static final String COMAPNAY_ID_REQ_PARAM = "cid";
    public static final String LOCALE_REQ_PARAM = "locale";

    private static final String SUBCONTEXTS_PARAM = "subcontexts";
    private static final String ROLES_SEPARATOR = ";;;";
    private static final String INROLE_SEPARATOR = ":::";

    private Map<String, Pattern> rolesPatterns = new HashMap<String, Pattern>();
    private Pattern skipPattern;
    private Pattern reloginPattern;
    private String loginUrl;
    private String homeUrl;

    private WebApplicationContext springContext;

    @Override
    public void init(FilterConfig pConfig) throws ServletException {
        {
            loginUrl = pConfig.getInitParameter(LOGIN_URL_PARAM);
            if (loginUrl == null || loginUrl.trim().length() == 0) {
                loginUrl = pConfig.getServletContext().getInitParameter(LOGIN_URL_PARAM);
                if (loginUrl == null || loginUrl.trim().length() == 0) {
                    throw new ServletException("Parameter '" + LOGIN_URL_PARAM + "' must be set.");
                }
            }
            homeUrl = pConfig.getInitParameter(HOME_URL_PARAM);
            if (homeUrl == null || homeUrl.trim().length() == 0) {
                homeUrl = pConfig.getServletContext().getInitParameter(HOME_URL_PARAM);
                if (homeUrl == null || homeUrl.trim().length() == 0) {
                    throw new ServletException("Parameter '" + HOME_URL_PARAM + "' must be set.");
                }
            }
        }

        {
            final String SKIP_PARAM = "skip";
            String skip = pConfig.getInitParameter(SKIP_PARAM);
            if (skip == null || skip.trim().length() == 0) {
                throw new ServletException("Parameter '" + SKIP_PARAM + "' must be set.");
            }
            try {
                skipPattern = Pattern.compile(skip);
            } catch (Exception e) {
                throw new ServletException("Invalid Skip pattern '" + skip + "'", e);
            }
        }

        {
            final String RELOGIN_PARAM = "relogin";
            String relogin = pConfig.getInitParameter(RELOGIN_PARAM);
            if (relogin == null || relogin.trim().length() == 0) {
                throw new ServletException("Parameter '" + RELOGIN_PARAM + "' must be set.");
            }
            try {
                reloginPattern = Pattern.compile(relogin);
            } catch (Exception e) {
                throw new ServletException("Invalid Relogin pattern '" + relogin + "'", e);
            }
        }

        {
            String subcontexts = pConfig.getInitParameter(SUBCONTEXTS_PARAM);
            if (subcontexts == null || subcontexts.trim().length() == 0) {
                throw new ServletException("Parameter '" + SUBCONTEXTS_PARAM + "' must be set.");
            }
            Logger.getLogger(getClass()).info("Processing RolesMap '" + subcontexts + "'");
            String[] scs = subcontexts.split(ROLES_SEPARATOR);
            for (String sc : scs) {
                Logger.getLogger(getClass()).info("Processing RoleMap '" + sc + "'");
                String[] s = sc.split(INROLE_SEPARATOR);
                if (s == null || s.length != 2 || s[0] == null || s[0].trim().length() == 0 || s[1].trim().length() == 0) {
                    throw new ServletException("Invalid RoleMap value '" + sc + "'");
                }
                try {
                    rolesPatterns.put(s[0], Pattern.compile(s[1]));
                } catch (Exception e) {
                    throw new ServletException("Invalid RoleMap pattern '" + s[0] + "' , '" + s[1] + "'", e);
                }
            }
        }
        springContext = WebApplicationContextUtils.getWebApplicationContext(pConfig.getServletContext());
    }

    /**
     * URLs matched by pattern in parameter 'skip' pass through filter. Role is
     * obtained from session. If the role matches one of roles in parameter
     * 'subcontexts' then it's pattern is used. If URL matches role's pattern
     * then it passes through filter. If URL does not match role's pattern then:
     * If URL matches 'relogin' pattern and request is not a GWT (does not have
     * 'X-GWT-Permutation' header) or mobile (does not have 'Mobile-Permutation'
     * header) service call then redirect to 'loginUrl' is performed, otherwise
     * 401-Unauthorised response is returned. Otherwise it's malconfiguration of
     * this filter - return 401-Unauthorised response and log error. If there is
     * no role in session then: If URL matches 'relogin' pattern and request is
     * not a GWT (does not have 'X-GWT-Permutation' header) or mobile (does not
     * have 'Mobile-Permutation' header) service call then redirect to
     * 'loginUrl' is performed, otherwise 401-Unauthorised response is returned.
     */
    @Override
    public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pChain) throws IOException, ServletException {

        boolean ok = false;
        HttpServletRequest hRequest = (HttpServletRequest) pRequest;
        HttpServletResponse hResponse = (HttpServletResponse) pResponse;
        final boolean isGWTServiceCall = hRequest.getHeader("X-GWT-Permutation") != null;
        final boolean isMobileServiceCall = hRequest.getHeader("Mobile-Permutation") != null;
        String url = hRequest.getServletPath();
        final boolean isJsResource = url.endsWith(".js");
        final boolean isServiceCall = isGWTServiceCall || isMobileServiceCall || isJsResource;

        if (url != null) {
            try {
                ok = skipPattern.matcher(url).matches();
            } catch (Exception e) {
                Logger.getLogger(getClass()).error("", e);
            }
            if (ok) {
                Logger.getLogger(getClass()).debug("SKIPPING URL: " + url);
                followThrough(pRequest, pResponse, pChain);
            } else {
                try {
                    String roleName = getRoleCode();
                    if (roleName != null) {
                        Pattern pattern = rolesPatterns.get(roleName);
                        if (pattern != null) {
                            if (ok = pattern.matcher(url).matches()) {
                                Logger.getLogger(getClass()).debug("Matches by ROLE URL: " + url);
                                if (checkClientCompany(hRequest)) {
                                    followThrough(pRequest, pResponse, pChain);
                                } else {
                                    refresh(hRequest, hResponse, isServiceCall);
                                }
                            } else {
                                Logger.getLogger(getClass()).debug("Matcher FAIL" + pattern.toString() + " ---> " + url);
                                if (ok = (reloginPattern.matcher(url).matches() && !isServiceCall)) {
                                    Logger.getLogger(getClass()).debug("Re-Login from ROLE URL: " + url);
                                    relogin(hRequest, (HttpServletResponse) pResponse);
                                }
                            }
                        } else {
                            Logger.getLogger(getClass()).error("Filter malconfiguration - role '" + roleName + "' is not described in '" + SUBCONTEXTS_PARAM + "' parameter.");
                        }
                    } else {
                        if (ok = (reloginPattern.matcher(url).matches() && !isServiceCall)) {
                            Logger.getLogger(getClass()).debug("Re-Login NO role URL: " + url);
                            relogin(hRequest, hResponse);
                        }
                    }
                } catch (Exception e) {
                    Logger.getLogger(getClass()).error("", e);
                }
            }
        }
        if (!ok) {
            Logger.getLogger(getClass()).debug("UNAUTHORIZED URL: " + url);
            unauthorized(hRequest, (HttpServletResponse) pResponse);
        }
    }

    private boolean checkClientCompany(HttpServletRequest hRequest) {
        /*
         * String orgId = hRequest.getHeader("X-ALCS-OrgId"); Als_session_ot
         * session = LoginServiceImpl.getDbSession(hRequest.getSession(false));
         * if (null != orgId && null != session && null != session.getOrg_id())
         * { return
         * orgId.equals(Integer.toString(session.getOrg_id().intValue())); }
         * else { return true; }
         */
        return true;
    }

    @Override
    public void destroy() {
        // Logger.getLogger(getClass()).debug("Destroying TheRoleFilter");
    }

    private void followThrough(ServletRequest pRequest, ServletResponse pResponse, FilterChain pChain) throws IOException, ServletException {
        pRequest.setCharacterEncoding("UTF-8");
        pChain.doFilter(pRequest, pResponse);
    }

    private void unauthorized(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException, ServletException {
        pResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void refresh(HttpServletRequest pRequest, HttpServletResponse pResponse, boolean serviceCall) throws IOException, ServletException {
        if (serviceCall) {
            pResponse.setHeader("X-ALCS-Refresh", "1");
            pResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String url = pRequest.getContextPath() + this.homeUrl;
            pResponse.sendRedirect(url);
        }
    }

    private void relogin(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException, ServletException {

        String locale = pRequest.getParameter(LOCALE_REQ_PARAM);
        if (null != locale && locale.length() != 2) {
            locale = null;
        }

        String url = pRequest.getContextPath() + this.loginUrl;
        if (null != locale) {
            url += (url.indexOf('?') == -1 ? "?" : "&") + LOCALE_REQ_PARAM + "=" + URLEncoder.encode(locale, "UTF-8");
        }

        String cid = pRequest.getParameter(COMAPNAY_ID_REQ_PARAM);
        if (null != cid) {
            url += (url.indexOf('?') == -1 ? "?" : "&") + COMAPNAY_ID_REQ_PARAM + "=" + URLEncoder.encode(cid, "UTF-8");
        }

        pResponse.sendRedirect(url);
    }

    private String getRoleCode() {
        SessionService sessionService = springContext.getBean(SessionService.class);
        UserSession userSession = null;
        if (sessionService.hasCurrentSession()) {
            try {
                userSession = sessionService.currentSession();
            } catch (SparkBusinessException e) {
                Logger.getLogger(getClass()).error("Cannot get session", e);
            }
        }
        String retVal = calculateRoleFromUserSession(userSession);
        return retVal;
    }

    protected String calculateRoleFromUserSession(UserSession userSession) {
        String retVal = null;
        if (null != userSession && null != userSession.getKey()) {
            retVal = ROLE_CODE_MOCK_ROLE;
        }
        return retVal;
    }

}
