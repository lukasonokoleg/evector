package lt.jmsys.spark.gwt.application.common.client.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.helper.UuidHelper;
import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEventHandler;
import lt.jmsys.spark.gwt.application.common.shared.websocket.NotificationSubscription;
import lt.jmsys.spark.gwt.application.common.shared.websocket.WebSocketCommand;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window.Location;

public class NotificationService {

    private int TIMEOUT_RETRY_AFTER_FAILURE = 120000;
    private int TIMEOUT_REOPEN = 5000;

    private static NotificationService instance;

    private List<NotificationSubscription> queue = new ArrayList<NotificationSubscription>();
    private List<String> unsentMessageQueue = new ArrayList<String>();

    private boolean disabled = false;
    private boolean started = false;
    private boolean active = false;
    private ClientWebSocket socket;
    private int scheduleId;
    private Set<String> topics = new HashSet<String>();

    private String clientId = UuidHelper.uuid();

    private Map<String, TopicEventHandler<String>> handlers = new HashMap<String, TopicEventHandler<String>>();

    private long openTime;

    private WebSocketHandler handler = new WebSocketHandler() {

        @Override
        public void onMessage(final String message) {
            if (WebSocketCommand.PING.equals(message)) {
                socket.send(WebSocketCommand.PONG);
            } else if (message.startsWith(WebSocketCommand.TOPIC)) {
                String[] m = message.substring(WebSocketCommand.TOPIC.length()).split(":");
                String topic = m[0];
                String msg = m.length > 1 ? m[1] : null;

                TopicEventHandler<String> h = handlers.get(topic);
                if (null != h) {
                    h.onMessage(new TopicEvent<String>(topic, msg));
                }
            }
        }

        @Override
        public void onClose() {
            active = false;
            openTime = 0;
            int timeout = (openTime != 0 && System.currentTimeMillis() - openTime > 5000) ? TIMEOUT_REOPEN : TIMEOUT_RETRY_AFTER_FAILURE;
            Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

                @Override
                public boolean execute() {
                    if (started && !active) {
                        openSocket();
                    }
                    return false;
                }
            }, timeout);
        }

        @Override
        public void onOpen() {
            active = true;
            openTime = System.currentTimeMillis();
            for (String topic : topics) {
                queue.add(new NotificationSubscription("S", topic));
            }
            processQueue();
        }

    };

    private NotificationService() {
    }

    public static NotificationService getInstance() {
        if (null == instance) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void start() {
        try {
            if (!disabled && !started && ClientWebSocket.isSupported()) {
                openSocket();
                started = true;
            }
        } catch (Exception e) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, "Failed to start WebSocket notification service", e);
        }
    }

    protected void openSocket() {
        String url = null;
        try {
            String id = SessionHolder.getSessionInfo().getNotificationId();
            Double userId = SessionHolder.getSessionInfo().getUserId();

            url = (Location.getProtocol().toLowerCase().equals("http:") ? "ws" : "wss") + "://" + Location.getHostName() + ":" + Location.getPort();
            url = url + "/alcs-socket/notifications?id=" + id + "&uid=" + userId + "&cid=" + clientId;
            socket = new ClientWebSocket(url, handler);
            final int _id = scheduleId++;
            Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

                @Override
                public boolean execute() {
                    if (started && !active && _id == scheduleId) {
                        openSocket();
                    }
                    return false;
                }
            }, TIMEOUT_RETRY_AFTER_FAILURE);

        } catch (Exception e) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, "Failed to create WebSocket for notification service, url = " + url, e);
        }
    }

    public void subscribe(String topic, TopicEventHandler<String> handler) {
        queue.add(new NotificationSubscription("S", topic));
        handlers.put(topic, handler);
        processQueue();
    }

    public void unsubscribe(String topic) {
        queue.add(new NotificationSubscription("U", topic));
        handlers.remove(topic);
        processQueue();
    }

    public void logPerformanceStats(String statsGroup, String statsName, long duration) {
        unsentMessageQueue.add(WebSocketCommand.STATS + statsGroup + "," + statsName + "," + duration);
        processQueue();
    }

    protected void processQueue() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                if (active) {

                    while (queue.size() != 0) {
                        NotificationSubscription s = queue.get(0);
                        if ("S".equals(s.getAction())) {
                            topics.add(s.getTopic());
                        } else {
                            topics.remove(s.getTopic());
                        }
                        String command = s.toStringValue();
                        if (!unsentMessageQueue.contains(command)) {
                            unsentMessageQueue.add(command);
                        }
                        queue.remove(0);
                    }

                    while (unsentMessageQueue.size() != 0) {
                        socket.send(unsentMessageQueue.get(0));
                        unsentMessageQueue.remove(0);
                    }
                }
            }
        });
    }

    public String getClientId() {
        return clientId;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        if (disabled && started) {
            throw new IllegalStateException("NotificationService can be disabled only before starting it");
        }
    }
}
