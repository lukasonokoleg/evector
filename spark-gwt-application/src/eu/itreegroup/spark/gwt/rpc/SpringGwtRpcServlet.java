package eu.itreegroup.spark.gwt.rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import lt.jmsys.spark.gwt.application.shared.security.PrivateData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.UnexpectedException;

import eu.itreegroup.spark.application.bean.UserSession;
import eu.itreegroup.spark.application.helper.AnnotationHelper;
import eu.itreegroup.spark.application.service.SessionService;
import eu.itreegroup.spark.application.shared.security.UnauthorizedAccessException;

public class SpringGwtRpcServlet extends RemoteServiceServlet {

    private static final String PARAM_SERVICE_CLASS = "service-class";

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SpringGwtRpcServlet.class);

    private static final String COOKIE_NAME_DEBUG_MODE = "debug_mode";

    private static final String GENERIC_FAILURE_MSG = "The call failed on the server: \n\n";

    private static final Logger gwtLog = LoggerFactory.getLogger("gwt");

    public static final String AUTHENTICATION_MANAGER_NAME = "authenticationManager";

    private String serviceClassName;

    private WebApplicationContext springContext;

    @Override
    public void log(String msg) {
        log.info(msg);
    }

    @Override
    public void log(String message, Throwable t) {
        log.error(message, t);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        serviceClassName = config.getInitParameter(PARAM_SERVICE_CLASS);
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

    }

    @Override
    public String processCall(String payload) throws SerializationException {
        checkPermutationStrongName();

        RPCRequest rpcRequest = null;
        try {
            rpcRequest = RPC.decodeRequest(payload, null, this);
            onAfterRequestDeserialized(rpcRequest);

            if (log.isDebugEnabled())
                log.debug("Process call: {}", callToString(rpcRequest));

            Object serviceBean = null;
            try {
                serviceBean = getServiceBean(rpcRequest);
                return RPC.invokeAndEncodeResponse(serviceBean, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy(), rpcRequest.getFlags());
            } finally {
                cleanHttpServletContext(serviceBean);
            }

        } catch (UnexpectedException ex) {
            String response = null;

            try {
                if (ex.getCause() instanceof ValidationException) {
                    response = encodeValidationException(rpcRequest, (ValidationException) ex.getCause());
                }
            } catch (Exception e) {
                log.error("Error on encode unexpected exception: " + e, e);
            }

            if (response != null)
                return response;
            throw ex;
        } catch (IncompatibleRemoteServiceException ex) {
            log.error("Error on processing " + callToString(rpcRequest) + " call: " + ex, ex);
            return RPC.encodeResponseForFailure(null, ex);
        } catch (RpcTokenException ex) {
            log.error("Error on processing " + callToString(rpcRequest) + " call: " + ex, ex);
            return RPC.encodeResponseForFailure(null, ex);
        } catch (UnauthorizedAccessException ex) {
            log.error("UnauthorizedAccessException " + callToString(rpcRequest) + " call: " + ex, ex);
            return RPC.encodeResponseForFailure(null, ex);
        } finally {
            addResponseHeadersAndCookies();
        }
    }

    protected Object getServiceBean(RPCRequest rpcRequest) throws UnauthorizedAccessException {
        Object serviceBean = this;
        Class<?> serviceClass = rpcRequest.getMethod().getDeclaringClass();
        if (null != serviceClassName) {
            if (!serviceClassName.equals(serviceClass.getName())) {
                try {
                    Class<?> configuredServiceClass = Class.forName(serviceClassName);
                    if (!serviceClass.isAssignableFrom(configuredServiceClass)) {
                        throw new IllegalArgumentException("Cannot accept request for service " + serviceClass.getName() + ", only requests for " + serviceClassName
                                + " are allowed");
                    }
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        if (!serviceClass.isAssignableFrom(getClass())) {
            WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
            serviceBean = springContext.getBean(serviceClass);
        }
        verifyService(serviceClass, serviceBean);
        setHttpServletContext(serviceBean);
        return serviceBean;
    }

    protected void setHttpServletContext(Object serviceBean) {
        if (serviceBean != null) {
            if (serviceBean instanceof AcceptsHttpServletContext) {
                HttpServletContextHelper.setContext((AcceptsHttpServletContext) serviceBean, this, getThreadLocalRequest(), getThreadLocalResponse());
            }
        }
    }

    protected void cleanHttpServletContext(Object serviceBean) {
        HttpServletContextHelper.cleanupContext();
    }

    protected void verifyService(Class<?> serviceClass, Object serviceBean) throws UnauthorizedAccessException {
        Class<?> serviceBeanClass = AopProxyUtils.ultimateTargetClass(serviceBean);
        boolean transactional = serviceBeanClass.getAnnotation(Transactional.class) != null;
        if (!transactional) {
            throw new UnauthorizedAccessException("Cannot access " + serviceBeanClass + "service. Only services annotated with @" + Transactional.class.getName() + " are allowed");
        }
    }

    /*	private void logRequest(){
            if (log.isDebugEnabled()) {
                String[] p = payload.split("\\|");
                if (p.length >= 7) {
                    String remoteAddress = getThreadLocalRequest().getHeader("X-Real-IP");
                    if (null == remoteAddress) {
                        remoteAddress = getThreadLocalRequest().getRemoteAddr();
                    }
                    Double uid = null;
                    String userName = null;
                    Double sessionId = null;
                    if (null != sessionInfo) {
                        uid = sessionInfo.getUser_id();
                        sessionId = sessionInfo.getDbSessionid();
                        userName = sessionInfo.getPerson_name() + " " + sessionInfo.getPerson_surname();
                    }
                    perfLogger.debug("stats:service," + p[5] + "." + p[6] + "," + (end - start) + ",uid=" + uid + ",name=" + userName + ",ip=" + remoteAddress + ",sid=" + sessionId);
                }
            }
    	}*/

    private String callToString(RPCRequest rpcRequest) {
        if (rpcRequest != null) {
            Method method = rpcRequest.getMethod();
            Annotation[][] annotations = method.getParameterAnnotations();
            StringBuilder sb = new StringBuilder();

            if (rpcRequest.getParameters() != null && rpcRequest.getParameters().length > 0) {
                int index = 0;
                for (Object param : rpcRequest.getParameters()) {
                    boolean privateData = AnnotationHelper.getAnnotation(annotations, index++, PrivateData.class) != null;
                    if (privateData) {
                        sb.append(param != null ? "****" : null);
                    } else if (param != null && param instanceof String) {
                        sb.append('\"').append(param).append('\"');

                    } else if (param != null && param.getClass().isArray()) {
                        sb.append('[');

                        int length = Array.getLength(param);
                        for (int i = 0; i < length; i++)
                            sb.append(Array.get(param, i)).append(", ");

                        if (length > 0)
                            sb.delete(sb.length() - 2, sb.length());

                        sb.append(']');

                    } else {
                        sb.append(param);
                    }

                    sb.append(", ");
                }
            }

            if (sb.length() > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }
            return method.getDeclaringClass().getName() + "." + method.getName() + "(" + sb + ")";

        } else
            return null;
    }

    protected String encodeValidationException(RPCRequest rpcRequest, ValidationException ex) throws SerializationException, IOException {
        if (log.isDebugEnabled())
            log.error("Validation error on processing " + callToString(rpcRequest) + " call: " + ex, ex);
        else
            log.error("Validation error on processing " + callToString(rpcRequest) + " call: " + ex);

        getThreadLocalResponse().sendError(HttpServletResponse.SC_BAD_REQUEST);

        return "";
    }

    @Override
    protected void doUnexpectedFailure(Throwable e) {
        log("Unexpected failure: " + e, e);
        try {
            if (gwtLog.isDebugEnabled()) {
                getThreadLocalResponse().reset();
                getThreadLocalResponse().setContentType("text/plain");
                getThreadLocalResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                setCookie(COOKIE_NAME_DEBUG_MODE, String.valueOf(gwtLog.isDebugEnabled()));
                PrintWriter writer = getThreadLocalResponse().getWriter();
                writer.write(GENERIC_FAILURE_MSG);
                e.printStackTrace(writer);
                writer.close();
            } else {
                super.doUnexpectedFailure(e);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Unable to report failure: " + ex, e);
        }
    }

    protected void addResponseHeadersAndCookies() {
        SessionService sessionService = springContext.getBean(SessionService.class);
        if (sessionService.hasCurrentSession()) {
            try {
                UserSession session = sessionService.currentSession();
                Long timeout = session.getTimeout();
                Date accessTime = (session.getAccessed() != null ? session.getAccessed() : session.getCreated());
                if (null != timeout && null != accessTime) {
                    long now = System.currentTimeMillis();
                    Long expiresAfter = accessTime.getTime() + timeout - now;
                    getThreadLocalResponse().setHeader("X-SPARK-SessionExpires", expiresAfter.toString());
                }
            } catch (Exception e) {
                log.error("Unable to set session expire time header", e);
            }
        } else if (getThreadLocalRequest().getSession(false) != null) {
            HttpSession session = getThreadLocalRequest().getSession(false);
            long expiresAfter = session.getMaxInactiveInterval() * 60 * 1000 - (System.currentTimeMillis() - session.getLastAccessedTime());
            getThreadLocalResponse().setHeader("X-SPARK-SessionExpires", Long.toString(expiresAfter));
        }
        setCookie(COOKIE_NAME_DEBUG_MODE, String.valueOf(gwtLog.isDebugEnabled()));
    }

    public void setCookie(String name, String value) {
        try {
            Cookie[] cookies = getThreadLocalRequest().getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie != null && cookie.getName() != null && cookie.getName().equals(name) && cookie.getValue() != null && cookie.getValue().equals(value)) {
                        return;
                    }
                }
            }
            Cookie cookie = new Cookie(name, value);
            cookie.setPath("/");
            getThreadLocalResponse().addCookie(cookie);
        } catch (Exception e) {
            throw new RuntimeException("Could not set '" + name + "'='" + value + "' cookie: " + e, e);
        }
    }

}
