package lt.jmsys.spark.gwt.application.common.client.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.common.client.messaging.MessageDeliveryManager;
import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.client.websocket.NotificationService;
import lt.jmsys.spark.gwt.client.callback.LoginException;
import lt.jmsys.spark.gwt.client.callback.LoginException.Code;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.client.rpc.impl.Serializer;

import eu.itreegroup.spark.application.shared.Version;

public class CommonServiceProxy extends RemoteServiceProxy {

    private static final Logger logger = Logger.getLogger(CommonServiceProxy.class.getName());

    private static SessionMonitor sessionMonitor = SessionMonitor.getInstance();
    private static MessageDeliveryManager messageDeliveryManager;

    public CommonServiceProxy(String moduleBaseURL, String remoteServiceRelativePath, String serializationPolicyName, Serializer serializer) {
        super(moduleBaseURL, remoteServiceRelativePath, serializationPolicyName, serializer);
    }

    @Override
    protected <T> Request doInvoke(ResponseReader responseReader, String methodName, RpcStatsContext statsContext, String requestData, AsyncCallback<T> callback) {
        try {
            return doInvokeSuper(responseReader, methodName, statsContext, requestData, callback);
        } finally {
            if (LogConfiguration.loggingIsEnabled() && logger.isLoggable(Level.INFO)) {
                logger.info("invoked service " + methodName);
            }
        }
    }

    protected <T> Request doInvokeSuper(ResponseReader responseReader, String methodName, RpcStatsContext statsContext, String requestData, AsyncCallback<T> callback) {

        RequestBuilder rb = doPrepareRequestBuilder(responseReader, methodName, statsContext, requestData, callback);

        try {
            return rb.send();
        } catch (RequestException ex) {
            InvocationException iex = new InvocationException("Unable to initiate the asynchronous service invocation (" + methodName + ") -- check the network connection", ex);
            callback.onFailure(iex);
        } finally {
            if (statsContext.isStatsAvailable()) {
                statsContext.stats(statsContext.bytesStat(methodName, requestData.length(), "requestSent"));
            }
        }
        return null;
    }

    protected <T> RequestCallback doCreateRequestCallback(ResponseReader responseReader, final String methodName, RpcStatsContext statsContext, AsyncCallback<T> callback) {

        return new RequestCallbackAdapter<T>(this, methodName, statsContext, callback, getRpcTokenExceptionHandler(), responseReader) {

            private long start = System.currentTimeMillis();

            @Override
            public void onResponseReceived(Request request, Response response) {
                try {
                    final int responseCode = response.getStatusCode();
                    if (responseCode == Response.SC_UNAUTHORIZED) {
                        onError(request, new LoginException(Code.NOAUTH));
                    } else if (responseCode == Response.SC_FORBIDDEN && "1".equals(response.getHeader("X-ALCS-Refresh"))) {
                        Location.assign(GWT.getHostPageBaseURL());
                    } else {
                        String expires = response.getHeader("X-SPARK-SessionExpires");
                        String messages = response.getHeader("X-ALCS-Messages");
                        if (null != expires && !expires.isEmpty()) {
                            sessionMonitor.setExpiresAfter(Long.parseLong(expires));
                        }
                        if (null != messages && !messages.isEmpty() && null != messageDeliveryManager) {
                            messageDeliveryManager.deliverMessages();
                        }
                        super.onResponseReceived(request, response);
                    }
                    NotificationService.getInstance().logPerformanceStats("service", methodName, getAge());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error on service response", e);
                    throw new RuntimeException("Error on service response", e);
                }
            }

            public long getAge() {
                return System.currentTimeMillis() - start;
            }
        };
    }

