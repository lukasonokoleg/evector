package lt.jmsys.spark.gwt.application.client.common.browse;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class BrowseFormPresenter<T, P> extends BaseFormPresenter<T, P> {

    public BrowseFormPresenter(BrowseFormDisplay<T> view) {
        super(view);
    }

    @Override
    public BrowseFormDisplay<T> getView() {
        return (BrowseFormDisplay<T>) super.getView();
    }

    @Override
    public P getParameters(Place place) {
        return null;
    }

    @Override
    public P getParameters(T value) {
        return null;
    }

    @Override
    protected void findValue(P parameters, AsyncCallback<T> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(T value, P parameters, FormPrivileges privileges, AsyncCallback<T> callback) {
        getView().setPrivileges(privileges);
        getView().refresh();
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
    }

    @Override
    public boolean validate(MessageContainer container, T value) {
        return false;
    }

}
