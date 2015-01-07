package lt.jmsys.spark.gwt.application.common.server.messaging;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lt.jmsys.spark.gwt.application.common.shared.messaging.Message;

import org.apache.log4j.Logger;

import com.google.gwt.event.shared.HandlerRegistration;

public class MessageQueueManager {

    private static final Logger log = Logger.getLogger(MessageQueueManager.class);
    private static MessageQueueManager instance;

    private Map<String, MessageQueueImpl> queues = new HashMap<String, MessageQueueImpl>();

    private MessageQueueManager() {

    }

    public static synchronized MessageQueueManager getInstance() {
        if (null == instance) {
            instance = new MessageQueueManager();
        }
        return instance;
    }

    public MessageQueue getQueue(Double userId, String sessionId) {
        String key = getQueueKey(userId, sessionId);
        synchronized (queues) {
            MessageQueue queue = queues.get(key);
            if (null != queue) {
                return queue;
            } else {
                return getQueueUnsynchronized(userId, sessionId);
            }
        }
    }

    private MessageQueueImpl getQueueUnsynchronized(Double userId, String sessionId) {
        String key = getQueueKey(userId, sessionId);
        MessageQueueImpl queue = queues.get(key);
        if (null == queue) {
            queue = new MessageQueueImpl(sessionId == null);
            queues.put(key, queue);
            log.debug("Create queue: " + key + "; queue map size: " + queues.size());
            if (null != sessionId) {
                MessageQueueImpl q = getQueueUnsynchronized(userId, null);
                q.attach(queue);
            } else if (null != userId) {
                MessageQueueImpl q = getQueueUnsynchronized(null, null);
                q.attach(queue);
            }
        }
        return queue;
    }

    public void removeQueue(Double userId, String sessionId) {
        if (null == userId || null == sessionId) {
            throw new IllegalArgumentException("Only session queue can be removed, both sessionId and userId are required");
        }
        String key = getQueueKey(userId, sessionId);
        log.debug("Remove queue: " + key);
        synchronized (queues) {
            MessageQueueImpl queue = queues.get(key);
            if (null != queue) {
                queues.remove(key);
                String userKey = getQueueKey(userId, null);
                MessageQueueImpl userQueue = queues.get(userKey);
                if (userQueue.detach(queue) == 0) {
                    log.debug("Remove user queue: " + userKey);
                    queues.remove(userKey);
                    String globalKey = getQueueKey(null, null);
                    MessageQueueImpl globalQueue = queues.get(globalKey);
                    if (globalQueue.detach(userQueue) == 0) {
                        queues.remove(globalKey);
                        log.debug("Removed global queue, queue map should be empty now: " + queues);
                    }

                }
            }
            log.debug("Queue map size after remove: " + queues.size() + " (key = " + key + ")");
        }
    }

    private static String getQueueKey(Double userId, String sessionId) {
        if (null == userId && sessionId != null) {
            throw new IllegalArgumentException("userId is required when sessionId is specified");
        }
        return (userId != null ? userId.intValue() : "") + "." + (sessionId != null ? sessionId : "");
    }

    private class MessageQueueImpl implements MessageQueue {

        private static final int MAX_QUEUE_SIZE = 20;

        private ArrayDeque<Message> queue;
        private List<MessageQueueImpl> attachedQueues;
        private Set<QueueListener> listeners;

        protected MessageQueueImpl(boolean writeOnly) {
            if (!writeOnly) {
                queue = new ArrayDeque<Message>();
            }
        }

        @Override
        public Message poll() {
            if (null != queue) {
                synchronized (queue) {
                    return queue.poll();
                }
            } else {
                return null;
            }
        }

        @Override
        public int size() {
            if (null != queue) {
                synchronized (queue) {
                    return queue.size();
                }
            } else {
                return 0;
            }
        }

        @Override
        public void add(Message message) {
            if (null != queue) {
                synchronized (queue) {
                    queue.add(message);
                    if (queue.size() > MAX_QUEUE_SIZE) {
                        log.warn("Queue size exceeds max queue size limit " + MAX_QUEUE_SIZE + ", removing oldest message: " + queue.poll());
                    }
                }
            }
            if (null != attachedQueues) {
                synchronized (attachedQueues) {
                    for (MessageQueueImpl q : attachedQueues) {
                        q.add(message);
                    }
                }
            }
            if (null != listeners) {
                for(QueueListener l : listeners){
                    l.messageArrived();
                }
            }
        }

        protected void attach(MessageQueueImpl q) {
            if (null == attachedQueues) {
                synchronized (this) {
                    if (null == attachedQueues) {
                        attachedQueues = new ArrayList<MessageQueueImpl>();
                    }
                }
            }
            synchronized (attachedQueues) {
                attachedQueues.add(q);
            }
        }

        protected int detach(MessageQueueImpl q) {
            if (null != attachedQueues) {
                synchronized (attachedQueues) {
                    attachedQueues.remove(q);
                    return attachedQueues.size();
                }
            } else {
                return 0;
            }
        }

        @Override
        public HandlerRegistration addQueueListener(final QueueListener listener) {
            if (null == listeners) {
                listeners = new HashSet<QueueListener>();
            }
            listeners.add(listener);
            return new HandlerRegistration() {

                @Override
                public void removeHandler() {
                    listeners.remove(listener);
                }
            };
        }
    }
}
