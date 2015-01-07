package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.callback.FailureHandler;
import lt.jmsys.spark.gwt.client.messages.MessagesByCode;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @param <T> Form bean type
 * @param <P> Form parameters type
 */
public interface FormPresenter<T, P> {

    P getParameters(Place place);

    P getParameters(T value);

    void loadByParameters(P parameters, EventBus eventBus, AsyncCallback<T> callback);

    void loadByValue(T value, EventBus eventBus, AsyncCallback<T> callback);
    
    void reload();

    void reload(AsyncCallback<T> callback);

    void cancelLoad();
    
    void onStart();

    void onClose();

    String mayClose();

    FormDisplay<T> getView();

    boolean validate();

    void setCallback(FormPresenterCallback callback);

    FailureHandler customFailureHandler();

    MessagesByCode[] customMessages();

    EventBus getEventBus();
    
    /**
     * FormBehaviour
     * addBehaviour()
     */

}
