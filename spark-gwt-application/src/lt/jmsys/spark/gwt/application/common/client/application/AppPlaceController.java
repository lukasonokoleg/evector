package lt.jmsys.spark.gwt.application.common.client.application;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.common.presenter.PopupPlace;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;
import lt.jmsys.spark.gwt.client.ui.application.Application;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppPlaceController extends PlaceController {

    private static final Logger log = Logger.getLogger(AppPlaceController.class.getName());

    private final PlaceHistoryMapper historyMapper;
    private final Historian historian;
    private final EventBus eventBus;

    private boolean _backInProgress;
    private List<Place> placeQueue = new ArrayList<Place>();

    private Place context;

    public AppPlaceController(EventBus eventBus, PlaceHistoryMapper historyMapper, Historian historian) {
        super((com.google.web.bindery.event.shared.EventBus) eventBus);
        this.eventBus = eventBus;
        this.historyMapper = historyMapper;
        this.historian = historian;
        historian.addValueChangeHandler(new QueueHandler());
    }

    @Override
    public void goTo(final Place newPlace) {
        if (_backInProgress) {
            log.fine("queue gotoPlace: " + newPlace);
            placeQueue.add(newPlace);
            return;
        }
        if (newPlace instanceof PopupPlace) {
            goToPopup((PopupPlace) newPlace);
            return;
        }
        final Place destinationPlace;
        if (newPlace instanceof CompositePlace) {
            destinationPlace = ((CompositePlace) newPlace).getWhere();
        } else {
            destinationPlace = newPlace;
        }
        MessageContainer c = Application.getApplication().getMessageContainer();
        if (null != c) {
            c.clear();
        }
        if (destinationPlace instanceof ApplicationPlace) {
            ApplicationPlace place = (ApplicationPlace) destinationPlace;
            if ((null == place.getMenuCode() || place.getMenuCode().isEmpty()) && getWhere() instanceof ApplicationPlace) {
                ApplicationPlace current = (ApplicationPlace) getWhere();
                place.setMenuCode(current.getMenuCode());
            }
        }
        
        // super.goTo() metode kviečiamas Window.confirm(), 
        // kuris yra sinchroninis ir uzblokuoja įvykių propagavimą,
        // todėl jį reikia kviesti asinchroniniame bloke
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            
            @Override
            public void execute() {
                AppPlaceController.super.goTo(newPlace instanceof CompositePlace ? newPlace : new CompositePlace(newPlace, null));
                GoogleAnalytics.trackPlace(destinationPlace);

                // ALCS-1589 Handle browser history when edit form refuses to exit after browser's back button was pressed
                if (!getWhere(false).equals(destinationPlace)) {
                    String token = historyMapper.getToken(getWhere(false));
                    
                    String historianToken = historian.getToken();
                    if (null != token && !token.equals(historianToken)) {
                        historian.newItem(token, false);
                    }
                }
                
            }
        });        

    }

    protected void goToPopup(PopupPlace popupPlace) {
        List<Place> popups = new ArrayList<Place>();
        Place currentPlace = getWhere(true, true);
        Place place = super.getWhere();
        if (place instanceof CompositePlace) {
            CompositePlace compositePlace = (CompositePlace) place;
            if (null != compositePlace.getPopups()) {
                popups.addAll(compositePlace.getPopups());
            }
        }
        popups.add(popupPlace.getPlace());
        goTo(new CompositePlace(currentPlace, popups));
        // eventBus.fireEvent(new ShowPopupEvent(popupPlace));
    }

    @Override
    public Place getWhere() {
        return getWhere(true, false);
    }

    protected Place getWhere(boolean resolveCompositePlace) {
        return getWhere(resolveCompositePlace, false);
    }

    protected Place getWhere(boolean resolveCompositePlace, boolean resolveToMainPlace) {
        if (null != context && resolveCompositePlace && !resolveToMainPlace) {
            return context;
        }
        Place place = super.getWhere();
        if (resolveCompositePlace && place instanceof CompositePlace) {
            place = resolveToMainPlace ? ((CompositePlace) place).getMainPlace() : ((CompositePlace) place).getWhere();
        }
        return place;
    }

    /**
     * CompositePlace introduced more than one concurrently started activity, but we still have single getWhere() method which should return correct current place.
     * @param context
     * @return
     */
    public AsyncCallback<Void> setWhereForCall(Place context) {
        this.context = context;
        return new AsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                AppPlaceController.this.context = null;
            }

            @Override
            public void onFailure(Throwable caught) {
                AppPlaceController.this.context = null;
            }
        };
    }

    public void back() {
        log.fine("back");
        _backInProgress = true;
        History.back();
    }

    private class QueueHandler implements ValueChangeHandler<String> {

        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
            if (_backInProgress) {                
                _backInProgress = false;
                log.fine("schedule queue processing after back to " + event.getValue());
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                    @Override
                    public void execute() {
                        for (Place p : placeQueue) {
                            log.fine("goTo from queue: " + p);
                            goTo(p);
                        }
                        placeQueue.clear();

                    }
                });
            }
        }
    }

}
