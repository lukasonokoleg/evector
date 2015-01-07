package lt.jmsys.spark.gwt.application.common.shared.event;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

public class TopicEvent<E> extends GwtEvent<TopicEventHandler<E>> {

    // private static final Type<TopicEventHandler<?>> TYPE = new Type<TopicEventHandler<?>>();

    private static Map<String, TopicEventType> types = new HashMap<String, TopicEventType>();

    private String topic;
    private E message;

    public TopicEvent(String topic, E message) {
        this.topic = topic;
        this.message = message;
    }

    public static <E> TopicEventType getType(String topic) {

        TopicEventType t = types.get(topic);
        if (null == t) {
            t = new TopicEventType(topic);
            types.put(topic, t);
        }
        return t;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<TopicEventHandler<E>> getAssociatedType() {
        return (Type) getType(topic);
    }

    public static <T> void fire(HasTopicEventHandlers<T> source, String topic, T message) {
        TopicEvent<T> event = new TopicEvent<T>(topic, message);
        source.fireEvent(event);
    }

    @Override
    protected void dispatch(TopicEventHandler<E> handler) {
        handler.onMessage(this);
    }

    public String getTopic() {
        return topic;
    }

    public E getMessage() {
        return message;
    }

    public static class TopicEventType extends Type<TopicEventHandler<?>> {

        private String topic;

        public TopicEventType(String topic) {
            this.topic = topic;
        }

        public String getTopic() {
            return topic;
        }
    }

}
