package univ.evector.gwt.client.L002.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PlaceManager;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.User;
import univ.evector.gwt.client.L001.presenter.L001Place;
import univ.evector.gwt.client.L002.presenter.L002Place;
import univ.evector.gwt.client.service.UserRegistrationService;
import univ.evector.gwt.client.service.UserRegistrationServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class L002Presenter extends BaseFormPresenter<User, L002Place> {

    private UserRegistrationServiceAsync userRegistrationService = GWT.create(UserRegistrationService.class);

    public interface L002Display extends FormDisplay<User> {

        String getPassword();

        HasClickHandlers getRegisterUserClickSrc();

    }

    public L002Presenter(L002Display view) {
        super(view);
        setLocalEvents();
    }

    public void setLocalEvents() {
        getView().getRegisterUserClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        if (validate()) {
            CommonProgressShowingCallback<Void> cb = new CommonProgressShowingCallback<Void>(getMessageContainer()) {

                @Override
                protected void call() {
                    User user = getView().getValue();
                    String password = getView().getPassword();
                    userRegistrationService.registerUser(user, password, this);
                }

                @Override
                public void onSuccess(Void result) {
                    super.onSuccess(result);
                    getView().setNotModified();
                    L001Place nextPlace = new L001Place();
                    PlaceManager.goTo(nextPlace);
                }
            };
            cb.perform();
        }
    }

    @Override
    public L002Display getView() {
        return (L002Display) super.getView();
    }

    @Override
    public L002Place getParameters(Place place) {
        return (L002Place) place;
    }

    @Override
    public L002Place getParameters(User value) {
        return null;
    }

    @Override
    protected void findValue(L002Place parameters, AsyncCallback<User> callback) {
        userRegistrationService.defaultRegistrationUser(callback);
    }

    @Override
    protected void setValue(User value, L002Place parameters, FormPrivileges privileges, AsyncCallback<User> callback) {
        getView().setValue(value);
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
    }

    @Override
    public boolean validate(MessageContainer container, User value) {
        return true;
    }

}
