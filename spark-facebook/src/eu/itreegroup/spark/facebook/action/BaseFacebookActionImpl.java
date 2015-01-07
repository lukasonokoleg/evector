package eu.itreegroup.spark.facebook.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import eu.itreegroup.spark.facebook.server.FacebookServlet;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;

public class BaseFacebookActionImpl implements BaseFacebookAction {

    private final static Logger LOGGER = Logger.getLogger(BaseFacebookActionImpl.class);

    private ThreadLocal<HttpServlet> perThreadServlet = new ThreadLocal<HttpServlet>();
    private ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();
    private ThreadLocal<HttpServletResponse> perThreadResponse = new ThreadLocal<HttpServletResponse>();

    public BaseFacebookActionImpl() {

    }

    public void setServletContextData(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        ensureAllNotNull(servlet, request, response);
        perThreadServlet.set(servlet);
        perThreadRequest.set(request);
        perThreadResponse.set(response);
    }

    @Override
    public HttpServlet getHttpServlet() {
        HttpServlet retVal = perThreadServlet.get();
        ensureNotNull(retVal);
        return retVal;
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        HttpServletRequest retVal = perThreadRequest.get();
        ensureNotNull(retVal);
        return retVal;
    }

    @Override
    public HttpServletResponse getHttpServletResponse() {
        HttpServletResponse retVal = perThreadResponse.get();
        ensureNotNull(retVal);
        return retVal;
    }

    @Override
    public Facebook getFacebook() {
        HttpServletRequest request = getHttpServletRequest();
        Object facebookAsObject = request.getSession(false).getAttribute(FacebookServlet.HTTP_SERVLET_SESSION_ATTR_FACEBOOK);
        ensureNotNull(facebookAsObject);
        Facebook retVal = null;
        if (facebookAsObject instanceof Facebook) {
            retVal = (Facebook) facebookAsObject;
        }
        return retVal;
    }

    @Override
    public void setAccessToken(String token) throws FacebookException {
        Facebook facebook = getFacebook();
        AccessToken accessToken = facebook.getOAuthAccessToken(token);
        facebook.setOAuthAccessToken(accessToken);
    }

    private static void ensureAllNotNull(Object... objects) {
        ensureNotNull(objects);
        for (Object object : objects) {
            ensureNotNull(object);
        }
    }

    private static void ensureNotNull(Object object) {
        if (object == null) {
            NullPointerException e = new NullPointerException();
            LOGGER.error("Ensuring object has not NULL value.", e);
            throw e;
        }
    }

    @Override
    public void clean() {
        perThreadRequest.remove();
        perThreadResponse.remove();
        perThreadServlet.remove();
    }

}
