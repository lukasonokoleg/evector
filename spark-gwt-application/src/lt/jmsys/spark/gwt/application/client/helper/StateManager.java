package lt.jmsys.spark.gwt.application.client.helper;

import java.util.ArrayList;
import java.util.List;

public class StateManager<E> {

    private static final int MAX_STATES = 10;

    private List<State<E>> states = new ArrayList<State<E>>();

    public State<E> newState(String stateId, String itemId, E item) {
        State<E> state = new State<E>();
        state.setItem(item);
        state.setItemId(itemId);
        state.setStateId(stateId);

        states.add(state);
        if (states.size() > MAX_STATES) {
            states.remove(0);
        }
        return state;
    }

    public State<E> updateState(String stateId, String itemId, E item) {
        State<E> state = getState(stateId, true);
        if (null != state) {
            state.setItem(item);
            state.setItemId(itemId);
        } else {
            state = newState(stateId, itemId, item);
        }
        return state;
    }

    public State<E> getState(String stateId) {
        return getState(stateId, false);
    }

    public State<E> getState(String stateId, boolean up) {
        State<E> s = null;
        for (State<E> state : states) {
            if (state.getStateId().equals(stateId)) {
                s = state;
                break;
            }
        }
        if (s != null && up) {
            states.remove(s);
            states.add(s);
        }
        return s;
    }

    public State<E> getStateByItemId(String itemId, boolean up) {
        State<E> s = null;
        for (int i = states.size() - 1; i >= 0; i--) {
            State<E> state = states.get(i);
            if (state.getItemId().equals(itemId)) {
                s = state;
                break;
            }
        }
        if (s != null && up) {
            states.remove(s);
            states.add(s);
        }
        return s;
    }

}
