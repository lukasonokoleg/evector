package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.client.common.v2.privileges.HasPrivilegedValue;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.callback.FailureHandler;
import lt.jmsys.spark.gwt.client.messages.MessagesByCode;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public abstract class BaseFormPresenter<T, P> implements FormPresenter<T, P> {

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);

    private P parameters;
    private T value;
    private EventBus eventBus;
    private FormPresenterCallback callback;
    private FormDisplay<T> view;
    private boolean started;

    // TODO: private EntityEventSource

    public BaseFormPresenter(FormDisplay<T> view) {
        this.view = view;
    }

    public P getParameters() {
        return parameters;
    }

    public T getValue() {
        return value;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    protected Object toProtectedObject(T value) {
        return value;
    }

    @Override
    final public void loadByValue(T value, EventBus eventBus, AsyncCallback<T> callback) {
        getView().setNotModified();
        P p = getParameters(value);
        if (null == p) {
            throw new IllegalStateException("Cannot load form by value, getParameters(value) is not implemented");
        }
        loadPrivileges(p, value, eventBus, callback);
    }

    @Override
    final public void loadByParameters(final P parameters, final EventBus eventBus, final AsyncCallback<T> callback) {
        getView().setNotModified();
        final AsyncCallback<T> valueCallback = new AsyncCallback<T>() {

            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(final T value) {
                loadPrivileges(parameters, value, eventBus, callback);
            }

        };

        findValue(parameters, valueCallback);
    }

    final protected void loadPrivileges(final P parameters, final T value, final EventBus eventBus, final AsyncCallback<T> callback) {
        this.eventBus = eventBus;
        this.parameters = parameters;
        this.value = value;
        final AsyncCallback<FormPrivileges> privilegesCallback = new AsyncCallback<FormPrivileges>() {

            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            @Override
            public void onSuccess(FormPrivileges privileges) {
                load(parameters, value, privileges, eventBus, callback);
            }
        };

        FormPrivileges.getPrivileges(toProtectedObject(value), privilegesCallback);
    }

    protected void load(P parameters, T value, FormPrivileges privileges, EventBus eventBus, AsyncCallback<T> callback) {
        if (!started){// event handleriai prikabinami tik startuojant, jų nebereikia papildomai kabinti kai vyksta formos atnaujinimas (reload())
            attachHandlers(eventBus);
        }
        getView().setPrivileges(privileges);
        // FIXME: ALCS_V2 išimti privilegijas iš setValue();
        if (null == callback) {
            callback = new AsyncCallback<T>() {

                @Override
                public void onFailure(Throwable caught) {

                }

                public void onSuccess(T result) {
                };
            };
        }
        setValue(value, parameters, privileges, callback);
    }

    protected boolean isFindPrivilegedSupported() {
        return false;
    }

    protected void findPrivilegedValue(P parameters, AsyncCallback<HasPrivilegedValue<T>> callback) {
        throw new IllegalStateException("findPrivilegedValue is not supported by this presenter");
    }

    @Override
    public void reload() {
        reload(null);
    }

    @Override
    public void reload(AsyncCallback<T> callback) {
        if (null == callback) {
            callback = new AsyncCallback<T>() {

                @Override
                public void onFailure(Throwable caught) {
                }

                public void onSuccess(T result) {
                }
            };
        }
        loadByParameters(getParameters(), getEventBus(), callback);
    }

    @Override
    public void cancelLoad() {

    }

    @Override
    public final void onStart() {
        started = true;
        onFormStart();
    }
    
    /**
     * Override this method in case you need to do something after form was opened
     */
    protected void onFormStart(){
        
    }

    @Override
    public final void onClose() {
        started = false;
        onFormClose();
    }
    
    /**
     * Override this method in case you need something special after form was closed
     */    
    protected void onFormClose(){
        
    }

    @Override
    public String mayClose() {
        if (getView().isModified()) {
            return CC.warnUnsavedChanges();
        } else {
            return null;
        }
    }

    @Override
    public FormDisplay<T> getView() {
        return view;
    }

    @Override
    public boolean validate() {
        return validate(getView());
    }

    protected boolean validate(Validator validator) {
        MessageContainer container = getView().getMessageContainer();
        if (null != container) {
            container.clear();
        }
        boolean valid = validator.validate(container);
        if (valid) {
            valid = validate(container, getView().getValue());
        }
        container.show();
        return valid;
    }

    @Override
    public void setCallback(FormPresenterCallback callback) {
        this.callback = callback;
    }

    public FormPresenterCallback getCallback() {
        return callback;
    }

    protected void requestFormClose() {
        getCallback().closeForm(false);
    }

    @Override
    public FailureHandler customFailureHandler() {
        return null;
    }

    @Override
    public MessagesByCode[] customMessages() {
        return null;
    }

    /**
     * showSaveFeedback()
     * showDeleteFeedback()
     * showFeedback(type, message)
     */

    protected abstract void findValue(P parameters, AsyncCallback<T> callback);

    /**
     * @param value
     * @param parameters
     * @param privileges
     * @param callback - do not forget to call callback.onSucess/onFailure method at the end.
     */
    protected abstract void setValue(T value, P parameters, FormPrivileges privileges, AsyncCallback<T> callback);

    /**
     * Attach required event handlers to event bus to receive events from active activities (childs or parents)
     * @param eventBus
     */
    protected abstract void attachHandlers(EventBus eventBus);

    public abstract boolean validate(MessageContainer container, T value);

    protected Double getId(Place place) {
        return place instanceof PlaceWithId ? ((PlaceWithId) place).getId() : null;
    }

    public MessageContainer getMessageContainer() {
        return getView().getMessageContainer();
    }

    public FormPrivileges getPrivileges() {
        return getView().getPrivileges();
    }
}
