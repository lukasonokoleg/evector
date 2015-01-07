package eu.itreegroup.spark.gwt.rpc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lt.jmsys.spark.gwt.client.callback.LoginException;

public interface HttpServletContext {

    void removeSessionCookie(String cookieName, String cookiePathSuffix);

    void addCokie(String cookieName, String cookieValue, String cookiePathSuffix);

    String getRedirectUrl(String homeUrl);

    String generateWorkplaceUid() throws Exception;

    void cleanHttpSession();

    String getCookie(String name);

    String getClientAddress();

    HttpSession getSession(boolean create) throws LoginException;

    String getServletInitParameter(String name);

    HttpServletRequest getHttpServletRequest();

}
