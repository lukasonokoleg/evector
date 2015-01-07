package lt.jmsys.spark.gwt.application.common.client.application;

import java.util.logging.Logger;

import lt.jmsys.spark.gwt.application.client.application.AlcsApplication;
import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.application.resource.ApplicationConstants;
import lt.jmsys.spark.gwt.application.client.common.presenter.AlcsPrivilegedEditFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStartEvent;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStartHandler;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.CompositePlace;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PopupManager;
import lt.jmsys.spark.gwt.application.client.helper.State;
import lt.jmsys.spark.gwt.application.client.helper.StateManager;
import lt.jmsys.spark.gwt.application.common.client.websocket.NotificationService;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEvent.TopicEventType;
import lt.jmsys.spark.gwt.application.common.shared.event.TopicEventHandler;
import lt.jmsys.spark.gwt.client.helper.ValueChangeSource;
import lt.jmsys.spark.gwt.client.ui.application.Application;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.impl.AsyncDownloadHelper;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.ResettableEventBus;
import com.google.gwt.http.client.Response;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceChangeRequestEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public abstract class AsyncActivityManager extends ActivityManager {

    private static final Logger log = Logger.getLogger(AsyncActivityManager.class.getName());
    private static final StateManager<ActivityWrapper> parents = new StateManager<ActivityWrapper>();
    private static final StateManager<Boolean> startStates = new StateManager<Boolean>();

    private static ResettableEventBus lastEventBus;

    private SingleActivityMapper singleMapper;
    private ValueChangeSource<CompositePlace> currentPlaceChangeSource = new ValueChangeSource<CompositePlace>();

    private CompositePlace currentPlace;
    private CompositePlace previousPlace;

    private ActivityPool pool = new ActivityPool();

    public AsyncActivityManager(EventBus eventBus) {
        this(new SingleActivityMapper(), eventBus);
    }

    private AsyncActivityManager(SingleActivityMapper singleMapper, EventBus eventBus) {
        super(singleMapper, new WrappedEventBus(eventBus));
        this.singleMapper = singleMapper;
    }

    protected Place getDestinationPlace(PlaceChangeEvent event) {
        Place place = event.getNewPlace();
        if (place instanceof CompositePlace) {
            place = ((CompositePlace) place).getWhere();
        }
        return place;
    }

    protected boolean isPopupPlace(Place place) {
        if (place instanceof CompositePlace) {
            CompositePlace compositePlace = (CompositePlace) place;
            if (null != compositePlace.getPopups() && compositePlace.getPopups().size() != 0) {
                return true;
            }
        }
        return false;
    }

    protected CompositePlace compositePlace(Place place) {
        if (place instanceof CompositePlace) {
            return (CompositePlace) place;
        } else {
            return null;
        }
    }

    @Override
    public void onPlaceChangeRequest(PlaceChangeRequestEvent event) {
        if (!isPopupPlace(event.getNewPlace())) {
            super.onPlaceChangeRequest(event);
        }
    }

    protected void onPlaceChange(final ActivityMapper activityMapper, final PlaceChangeEvent event) {
        final ActivityMapper mapper = pool.wrap(activityMapper);
        previousPlace = currentPlace;
        currentPlace = compositePlace(event.getNewPlace());
        if (null != singleMapper.getActivity()) {
            singleMapper.getActivity().setNextPlace(currentPlace);
        }
        final Place place = getDestinationPlace(event);
        if (null != mapper) {
            singleMapper.activity = new ActivityWrapper(mapper.getActivity(place), currentPlace, singleMapper.getActivity(), previousPlace);
            singleMapper.place = event.getNewPlace();
        } else {
            singleMapper.activity = null;
            singleMapper.place = event.getNewPlace();
        }
        AsyncActivityManager.super.onPlaceChange(event);
    }

    protected void onPlaceChangeAsync(AsyncActivityMapper activityMapper, final PlaceChangeEvent event) {
        final AsyncActivityMapper mapper = pool.wrap(activityMapper);
        previousPlace = currentPlace;
        currentPlace = compositePlace(event.getNewPlace());
        if (null != singleMapper.getActivity()) {
            singleMapper.getActivity().setNextPlace(currentPlace);
        }
        final Place place = getDestinationPlace(event);
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                ((AsyncActivityMapper) mapper).getActivity(place, new AsyncCallback<Activity>() {

                    @Override
                    public void onSuccess(Activity result) {
                        mapper.getActivity(place, new AsyncCallback<Activity>() {

                            @Override
                            public void onFailure(Throwable caught) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onSuccess(Activity activity) {
                                singleMapper.activity = new ActivityWrapper(activity, currentPlace, singleMapper.getActivity(), previousPlace);
                                singleMapper.place = event.getNewPlace();
                                AsyncActivityManager.super.onPlaceChange(event);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        handleRunAsyncFailure(event, caught);
                    }
                });
            }

            @Override
            public void onFailure(Throwable reason) {
                singleMapper.activity = null;
                singleMapper.place = place;
                AsyncActivityManager.super.onPlaceChange(event);
            }
        });

    }

    protected void handleRunAsyncFailure(final PlaceChangeEvent event, Throwable caught) {
        int status = AsyncDownloadHelper.getDownloadStatusCode(caught);
        if (status == Response.SC_UNAUTHORIZED) {
            ApplicationConstants C = GWT.create(ApplicationConstants.class);
            Application application = Application.getApplication();
            if (application instanceof AlcsApplication) {
                ((AlcsApplication) application).showLogin(true, C.msgSessionExpired(), new AsyncCallback<Void>() {

                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(Void result) {
                        if (null != event) {
                            onPlaceChange(event);
                        }
                    }
                });
            } else {
                Application.getApplication().showLogin(true, C.msgSessionExpired());
            }
        } else {
            Application.getApplication().onUncaughtException(caught);
        }
    }

    private class ActivityWrapper implements Activity {

        private ActivityWrapper parent;
        private Activity activity;
        private CompositePlace previousPlace;
        private CompositePlace currentPlace;
        private CompositePlace nextPlace;
        private ResettableEventBus eventBus;

        private HandlerRegistration startRegistration;

        public ActivityWrapper(Activity activity, CompositePlace currentPlace, ActivityWrapper previousActivity, CompositePlace previousPlace) {
            this.parent = currentPlace.isChild(previousPlace) ? previousActivity : null;
            if (null != parent) {
                parents.updateState(Long.toString(currentPlace.getStateId()), null, parent);
            } else {
                State<ActivityWrapper> state = parents.getState(Long.toString(currentPlace.getStateId()));
                parent = null != state ? state.getItem() : null;
            }
            this.activity = activity;
            this.currentPlace = currentPlace;
            this.previousPlace = previousPlace;
        }

        public ActivityWrapper getParent() {
            return parent;
        }

        public String mayStop() {
            if (!currentPlace.isParent(nextPlace)) {
                return activity.mayStop();
            } else {
                return null;
            }
        }

        public void onCancel() {
            setStarted(false);
            activity.onCancel();
            cleanStartHandler();
            eventBus.removeHandlers();
        }

        public void onStop() {
            onStop(false);
        }

        /**
         * Stops activity if navigation is not to pop-up or stop request is issued from child pop-up activity;
         * @param fromChild
         */
        protected void onStop(boolean fromChild) {
            if (fromChild || !currentPlace.isParent(nextPlace)) {
                setStarted(false);
                if (null != parent && (fromChild || !currentPlace.isChild(nextPlace))) {
                    parent.onStop(true);
                }
                activity.onStop();
                log.finer("remove handlers from bus " + eventBus + ", currentPlace " + currentPlace + ", nextPlace " + nextPlace);
                eventBus.removeHandlers();
            }
            cleanStartHandler();
        }

        public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus _eventBus) {
            this.eventBus = new ResettableEventBus(_eventBus) {

                public <H extends Object> HandlerRegistration addHandler(Event.Type<H> type, final H handler) {
                    final HandlerRegistration r = super.addHandler(type, handler);
                    log.finer("add handler " + handler + " to event bus " + this + ", registration " + r);
                    return r;
                }
            };

            lastEventBus = this.eventBus;

            if (currentPlace.isPopupPlace()) {
                if (!isStarted()) {
                    if (!(activity instanceof FormActivity<?, ?>)) {
                        throw new IllegalStateException("Cannot start " + activity + " as popup, only FormActivity is supported");
                    }

                    /**
                     * Back to pop-up from full screen form
                     */
                    if (parent != null && !parent.isStarted()) {
                        panel.setWidget(null);
                        parent.start(panel, eventBus);
                    }
                    @SuppressWarnings("unchecked")
                    FormActivity<?, Object> formActivity = (FormActivity<?, Object>) activity;
                    Object parameters = formActivity.getPresenter().getParameters(currentPlace.getWhere());
                    MessageContainer parentMessageContainer = null;
                    if (null != parent && parent.getActivity() instanceof FormActivity<?, ?>) {
                        FormActivity<?, ?> parentActivity = (FormActivity<?, ?>) parent.getActivity();
                        parentMessageContainer = parentActivity.getPresenter().getView().getMessageContainer();
                    }
                    startRegistration = formActivity.getStartSource().addActivityStartHandler(new ActivityStartHandler() {

                        @Override
                        public void onStart(ActivityStartEvent event) {
                            cleanStartHandler();
                            setStartingNext(false);
                        }
                    });
                    PopupManager.getInstance().showPopup(formActivity, eventBus, parentMessageContainer, parameters);
                } else {
                    setStartingNext(false);
                }
            } else if (!isStarted()) {
                log.finer("Remove handlers from active event bus " + getActiveEventBus());
                getActiveEventBus().removeHandlers();
                AppPlaceController placeController = ClientFactory.getInstance().getPlaceController();
                startAsync(panel, eventBus, placeController.setWhereForCall(currentPlace.getWhere()));
            } else {
                setStartingNext(false);
            }
            setStarted(true);
        }

        public Activity getActivity() {
            return activity;
        }

        public void setNextPlace(CompositePlace nextPlace) {
            this.nextPlace = nextPlace;
        }

        public CompositePlace getCurrentPlace() {
            return currentPlace;
        }

        private void cleanStartHandler() {
            if (null != startRegistration) {
                startRegistration.removeHandler();
                startRegistration = null;
            }
        }

        private void setStarted(boolean started) {
            startStates.updateState(Long.toString(currentPlace.getStateId()), null, started);
            if (started){
                ValueChangeEvent.fire(currentPlaceChangeSource, currentPlace);
            }
        }

        public boolean isStarted() {
            State<Boolean> state = startStates.getState(Long.toString(currentPlace.getStateId()));
            return null != state && Boolean.TRUE.equals(state.getItem());
        }

        protected void startAsync(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus, AsyncCallback<Void> callback) {
            if (activity instanceof FormActivity<?, ?>) {
                ((FormActivity<?, ?>) activity).start(panel, eventBus, callback);
            } else if (activity instanceof AlcsPrivilegedEditFormPresenter<?>) {
                ((AlcsPrivilegedEditFormPresenter<?>) activity).start(panel, eventBus, callback);
            } else {
                activity.start(panel, eventBus);
                callback.onSuccess(null);
            }
        }

    }

    private static class SingleActivityMapper implements ActivityMapper {

        private Place place;
        private ActivityWrapper activity;

        @Override
        public Activity getActivity(Place place) {
            if (place.equals(this.place)) {
                return activity;
            } else {
                throw new IllegalStateException("Cannot get activity - mapping place: " + this.place + " does not match requested place: " + place);
            }
        }

        public ActivityWrapper getActivity() {
            return activity;
        }

    }

    private static class WrappedEventBus extends ResettableEventBus {

        public WrappedEventBus(EventBus eventBus) {
            super(eventBus);
        }

        public <H extends Object> HandlerRegistration addHandler(Event.Type<H> type, H handler) {
            final HandlerRegistration r = super.addHandler(type, handler);
            if (type instanceof TopicEventType) {
                final String topic = ((TopicEventType) type).getTopic();

                NotificationService.getInstance().subscribe(topic, new TopicEventHandler<String>() {

                    @Override
                    public void onMessage(TopicEvent<String> event) {
                        fireEvent(event);
                    }
                });

                return new HandlerRegistration() {

                    @Override
                    public void removeHandler() {
                        r.removeHandler();
                        NotificationService.getInstance().unsubscribe(topic);
                    }
                };
            } else {
                return r;
            }
        };

    }

    @Override
    public void setDisplay(final AcceptsOneWidget display) {
        super.setDisplay(new AcceptsOneWidget() {

            @Override
            public void setWidget(IsWidget w) {
                ActivityWrapper wrapper = singleMapper.getActivity();
                boolean openPopup = null != wrapper && wrapper.getCurrentPlace().isPopupPlace() && null != wrapper.getParent() && wrapper.getParent().isStarted();
                boolean closePopup = (previousPlace != null && previousPlace.isPopupPlace() && currentPlace.isParent(previousPlace));
                if (null == w && (openPopup || closePopup)) {
                    // Do not clear main panel then pop-up is opened or closed
                } else {
                    display.setWidget(w);
                }
            }
        });
    }

    @Override
    protected void removeHandlers() {
        /*        if (!currentPlace.isPopupPlace()) {
                    super.removeHandlers();
                }*/
        /*        if (!(currentPlace.isPopupPlace() && currentPlace.isChild(previousPlace))) {
                    ActivityWrapper wrapper = singleMapper.getActivity();
                    if (null != wrapper && null != wrapper.eventBus) {
                        wrapper.eventBus.removeHandlers();
                    }
                }*/
    }

    public static com.google.gwt.event.shared.EventBus getLastEventBus() {
        return lastEventBus;
    }
        
    public HasValueChangeHandlers<CompositePlace> getCurrentPlaceChangeSource() {
        return currentPlaceChangeSource;
    }

}
