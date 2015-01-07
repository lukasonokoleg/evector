package univ.evector.gwt.client.L001.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PlaceManager;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.L001.bean.LoginType;
import univ.evector.gwt.client.L002.presenter.L002Place;
import univ.evector.gwt.client.service.LoginService;
import univ.evector.gwt.client.service.LoginServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class L001Presenter extends BaseFormPresenter<LoginType, L001Place> {

    private LoginServiceAsync loginService = GWT.create(LoginService.class);

    public interface L001Display extends FormDisplay<LoginType> {

        HasClickHandlers getRegisterUserClickSrc();

        HasClickHandlers getSignInUserClickSrc();

        HasClickHandlers getFaceBookSignInClickSrc();

        String getUserName();

        String getPassword();

    }

    public L001Presenter(L001Display view) {
        super(view);
        setLocalEvents();
    }

    @Override
    public L001Display getView() {
        return (L001Display) super.getView();
    }

    private void setLocalEvents() {
        getView().getRegisterUserClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getView().setNotModified();
                L002Place nextPlace = new L002Place();
                PlaceManager.goTo(nextPlace);
            }
        });
        getView().getSignInUserClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                createSessionByPassword();
            }
        });

        getView().getFaceBookSignInClickSrc().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                redirectToFacebookLogin();
            }
        });
    }

    private void redirectToFacebookLogin() {
        CommonProgressShowingCallback<String> callback = new CommonProgressShowingCallback<String>(getMessageContainer()) {

            @Override
            protected void call() {
                loginService.getFaceBookLoginUrl(this);
            }

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Location.assign(result);
            }
        };
        callback.perform();
    }

    private void createSessionByPassword() {
        CommonProgressShowingCallback<LoginResult> callback = new CommonProgressShowingCallback<LoginResult>(getMessageContainer()) {

            @Override
            protected void call() {
                String username = getView().getUserName();
                String password = getView().getPassword();

                loginService.create_session_by_pass(username, password, null, null, false, this);
            }

            @Override
            public void onSuccess(LoginResult result) {
                super.onSuccess(result);
                finishSignIn(result);
            }

        };
        callback.perform();
    }

    protected void finishSignIn(LoginResult result) {
        if (result == null) {
            throw new IllegalArgumentException("Input argument result has NULL value.");
        }
        Location.assign(result.getRedirectUrl());
        /* ApplicationRedirectHelper.redirect(result.getRedirectUrl());*/
    }

    @Override
    public L001Place getParameters(Place place) {
        return (L001Place) place;
    }

    @Override
    public L001Place getParameters(LoginType value) {
        return null;
    }

    @Override
    protected void findValue(L001Place parameters, AsyncCallback<LoginType> callback) {
        loginService.getUserLoginType(callback);
    }

    @Override
    protected void setValue(LoginType value, L001Place parameters, FormPrivileges privileges, AsyncCallback<LoginType> callback) {
        if (LoginType.LOGIN_BY_FACEBOOK.equals(value)) {
            loginByFacebook(callback);
        } else if (LoginType.LOGIN_BY_SESSION_KEY.equals(value)) {
            loginBySessionKey(callback);
        } else {
            callback.onSuccess(value);
        }
    }

    private void loginBySessionKey(AsyncCallback<LoginType> callback) {
        CommonProgressShowingCallback<LoginResult> cb = new CommonProgressShowingCallback<LoginResult>(getMessageContainer()) {

            @Override
            protected void call() {
                loginService.create_session_by_key(this);
            }

            @Override
            public void onSuccess(LoginResult result) {
                super.onSuccess(result);
                finishSignIn(result);
            }

        };
        cb.perform();
    }

    private void loginByFacebook(AsyncCallback<LoginType> callback) {
        CommonProgressShowingCallback<LoginResult> cb = new CommonProgressShowingCallback<LoginResult>(getMessageContainer()) {

            @Override
            protected void call() {
                loginService.create_session_by_facebook(this);
            }

            @Override
            public void onSuccess(LoginResult result) {
                super.onSuccess(result);
                finishSignIn(result);
            }

        };
        cb.perform();
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validate(MessageContainer container, LoginType value) {
        return true;
    }

}
