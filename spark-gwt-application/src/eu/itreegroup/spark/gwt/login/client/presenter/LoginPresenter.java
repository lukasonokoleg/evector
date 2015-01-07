package eu.itreegroup.spark.gwt.login.client.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.CommonFailureHandler;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.client.helper.ApplicationRedirectHelper;
import lt.jmsys.spark.gwt.application.common.client.login.service.LoginService;
import lt.jmsys.spark.gwt.application.common.client.login.service.LoginServiceAsync;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;
import lt.jmsys.spark.gwt.client.callback.CommonAsyncCallbackView;
import lt.jmsys.spark.gwt.client.callback.FailureHandler;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;
import lt.jmsys.spark.gwt.client.ui.message.RichMessage;
import lt.jmsys.spark.gwt.user.client.ui.core.validator.MessageDataProvider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

import eu.itreegroup.spark.application.bean.User;
import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;
import eu.itreegroup.spark.gwt.login.client.resource.LoginContstants;

public class LoginPresenter<USER extends User> extends BaseFormPresenter<USER, Layout> {

    public static String TOPIC_CANCEL = LoginPresenter.class.getName() + ".topic.cancel";

    private static final LoginContstants C = GWT.create(LoginContstants.class);

    protected LoginServiceAsync loginService = createLoginService();

    protected LoginServiceAsync createLoginService() {
        return GWT.create(LoginService.class);
    }

    public interface LoginDisplay<USER extends User> extends FormDisplay<USER> {

        HasClickHandlers getSignInClickSource();

        HasClickHandlers getRegistrationClickSource();

        HasClickHandlers getForgotPasswordClickSource();

        HasClickHandlers getResetPasswordClickSource();

        void layoutEditUser();

        void layoutForgotPassword();

        void layoutRegisterUser();

        void layoutSignIn();

        void layoutChangePassword();

        String getEmail();

        String getPassword();

        String getNewPassword1();

        String getNewPassword2();

        boolean getRememberMe();

        void showSaveFeedbackMessage();
    }

    private LoginDisplay<USER> view;

    public LoginPresenter(final LoginDisplay<USER> view) {
        super(view);
        this.view = view;
        view.getSignInClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (validate()) {
                    signIn();
                }
            }
        });
        view.getRegistrationClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (validate()) {
                    registerUser();
                }
            }
        });
        view.getForgotPasswordClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                view.layoutForgotPassword();
            }
        });
        view.getResetPasswordClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (validate()) {
                    resetPassword();
                }
            }
        });

    }

    @Override
    public boolean validate(MessageContainer container, User value) {
        boolean retVal = true;
        return retVal;
    }

    protected void registerUser() {
        // personal registration removed
    }

    protected void saveUser() {
        // User saving removed
    }

    protected void resetPassword() {
        final LoginServiceAsync service = GWT.create(LoginService.class);
        final String p_email = view.getEmail();
        CommonProgressShowingCallback<Void> cb = new CommonProgressShowingCallback<Void>(view.getMessageContainer()) {

            @Override
            protected void call() {
                service.forgotPassword(p_email, this);
            }

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
                view.layoutSignIn();
                view.getMessageContainer().addMessage(new RichMessage(MessageType.SUCCESS, new HTML(C.msgNewPasswordRequested())));
                view.getMessageContainer().show();
            }

        };

        cb.perform();
    }

    private class LoginFailureHandler extends CommonFailureHandler {

        public LoginFailureHandler(MessageDataProvider pMdp, MessageContainer pMsgc, CommonAsyncCallbackView pCpsc) {
            super(pMdp, pMsgc, pCpsc);
        }

        @Override
        protected void handlePasswordExpired() {
            super.handlePasswordExpired();
            view.layoutChangePassword();
        }

    }

    @Override
    public Layout getParameters(Place place) {
        return ((LoginPlace) place).getValue();
    }

    @Override
    public Layout getParameters(User value) {
        return null;
    }

    @Override
    protected void findValue(Layout parameters, AsyncCallback<USER> callback) {
        callback.onSuccess(null);
    }

    @Override
    protected void setValue(final USER value, final Layout parameters, final FormPrivileges privileges, final AsyncCallback<USER> callback) {
        AsyncCallback<Boolean> hasSessionKeyCallBack = new AsyncCallback<Boolean>() {

            @Override
            public void onSuccess(Boolean result) {
                if (Boolean.TRUE.equals(result)) {
                    signInByLoginKey(value, parameters, privileges, callback);
                } else {
                    signInByPass(value, parameters, privileges, callback);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                signInByPass(value, parameters, privileges, callback);
            }

        };
        loginService.hasSessionKey(hasSessionKeyCallBack);
    }

    protected void signIn() {
        CommonProgressShowingCallback<LoginResult> cb = new CommonProgressShowingCallback<LoginResult>(getMessageContainer()) {

            @Override
            protected void call() {
                String p_email = view.getEmail();
                String p_password = view.getPassword();
                String newPassword1 = view.getNewPassword1();
                String newPassword2 = view.getNewPassword2();
                boolean rememberMe = view.getRememberMe();
                loginService.create_session_by_pass(p_email, p_password, newPassword1, newPassword2, rememberMe, this);
            }

            @Override
            public void onSuccess(final LoginResult result) {
                finishSignIn(result);
            };

            @Override
            protected FailureHandler createFailureHandler(MessageDataProvider pMdp, MessageContainer pMsgc) {
                return new LoginFailureHandler(pMdp, pMsgc, getMsgPresenter());
            }

        };
        cb.perform();
    }

    protected void finishSignIn(LoginResult result) {
        if (result == null) {
            throw new IllegalArgumentException("Input argument result has NULL value.");
        }
        ApplicationRedirectHelper.redirect(result.getRedirectUrl());
    }

    private void signInByLoginKey(final USER value, final Layout parameters, final FormPrivileges privileges, final AsyncCallback<USER> callback) {
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

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                signInByPass(value, parameters, privileges, callback);
            }

        };
        cb.perform();
    }

    private void signInByPass(USER value, final Layout parameters, FormPrivileges privileges, AsyncCallback<USER> callback) {
        final Layout layout = parameters != null ? parameters : Layout.SIGN_IN;
        switch (layout) {
            case CHANGE_PASSWORD:
                view.layoutChangePassword();
                break;
            case FORGOT_PASSWORD:
                view.layoutForgotPassword();
                break;
            case EDIT_USER:
                view.layoutEditUser();
                break;
            case REGISTER_USER:
                view.layoutRegisterUser();
                break;
            case SIGN_IN:
            default:
                view.layoutSignIn();
                break;
        }
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {

    }

}
