package eu.itreegroup.spark.gwt.rpc;

import java.security.MessageDigest;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lt.jmsys.spark.gwt.client.callback.LoginException;

public class HttpServletHelper {

    private static final int REMEMBER_LOGIN_COOKIE_EXPIRES = 356 * 24 * 60 * 60;

    public static String getRedirectUrl(HttpServletRequest httpServletRequest, String homeUrl) {
        String retVal = null;
        if (httpServletRequest != null) {
            retVal = httpServletRequest.getContextPath() + homeUrl;
        }
        return retVal;
    }

    public static void removeSessionCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String cookieName, String cookiePathSuffix) {
        if (httpServletRequest != null) {
            String path = HttpServletHelper.getCookiePath(httpServletRequest, cookiePathSuffix);
            if (httpServletResponse != null) {
                Cookie sessionCookie = new Cookie(cookieName, null);
                sessionCookie.setPath(path);
                sessionCookie.setMaxAge(0);
                httpServletResponse.addCookie(sessionCookie);
            }
        }
    }

    public static HttpSession getSession(HttpServletRequest httpServletRequest, boolean create) throws LoginException {
        HttpSession retVal = null;
        if (httpServletRequest != null) {
            retVal = httpServletRequest.getSession(create);
        } else {
            throw new LoginException(LoginException.Code.NOREQUEST);
        }
        if (create && retVal == null) {
            throw new LoginException(LoginException.Code.NOSESSIONCREATE);
        }
        return retVal;
    }

    public static void addCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String cookieName, String cookieValue, String cookiePathSuffix) {
        if (httpServletResponse != null) {
            String path = getCookiePath(httpServletRequest, cookiePathSuffix);
            Cookie sessionCookie = new Cookie(cookieName, cookieValue);
            sessionCookie.setPath(path);
            sessionCookie.setMaxAge(REMEMBER_LOGIN_COOKIE_EXPIRES);
            httpServletResponse.addCookie(sessionCookie);
        }
    }

    public static String getCookiePath(HttpServletRequest httpServletRequest, String cookiePathSuffix) {
        String retVal = null;
        if (httpServletRequest != null) {
            retVal = httpServletRequest.getContextPath() + cookiePathSuffix;
        }
        return retVal;
    }

    public static void cleanHttpSession(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            HttpSession session = httpServletRequest.getSession(false);
            if (null != session) {
                Enumeration<String> names = session.getAttributeNames();
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    session.removeAttribute(name);
                }
            }
        }
    }

    public static String getCookie(HttpServletRequest httpServletRequest, String name) {
        if (httpServletRequest != null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (null != cookies) {
                for (Cookie c : cookies) {
                    if (name.equals(c.getName())) {
                        return c.getValue();
                    }
                }
            }
        }
        return null;
    }

    public static String getClientAddress(HttpServletRequest httpServletRequest) {
        String retVal = null;
        if (httpServletRequest != null) {
            retVal = httpServletRequest.getHeader("X-Real-IP");
            if (null == retVal) {
                retVal = httpServletRequest.getRemoteAddr();
            }
        }
        return retVal;
    }

    public static String generateWorkplaceUid(HttpServletRequest httpServletRequest) throws Exception {
        String retVal = null;
        if (httpServletRequest != null) {
            String userAgent = httpServletRequest.getHeader("User-Agent");
            retVal = hashHeader(userAgent) + (long) (Math.random() * 1000000000);
        }
        return retVal;
    }

    private static String hashHeader(String header) throws Exception {
        header = header.toUpperCase();
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < header.length(); i++) {
            char c = header.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                buff.append(c);
            }
        }

        byte[] bytesOfMessage = buff.toString().getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(bytesOfMessage);

        buff.setLength(0);

        for (byte b : digest) {
            buff.append(Integer.toString(b));
        }

        return buff.toString();
    }

}
