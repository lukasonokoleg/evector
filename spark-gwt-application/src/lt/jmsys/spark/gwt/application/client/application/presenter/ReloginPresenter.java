package lt.jmsys.spark.gwt.application.client.application.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.client.login.service.LoginService;
import lt.jmsys.spark.gwt.application.common.client.login.service.LoginServiceAsync;
import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LoginResult;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import lt.jmsys.spark.gwt.client.ui.application.Application;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ReloginPresenter {

    public interface ReloginDisplay {

        String getPassword();

        MessageContainer getMessageContainer();

        boolean validate(MessageContainer container);

        void clearPassword();

        void showPopup(String username, String message);

        void hidePopup();
        
        boolean isPopupShowing();

        HasClickHandlers getLoginButton();

        HasClickHandlers getCancelButton();
    }

    private ReloginDisplay view;
    private AsyncCallback<Void> afterReloginCallback;

    public ReloginPresenter(final ReloginDisplay view) {
        this.view = view;

        view.getLoginButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (validate()) {
                    relogin();
                }
            }
        });

        view.getCancelButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                view.hidePopup();
                Application.getApplication().showLogin(false, null);
            }
        });
    }

    public boolean validate() {
        MessageContainer container = view.getMessageContainer();
        if (null != container) {
            container.clear();
        }
        boolean valid = view.validate(container);
        if (valid) {

        }
        container.show();
        return valid;
    }

    private void relogin() {
        final LoginServiceAsync service = GWT.create(LoginService.class);
        final SessionInfo sessionInfo = SessionHolder.getSessionInfo();
        final Double userId = sessionInfo.getUserId();
        final Double companyId = sessionInfo.getCompanyId();
        final String p_password = view.getPassword();

        CommonProgressShowingCallback<LoginResult> cb = new CommonProgressShowingCallback<LoginResult>(view.getMessageContainer()) {

            @Override
            protected void call() {
                service.relogin(userId, p_password, companyId, this);
            }

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                view.clearPassword();
            }

            @Override
            public void onSuccess(LoginResult result) {
                final CommonProgressShowingCallback<LoginResult> _this = this;
                ClientFactory.getInstance().getApplicationPresenter().preloadSessionData(new AsyncCallback<PreloadedSessionData>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        _this.onFailure(caught);
                        if (null != afterReloginCallback) {
                            afterReloginCallback.onFailure(caught);
                        }
                    }

                    @Override
                    public void onSuccess(PreloadedSessionData result) {
                        view.hidePopup();
                        if (null != afterReloginCallback) {
                            afterReloginCallback.onSuccess(null);
                        }
                    }
                }, true);
            }
        };

        cb.perform();
    }

    public void showReloginPopup(String message) {
        showReloginPopup(message, null);
    }

    public void showReloginPopup(String message, AsyncCallback<Void> afterReloginCallback) {
        if (!view.isPopupShowing()) {
            this.afterReloginCallback = afterReloginCallback;
            String username = SessionHolder.getSessionInfo().getFirstName() + " " + SessionHolder.getSessionInfo().getLastName();
            view.showPopup(username, message);
        }
    }
}
