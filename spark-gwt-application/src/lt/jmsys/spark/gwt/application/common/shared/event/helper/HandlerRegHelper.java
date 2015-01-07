package lt.jmsys.spark.gwt.application.common.shared.event.helper;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;

public class HandlerRegHelper {

    public final static void clearRegistrations(List<HandlerRegistration> vHandlers) {
        if (vHandlers == null) {
            throw new IllegalArgumentException("Input variable handlers is NULL!");
        }
        for (HandlerRegistration handlerReg : vHandlers) {
            if (handlerReg != null) {
                handlerReg.removeHandler();
            }
        }
        vHandlers.clear();
    }

    public final static void addHandlerRegistration(List<HandlerRegistration> container, HandlerRegistration handler) {
        if (container == null) {
            throw new IllegalArgumentException("Input variable container is NULL!");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Input variable handler is NULL!");
        }
        if (!container.contains(handler)) {
            container.add(handler);
        }
    }
}
