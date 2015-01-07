package lt.jmsys.spark.gwt.application.client.application.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.presenter.MenuPresenter.MenuDisplay;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;
import lt.jmsys.spark.gwt.application.client.common.service.SessionService;
import lt.jmsys.spark.gwt.application.client.common.service.SessionServiceAsync;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;
import lt.jmsys.spark.gwt.application.client.messaging.MessageDeliveryManagerImpl;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.client.messaging.MessageDeliveryManager;
import lt.jmsys.spark.gwt.application.common.client.service.CommonServiceProxy;
import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.client.websocket.NotificationService;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEventHandler;
import lt.jmsys.spark.gwt.application.common.shared.messaging.MessageEvent;
import lt.jmsys.spark.gwt.application.common.shared.messaging.MessageEventHandler;
import lt.jmsys.spark.gwt.application.common.shared.messaging.MessageEventSource;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.application.shared.bean.PreloadedSessionData;
import lt.jmsys.spark.gwt.application.shared.clsf.ClassifierFactory;
import lt.jmsys.spark.gwt.application.shared.messaging.MessageCountMessage;
import lt.jmsys.spark.gwt.client.suggest.oracle.TransliterationHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.itreegroup.spark.application.shared.db.bean.Find_char_translations;
import eu.itreegroup.spark.gwt.application.client.settings.ClientSettings;

public class ApplicationPresenter {

    public interface CompanyChangeHandler {

        void onChange(Double companyId);
    }

    public interface ApplicationDisplay extends IsWidget {

        HasClickHandlers getUserLink();

        HasClickHandlers getMessagesLink();

        HasClickHandlers getSettingsLink();

        HasClickHandlers getLogoutLink();

        HasClickHandlers getHelpLink();

        void setHelpUrl(String url);

        MenuDisplay getMenuView();

        AcceptsOneWidget getFormPlaceholder();

        void setSessionInfo(SessionInfo info);

        void setUnreadMessagesCount(int unreadMessages);

        BreadCrumbsDisplay getBreadCrumbsView();

        SimplePanel getFormLegendPanel();
    }

    public interface BreadCrumbsDisplay extends IsWidget {

        void setPlaces(List<ModulePlace> places);
    }

    private final SessionServiceAsync sessionService = GWT.create(SessionService.class);

    private ApplicationDisplay view;
    private MenuPresenter menuPresenter;
    private EventBus eventBus;

    public ApplicationPresenter(ClientFactory factory, final ApplicationDisplay view) {
        this.view = view;
        this.eventBus = factory.getEventBus();
        menuPresenter = createMenuPresenter(view.getMenuView());

        view.getUserLink().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // ClientFactory.getInstance().getPlaceController().goTo(new SYS024Place());
            }
        });

        view.getMessagesLink().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                gotoMessagesForm();
            }
        });

        MessageEventSource<MessageCountMessage> messageEventSource = new MessageEventSource<MessageCountMessage>(eventBus, MessageCountMessage.class);
        messageEventSource.addMessageEventHandler(new MessageEventHandler<MessageCountMessage>() {

            @Override
            public void onMessage(MessageEvent<MessageCountMessage> event) {
                getView().setUnreadMessagesCount(event.getMessage().getUnreadMessages());
            }
        });

        factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {

            @Override
            public void onPlaceChange(PlaceChangeEvent event) {
                setLegendVisible(false);
                Place place = event.getNewPlace();
                if (place instanceof CompositePlace) {
                    place = ((CompositePlace) place).getMainPlace();
                }
                if (place instanceof ModulePlace) {
                    updateMenu((ModulePlace) place);
                }
            }
        });
        /*        factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new BreadCrumbsHandler(view.getBreadCrumbsView()));
                factory.getEventBus().addHandler(PlaceChangeEvent.TYPE, new ContextHelpHandler(view));*/

        SessionHolder.getSessionChangeSource().addValueChangeHandler(new ValueChangeHandler<SessionInfo>() {

            @Override
            public void onValueChange(ValueChangeEvent<SessionInfo> event) {
                view.setSessionInfo(event.getValue());
            }
        });
    }

    public ApplicationDisplay getView() {
        return view;
    }

    public void updateMenu(ModulePlace place) {
        menuPresenter.updateMenu(place);
    }

    public void preloadSessionData(final AsyncCallback<PreloadedSessionData> callback, final boolean afterRelogin) {

        CommonProgressShowingCallback<PreloadedSessionData> cb = new CommonProgressShowingCallback<PreloadedSessionData>(null) {

            @Override
            protected void call() {
                sessionService.preloadSessionData(false, this);
            }

            @Override
            public void onSuccess(PreloadedSessionData result) {
                if (null != result) {
                    preloadSessionData(result, afterRelogin);
                }
                super.onSuccess(result);
                callback.onSuccess(result);
            }
        };
        cb.perform();
    }

    protected void preloadSessionData(PreloadedSessionData data, boolean afterRelogin) {
        SessionHolder.setSessionInfo(data.getSessionInfo());
        SessionMonitor.start();
        if (!afterRelogin) {
            final MessageDeliveryManager messageDeliveryManager = new MessageDeliveryManagerImpl(eventBus);
            CommonServiceProxy.setMessageDeliveryManager(messageDeliveryManager);
            NotificationService notificationService = NotificationService.getInstance();
            ClassifierFactory.init(data.getClassifierMap());
            ClientSettings.init(data.getClientSettings());
            initTransliterationHelper(data.getCharTranslations());
            menuPresenter.initMenus(data.getUserMenu());
            notificationService.start();
            notificationService.subscribe("messages", new TopicEventHandler<String>() {

                @Override
                public void onMessage(TopicEvent<String> event) {
                    messageDeliveryManager.deliverMessages();
                }
            });
        }
    }

    public ModulePlace getFirstPlace() {
        return menuPresenter.getFirstPlace();
    }

    public void setLegendVisible(boolean isVisible) {
        getView().getFormLegendPanel().setVisible(isVisible);
    }

    public void setLegendWidget(Widget legend) {
        if (legend != null) {
            setLegendVisible(true);
            getView().getFormLegendPanel().setWidget(legend);
        }
    }

    private static void initTransliterationHelper(List<Find_char_translations> charTranslations) {
        if (null != charTranslations) {
            Map<Character, String> transliterationTable = new HashMap<Character, String>();
            for (Find_char_translations t : charTranslations) {
                transliterationTable.put(t.getCh_from().charAt(0), t.getCh_to());
            }
            TransliterationHelper.init(transliterationTable);
        }
    }

    protected void gotoMessagesForm() {
        // ClientFactory.getInstance().getPlaceController().goTo(new ALL001Place());
    }

    protected MenuPresenter createMenuPresenter(MenuDisplay menuView) {
        return new MenuPresenter(menuView);
    }

}
