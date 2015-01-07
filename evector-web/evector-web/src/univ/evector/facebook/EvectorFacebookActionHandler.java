package univ.evector.facebook;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import univ.evector.beans.UserSessionHolder;
import eu.itreegroup.spark.facebook.action.FacebookCallbackAction;
import eu.itreegroup.spark.facebook.action.FacebookLoginAction;
import eu.itreegroup.spark.facebook.action.FacebookLogoutAction;
import eu.itreegroup.spark.facebook.application.FacebookActionHandler;
import facebook4j.Facebook;

@Component
public class EvectorFacebookActionHandler implements FacebookActionHandler {

    private final static String URL_PARAM_ERROR_CODE = "error_code";

    private final static String URL_REDIRECT = "http://evectordev.eu:8080/evector-web/login.html";

    @Override
    public void onFacebookLoginAction(FacebookLoginAction fbLogin) throws IOException {
        Facebook facebook = fbLogin.getFacebook();
        fbLogin.getHttpServletRequest().getSession().setAttribute("facebook", facebook);
        StringBuffer callbackURL = fbLogin.getHttpServletRequest().getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback");
        fbLogin.getHttpServletResponse().sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
    }

    @Override
    public void onFacebookCallbackAction(FacebookCallbackAction fbCallback) throws IOException {
        HttpServletRequest request = fbCallback.getHttpServletRequest();
        HttpServletResponse response = fbCallback.getHttpServletResponse();
        if (!hasErrorCode(request.getParameterMap())) {
            Facebook facebook = fbCallback.getFacebook();

            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fbCallback.getHttpServlet().getServletContext());
            context.getBean(UserSessionHolder.class).setFacebook(facebook);

            response.sendRedirect(URL_REDIRECT);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Params: " + request.getParameterMap());
        }
    }

    private static boolean hasErrorCode(Map<String, String[]> httpQuery) {
        boolean retVal = false;
        if (httpQuery != null && httpQuery.containsKey(URL_PARAM_ERROR_CODE)) {
            retVal = true;
        }
        return retVal;
    }

    @Override
    public void onFacebookLogoutAction(FacebookLogoutAction fbLogout) throws IOException {
        Facebook facebook = fbLogout.getFacebook();

        HttpServletRequest request = fbLogout.getHttpServletRequest();
        HttpServletResponse response = fbLogout.getHttpServletResponse();
        String accessToken = "";
        try {
            accessToken = facebook.getOAuthAccessToken().getToken();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        request.getSession(false).invalidate();
        StringBuffer next = request.getRequestURL();
        int index = next.lastIndexOf("/");
        next.replace(index + 1, next.length(), "");

        response.sendRedirect("http://www.facebook.com/logout.php?next=" + next.toString() + "&access_token=" + accessToken);
    }
}
