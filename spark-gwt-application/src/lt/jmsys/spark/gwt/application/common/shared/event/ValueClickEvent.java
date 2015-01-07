package lt.jmsys.spark.gwt.application.common.shared.event;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

public class ValueClickEvent<E> extends GwtEvent<ValueClickHandler<E>> {

    private static Type<ValueClickHandler<?>> TYPE;

    private static Map<Class<?>, Type<ValueClickHandler<?>>> types = new HashMap<Class<?>, GwtEvent.Type<ValueClickHandler<?>>>();

    private E value;
    private Class<E> associatedClass;

    public ValueClickEvent(E value, Class<E> associatedClass) {
        this.value = value;
        this.associatedClass = associatedClass;
    }

    public static <E> Type<ValueClickHandler<?>> getType(Class<E> associatedClass) {

        Type<ValueClickHandler<?>> t = types.get(associatedClass);
        if (null == t) {
            t = new Type<ValueClickHandler<?>>();
            types.put(associatedClass, t);
        }

        if (TYPE == null) {
            TYPE = new Type<ValueClickHandler<?>>();
        }
        return t;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<ValueClickHandler<E>> getAssociatedType() {
        return (Type) getType(associatedClass);
    }

    public static <T> void fire(HasValueClickHandlers<T> source, T value) {
        if (TYPE != null) {
            ValueClickEvent<T> event = new ValueClickEvent<T>(value, source.getAssociatedClass());
            source.fireEvent(event);
        }
    }

    @Override
    protected void dispatch(ValueClickHandler<E> handler) {
        handler.onClick(this);
    }

    public E getValue() {
        return value;
    }

}
