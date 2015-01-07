package univ.evector.gwt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.client.common.service.SessionService;
import lt.jmsys.spark.gwt.application.common.server.messaging.MessageQueue;
import lt.jmsys.spark.gwt.application.common.server.messaging.MessageQueueManager;
import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.application.server.quartz.MessageCountChecker.MessageCountEnvelope;
import lt.jmsys.spark.gwt.application.server.session.SessionManager;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import lt.jmsys.spark.gwt.client.callback.LoginException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import univ.evector.beans.User;
import univ.evector.beans.UserSession;
import univ.evector.db.dao.ClassifierDao;
import univ.evector.gwt.client.session.EvectorPreloadedSessionData;
import univ.evector.internal.service.UserService;
import univ.evector.security.SelfSecured;
import eu.itreegroup.spark.application.bean.Classifier;
import eu.itreegroup.spark.application.bean.Setting;
import eu.itreegroup.spark.application.service.SettingsService;

@Service("sessionService")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class SessionServiceImpl implements SessionService {

    private static final String SESSION_INFO_KEY = SessionServiceImpl.class.getName() + ".session.info";

    @Autowired
    UserService userService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private ClassifierDao classifierDao;

    private static final Logger log = Logger.getLogger(SessionServiceImpl.class);

    private SessionInfo getSessionInfo() {
        SessionInfo sessionInfo = null;
        HttpSession session = request.getSession(false);
        try {
            if (null != session) {
                sessionInfo = (SessionInfo) session.getAttribute(SESSION_INFO_KEY);
                if (null != sessionInfo) {
                    return sessionInfo;
                }
                if (userService.hasCurrentSession()) {
                    UserSession userSession = userService.currentSession();
                    sessionInfo = createSessionInfo(userSession, null, false);
                    session.setAttribute(SESSION_INFO_KEY, sessionInfo);
                    SessionManager.getInstance().registerSession(session.getId(), sessionInfo.getNotificationId(), sessionInfo.getUserId(), sessionInfo.getCompanyId(),
                            userSession.getSes_id().doubleValue());
                }
            }
        } catch (Throwable e) {
            log.error("Cannot get session info", e);
        }
        return sessionInfo;
    }

    private static SessionInfo createSessionInfo(UserSession userSession, String[] roles, boolean keepFresh) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setKeepFresh(keepFresh);
        sessionInfo.setTimeout(userSession.getTimeout() != null ? userSession.getTimeout().doubleValue() : null);
        sessionInfo.setRoles(roles);
        User user = userSession.getUser();
        if (user != null) {
            sessionInfo.setUserId(user.getUsr_id() != null ? user.getUsr_id().doubleValue() : null);
            sessionInfo.setUserName(user.getUsr_email());
            sessionInfo.setFirstName(user.getUsr_first_name());
            sessionInfo.setLastName(user.getUsr_last_name());
        }
        sessionInfo.setCompanyId(null);
        sessionInfo.setCompanyName(null);
        sessionInfo.setLanguage(null);
        sessionInfo.setDbSessionid(userSession.getSes_id().doubleValue());
        String id = SessionManager.getInstance().getNidByDbSessionId(userSession.getSes_id().doubleValue());
        if (null == id) {
            id = UUID.randomUUID().toString();
        }
        sessionInfo.setNotificationId(id);
        return sessionInfo;
    }

    @Override
    @SelfSecured
    public PreloadedSessionData preloadSessionData(boolean doSignOut) throws SparkBusinessException {
        /*        if (doSignOut) {
                    LogoutServlet.logout(getServiceContext(), false);
                }*/
        SessionInfo session = getSessionInfo();
        if (null == session) {
            throw new LoginException(LoginException.Code.NOAUTH);
        }
        EvectorPreloadedSessionData data = new EvectorPreloadedSessionData();
        data.setSessionInfo(session);
        UserSession userSession = userService.currentSession();
        data.setUserSession(userSession);
        try {
            HashMap<String, ArrayList<Classifier>> classifiers = classifierDao.findClassifiers();

            data.setClassifierMap(classifiers);
            data.setUserMenu(MockMenuList.getMenu());
            data.setClientSettings(clientSettings());

            onFirstLoad();

            /*        } catch (SparkBusinessException e) {
                        log.error("Service error", e);
                        throw e;*/
        } catch (Throwable e) {
            log.error("Service error", e);
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    @SelfSecured
    public void touchSession() {
        try {
            UserSession session = userService.currentSession();
            userService.touchSession(session.getKey());
        } catch (LoginException e) {
            log.error("Session touch failded, LoginException: " + e.getCode());
        } catch (Throwable e) {
            log.error("Session touch failded", e);
        }
    }

    @Override
    @SelfSecured
    public ArrayList<Message> receiveMessages() {
        /*        SessionInfo sessionInfo = getSessionInfo();
                if (null != sessionInfo) {
                    ArrayList<Message> messages = new ArrayList<Message>();
                    MessageQueue queue = MessageQueueManager.getInstance().getQueue(sessionInfo.getUser_id(), sessionInfo.getNotificationId());
                    Message message;
                    Set<String> tags = new HashSet<String>();
                    while ((message = queue.poll()) != null) {
                        if (message instanceof EnvelopedMessage) {
                            try {
                                if (!tags.contains(message.getMessageTag())) {
                                    if (null != message.getMessageTag()) {
                                        tags.add(message.getMessageTag());
                                    }
                                    message = ((EnvelopedMessage) message).open(this);
                                    if (null != message && message.getMessageTag() != null) {
                                        tags.remove(message.getMessageTag());
                                    }
                                } else {
                                    message = null;
                                }
                            } catch (SparkBusinessException e) {
                                log.error("Cannot open envelope: " + message + ", userId : " + sessionInfo.getUser_id(), e);
                                message = null;
                            }
                        }
                        if (message != null && !tags.contains(message.getMessageTag())) {// envelope.open() may return null;
                            if (null != message.getMessageTag()) {
                                tags.add(message.getMessageTag());
                            }
                            messages.add(message);
                        }
                    }
                    return messages;
                } else {
                    return null;
                }*/
        return null;
    }

    private void onFirstLoad() {
        SessionInfo sessionInfo = getSessionInfo();
        MessageQueue queue = MessageQueueManager.getInstance().getQueue(sessionInfo.getUserId(), sessionInfo.getNotificationId());
        queue.add(new MessageCountEnvelope());
    }

    private HashMap<Setting, String> clientSettings() {
        HashMap<Setting, String> settings = new HashMap<>();

        return settings;
    }

}
