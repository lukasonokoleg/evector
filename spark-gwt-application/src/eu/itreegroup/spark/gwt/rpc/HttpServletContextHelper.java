package eu.itreegroup.spark.gwt.rpc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lt.jmsys.spark.gwt.client.callback.LoginException;

public class HttpServletContextHelper {

    private static HttpServletThreadContext threadContext = new HttpServletThreadContext();

    public static void setContext(AcceptsHttpServletContext acceptsContext, HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        threadContext.perThreadRequest.set(request);
        threadContext.perThreadResponse.set(response);
        threadContext.perThreadServlet.set(servlet);
        acceptsContext.setHttpServletContext(threadContext);
    }

    public static void cleanupContext() {
        threadContext.perThreadRequest.remove();
        threadContext.perThreadResponse.remove();
        threadContext.perThreadServlet.remove();
    }

    private static class HttpServletThreadContext implements HttpServletContext {

        private ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();
        private ThreadLocal<HttpServletResponse> perThreadResponse = new ThreadLocal<HttpServletResponse>();
        private ThreadLocal<HttpServlet> perThreadServlet = new ThreadLocal<HttpServlet>();

        @Override
        public void removeSessionCookie(String cookieName, String cookiePathSuffix) {
            HttpServletHelper.removeSessionCookie(request(), response(), cookieName, cookiePathSuffix);
        }

        @Override
        public HttpSession getSession(boolean create) throws LoginException {
            return HttpServletHelper.getSession(request(), create);
        }

        @Override
        public String getRedirectUrl(String homeUrl) {
            return HttpServletHelper.getRedirectUrl(request(), homeUrl);
        }

        @Override
        public String getCookie(String name) {
            return HttpServletHelper.getCookie(request(), name);
        }

        @Override
        public String getClientAddress() {
            return HttpServletHelper.getClientAddress(request());
        }

        @Override
        public String generateWorkplaceUid() throws Exception {
            return HttpServletHelper.generateWorkplaceUid(request());
        }

        @Override
        public void cleanHttpSession() {
            HttpServletHelper.cleanHttpSession(request());
        }

        @Override
        public void addCokie(String cookieName, String cookieValue, String cookiePathSuffix) {
            HttpServletHelper.addCookie(request(), response(), cookieName, cookieValue, cookiePathSuffix);
        }

        @Override
        public String getServletInitParameter(String name) {
            return servlet().getInitParameter(name);
        }

        @Override
        public HttpServletRequest getHttpServletRequest() {
            return request();
        }

        private HttpServletRequest request() {
            HttpServletRequest request = perThreadRequest.get();
            if (null == request) {
                throw new IllegalStateException("HttpServletContext was not set for this thread");
            }
            return request;
        }

        private HttpServletResponse response() {
            HttpServletResponse response = perThreadResponse.get();
            if (null == response) {
                throw new IllegalStateException("HttpServletContext was not set for this thread");
            }
            return response;
        }

        private HttpServlet servlet() {
            HttpServlet servlet = perThreadServlet.get();
            if (null == servlet) {
                throw new IllegalStateException("HttpServletContext was not set for this thread");
            }
            return servlet;
        }
    }
}
