package lt.jmsys.spark.gwt.application.common.shared.event;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

public class EntityEvent<E> extends GwtEvent<EntityEventHandler<E>> {

    private static Type<EntityEventHandler<?>> TYPE;

    private static Map<Class<?>, Type<EntityEventHandler<?>>> types = new HashMap<Class<?>, GwtEvent.Type<EntityEventHandler<?>>>();

    public static enum Action {
        CREATE, UPDATE, DELETE, VIEW, ACTION_REQUEST;
    }

    private Action action;
    private E entity;
    private Class<E> associatedClass;

    private EntityEvent(Action action, E entity, Class<E> associatedClass) {
        this.action = action;
        this.entity = entity;
        this.associatedClass = associatedClass;
    }

    public static <E> Type<EntityEventHandler<?>> getType(Class<E> associatedClass) {

        Type<EntityEventHandler<?>> t = types.get(associatedClass);
        if (null == t) {
            t = new Type<EntityEventHandler<?>>();
            types.put(associatedClass, t);
        }

        if (TYPE == null) {
            TYPE = new Type<EntityEventHandler<?>>();
        }
        return t;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<EntityEventHandler<E>> getAssociatedType() {
        return (Type) getType(associatedClass);
    }

    public static <T> void fireCreated(HasEntityEventHandlers<T> source, T value) {
        fire(source, Action.CREATE, value);
    }

    public static <T> void fireUpdated(HasEntityEventHandlers<T> source, T value) {
        fire(source, Action.UPDATE, value);
    }

    public static <T> void fireDeleted(HasEntityEventHandlers<T> source, T value) {
        fire(source, Action.DELETE, value);
    }

    public static <T> void fireViewed(HasEntityEventHandlers<T> source, T value) {
        fire(source, Action.VIEW, value);
    }

    public static <T> void fireOnActionRequest(HasEntityEventHandlers<T> source, T value) {
        fire(source, Action.ACTION_REQUEST, value);
    }

    private static <T> void fire(HasEntityEventHandlers<T> source, Action action, T value) {
        if (TYPE != null) {
            EntityEvent<T> event = new EntityEvent<T>(action, value, source.getAssociatedClass());
            source.fireEvent(event);
        }
    }

    @Override
    protected void dispatch(EntityEventHandler<E> handler) {
        switch (action) {
            case CREATE:
                handler.afterCreate(this);
                break;
            case UPDATE:
                handler.afterUpdate(this);
                break;
            case DELETE:
                handler.afterDelete(this);
                break;
            case VIEW:
                handler.afterView(this);
                break;
            case ACTION_REQUEST:
                handler.onActionRequest(this);
                break;
        }
    }

    public Action getAction() {
        return action;
    }

    public E getEntity() {
        return entity;
    }

    public static class Handler<E> implements EntityEventHandler<E> {

        @Override
        public void afterCreate(EntityEvent<E> event) {
        }

        @Override
        public void afterDelete(EntityEvent<E> event) {
        }

        @Override
        public void afterUpdate(EntityEvent<E> event) {
        }

        @Override
        public void afterView(EntityEvent<E> event) {
        }

        @Override
        public void onActionRequest(EntityEvent<E> event) {
        }
    }

}
