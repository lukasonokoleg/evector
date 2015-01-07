package univ.evector.gwt.client.index.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter;
import lt.jmsys.spark.gwt.application.client.application.presenter.MenuPresenter;
import lt.jmsys.spark.gwt.application.client.application.presenter.MenuPresenter.MenuDisplay;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.shared.login.bean.LogoutResult;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import lt.jmsys.spark.gwt.client.callback.CommonCancelableProgressShowingCallback;
import lt.jmsys.spark.gwt.client.ui.message.MessageType;
import univ.evector.beans.UserSession;
import univ.evector.gwt.client.F001.presenter.F001Place;
import univ.evector.gwt.client.index.IndexMenuPresenter;
import univ.evector.gwt.client.index.IndexMessageCounter;
import univ.evector.gwt.client.service.LoginService;
import univ.evector.gwt.client.service.LoginServiceAsync;
import univ.evector.gwt.client.session.EvectorPreloadedSessionData;
import univ.evector.gwt.client.session.UserSessionHolder;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

public class EvectorIndexPresenter extends ApplicationPresenter {

    private IndexMessageCounter messagesChecker = new IndexMessageCounter();

    private LoginServiceAsync loginService = GWT.create(LoginService.class);

    public interface EvectorApplicationDisplay extends ApplicationDisplay {

    }

    public EvectorIndexPresenter(ClientFactory factory, EvectorApplicationDisplay view) {
        super(factory, view);
        view.getMessagesLink().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Messages are not implemented!");
            }
        });

        view.getSettingsLink().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Messages are not implemented!");
            }
        });
        view.getUserLink().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Messages are not implemented!");
            }
        });

        messagesChecker.setEventBus(factory.getEventBus());
    }

    @Override
    public ModulePlace getFirstPlace() {
        return new F001Place();
    }

    @Override
    protected void preloadSessionData(PreloadedSessionData data, boolean afterRelogin) {
        EvectorPreloadedSessionData evData = null;
        UserSession session = null;
        if (data != null) {
            if (data instanceof EvectorPreloadedSessionData) {
                evData = (EvectorPreloadedSessionData) data;
                session = evData.getUserSession();
            }
        }
        UserSessionHolder.setUserSession(session);
        super.preloadSessionData(data, afterRelogin);
    }

    @Override
    protected MenuPresenter createMenuPresenter(MenuDisplay menuView) {
        return new IndexMenuPresenter(menuView);
    }
}
