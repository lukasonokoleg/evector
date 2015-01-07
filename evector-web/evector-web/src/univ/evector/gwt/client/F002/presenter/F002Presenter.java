package univ.evector.gwt.client.F002.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F002Presenter extends BaseFormPresenter<Void, F002Place> {

    public interface F002Display extends FormDisplay<Void> {

    }

    public F002Presenter(F002Display view) {
        super(view);
        setLocalEventHandlers();
    }

    private void setLocalEventHandlers() {
    }

    @Override
    public F002Display getView() {
        return (F002Display) super.getView();
    }

    @Override
    public F002Place getParameters(Place place) {
        return (F002Place) place;
    }

    @Override
    public F002Place getParameters(Void value) {
        return null;
    }

    @Override
    protected void findValue(F002Place parameters, AsyncCallback<Void> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(Void value, F002Place parameters, FormPrivileges privileges, AsyncCallback<Void> callback) {
        getView().setValue(value);
        callback.onSuccess(null);
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
