package univ.evector.gwt.client.F005.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F005Presenter extends BaseFormPresenter<Void, F005Place> {

    public interface F005Display extends FormDisplay<Void> {

    }

    public F005Presenter(F005Display view) {
        super(view);
    }

    @Override
    public F005Place getParameters(Place place) {
        return (F005Place) place;
    }

    @Override
    public F005Place getParameters(Void value) {
        return null;
    }

    @Override
    protected void findValue(F005Place parameters, AsyncCallback<Void> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(Void value, F005Place parameters, FormPrivileges privileges, AsyncCallback<Void> callback) {
        getView().setValue(value);
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validate(MessageContainer container, Void value) {
        return true;
    }

}