    @Override
    protected <T> RequestBuilder doPrepareRequestBuilder(ResponseReader responseReader, String methodName, RpcStatsContext statsContext, String requestData,
            AsyncCallback<T> callback) {
        RequestBuilder builder = super.doPrepareRequestBuilder(responseReader, methodName, statsContext, requestData, callback);

        if (null != SessionHolder.getSessionInfo() && null != SessionHolder.getSessionInfo().getCompanyId()) {
            builder.setHeader("X-ALCS-OrgId", Integer.toString(SessionHolder.getSessionInfo().getCompanyId().intValue()));
        }

        builder.setHeader("X-ALCS-cid", NotificationService.getInstance().getClientId());
        builder.setHeader("X-ALCS-Version", Version.getVersion());

        return builder;
    }

    public static void startSessionMonitor(SessionMonitorCallback callback) {
        sessionMonitor.start(callback);
    }
    
    public static long getSessionExpiresAfter(){
        return sessionMonitor.expiresAfter;
    }

    public static void setMessageDeliveryManager(MessageDeliveryManager messageDeliveryManager) {
        CommonServiceProxy.messageDeliveryManager = messageDeliveryManager;
    }

    public interface SessionMonitorCallback {

        void beforeExpire();

        void afterExpire();
    }

    private static class SessionMonitor implements RepeatingCommand {

        private static int TIME_RESERVE = 10000;// ms

        private static SessionMonitor instance;
        private long lastKnown;
        private long expiresAfter = Long.MAX_VALUE;
        private SessionMonitorCallback callback;

        private boolean started;

        private SessionMonitor() {

        }

        public static SessionMonitor getInstance() {
            if (null == instance) {
                instance = new SessionMonitor();
            }
            return instance;
        }

        public void start(SessionMonitorCallback callback) {
            /*            if (started) {
                            throw new IllegalStateException("SessionMonitor cannot be started more than once");
                        }*/
            this.callback = callback;
            Scheduler.get().scheduleFixedDelay(this, TIME_RESERVE);
            started = true;
        }

        @Override
        public boolean execute() {
            int scheduleAfter = -1;
            if (getExpiresAfter() < TIME_RESERVE) {
                callback.afterExpire();
            } else if (getExpiresAfter() < TIME_RESERVE * 2) {
                callback.beforeExpire();
                scheduleAfter = TIME_RESERVE;
            } else {
                scheduleAfter = ((int) getExpiresAfter() - TIME_RESERVE * 2 + TIME_RESERVE / 4);
            }

            if (scheduleAfter > 0) {
                Scheduler.get().scheduleFixedDelay(this, scheduleAfter);
                started = true;
            } else {
                started = false;
            }
            return false;
        }

        public void setExpiresAfter(long expiresAfter) {
            /*            this.expiresAfter = expiresAfter;*/
            this.expiresAfter = Long.MAX_VALUE;
            lastKnown = System.currentTimeMillis();
        }

        public long getExpiresAfter() {
            return lastKnown + expiresAfter - System.currentTimeMillis();
        }
    }

    public static void changeServiceModule(CommonServiceProxy commonServiceProxy, HasApplicationModuleName currentName, HasApplicationModuleName newName) {
        if (commonServiceProxy != null && currentName != null && newName != null) {
            String currentServiceEntryPoint = commonServiceProxy.getServiceEntryPoint();
            String newServiceEntryPoint = null;
            if (currentServiceEntryPoint.contains(currentName.getName())) {
                newServiceEntryPoint = currentServiceEntryPoint.replace(currentName.getName(), newName.getName());
            }
            if (newServiceEntryPoint != null) {
                commonServiceProxy.setServiceEntryPoint(newServiceEntryPoint);
            }
        }
    }

    public static void changeServiceName(CommonServiceProxy commonServiceProxy, String currentName, String newName) {
        if (commonServiceProxy != null && currentName != null && newName != null) {
            String currentServiceEntryPoint = commonServiceProxy.getServiceEntryPoint();
            String newServiceEntryPoint = null;
            if (currentServiceEntryPoint.contains(currentName)) {
                newServiceEntryPoint = currentServiceEntryPoint.replace(currentName, newName);
            }
            if (newServiceEntryPoint != null) {
                commonServiceProxy.setServiceEntryPoint(newServiceEntryPoint);
            }

        }

    }

}
