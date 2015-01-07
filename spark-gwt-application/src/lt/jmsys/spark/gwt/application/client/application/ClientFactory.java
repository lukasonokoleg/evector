package lt.jmsys.spark.gwt.application.client.application;

import lt.jmsys.spark.gwt.application.client.application.presenter.ApplicationPresenter;
import lt.jmsys.spark.gwt.application.client.application.view.ApplicationView;
import lt.jmsys.spark.gwt.application.common.client.application.AppPlaceController;
import lt.jmsys.spark.gwt.application.common.client.application.ApplicationEventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryHandler.DefaultHistorian;
import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;

public class ClientFactory {

    private static ClientFactory instance;

    private final PlaceHistoryMapper historyMapper = createHistoryMapper();
    private final Historian historian = (Historian) GWT.create(DefaultHistorian.class);
    private final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper, historian);

    private ApplicationPresenter applicationPresenter;
    private AppPlaceController placeController;

    protected ClientFactory() {
        placeController = createPlaceController(getEventBus(), historyMapper, historian);
    }

    public static void init() {
        // separate init metod is used to split ApplicationPresenter/View code from login module
        instance = GWT.create(ClientFactory.class);
    }

    public static ClientFactory getInstance() {
        if (null == instance) {
            throw new IllegalStateException("please call ClientFactory.init() first");
        }
        return instance;
    }

    public EventBus getEventBus() {
        return ApplicationEventBus.getEventBus();
    }

    public AppPlaceController getPlaceController() {
        return placeController;
    }

    public PlaceHistoryHandler getHistoryHandler() {
        return historyHandler;
    }

    public ApplicationPresenter getApplicationPresenter() {
        if (null == applicationPresenter) {
            applicationPresenter = createApplicationPresenter();
        }
        return applicationPresenter;
    }

    public PlaceHistoryMapper getHistoryMapper() {
        return historyMapper;
    }

    protected PlaceHistoryMapper createHistoryMapper() {
        return GWT.create(ApplicationHistoryMapper.class);
    }

    protected AppPlaceController createPlaceController(EventBus eventBus, PlaceHistoryMapper historyMapper, Historian historian) {
        return new AppPlaceController(eventBus, historyMapper, historian);
    }

    protected ApplicationPresenter createApplicationPresenter() {
        return new ApplicationPresenter(this, new ApplicationView());
    }

}
