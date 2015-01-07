package lt.jmsys.spark.gwt.application.client.common.presenter;

import lt.jmsys.spark.gwt.application.client.helper.State;
import lt.jmsys.spark.gwt.application.client.helper.StateManager;

public class ValuePlace<E> extends PlaceWithId {

    private E value;

    public ValuePlace(Number id, E value) {
        this(null, null, id, value);
    }

    public ValuePlace(Module module, String menuCode, Number id, E value) {
        super(module, menuCode, id);
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public static abstract class Tokenizer<E, P extends ValuePlace<E>> extends PlaceWithId.Tokenizer<P> {

        private static StateManager<Object> stateManager = new StateManager<Object>();

        @Override
        public String getToken(P place) {
            String token = super.getToken(place);
            stateManager.updateState(place.getStateId(), null, place.getValue());
            return token;
        }

        @SuppressWarnings("unchecked")
        @Override
        public P getPlace(String token) {
            P place = super.getPlace(token);
            State<Object> state = stateManager.getState(place.getStateId());
            if (null != state) {
                place.setValue((E) state.getItem());
            }
            return place;
        }

    }

}
