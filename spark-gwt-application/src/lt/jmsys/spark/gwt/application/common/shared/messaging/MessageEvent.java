package lt.jmsys.spark.gwt.application.common.shared.messaging;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

public class MessageEvent<E extends Message> extends GwtEvent<MessageEventHandler<E>> {

    private static Type<MessageEventHandler<?>> TYPE;

    private static Map<String, Type<MessageEventHandler<?>>> types = new HashMap<String, GwtEvent.Type<MessageEventHandler<?>>>();

    private E message;
    private String associatedClassAndCategory;

    private MessageEvent(E message, Class<E> associatedClass, String category) {
        this.message = message;
        this.associatedClassAndCategory = associatedClass.getName() + "." + category;
    }

    public static <E> Type<MessageEventHandler<?>> getType(Class<E> associatedClass, String category) {
        return getType(associatedClass.getName() + "." + category);
    }

    private static <E> Type<MessageEventHandler<?>> getType(String associatedClassAndCategory) {

        Type<MessageEventHandler<?>> t = types.get(associatedClassAndCategory);
        if (null == t) {
            t = new Type<MessageEventHandler<?>>();
            types.put(associatedClassAndCategory, t);
        }

        if (TYPE == null) {
            TYPE = new Type<MessageEventHandler<?>>();
        }
        return t;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<MessageEventHandler<E>> getAssociatedType() {
        return (Type) getType(associatedClassAndCategory);
    }

    public static <T extends Message> void fire(HasMessageEventHandlers<T> source, T message) {
        if (TYPE != null) {
            MessageEvent<T> event = new MessageEvent<T>(message, source.getAssociatedClass(), source.getCategory());
            source.fireEvent(event);
        }
    }

    @Override
    protected void dispatch(MessageEventHandler<E> handler) {
        handler.onMessage(this);
    }

    public E getMessage() {
        return message;
    }

}
