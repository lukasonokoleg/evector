package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lt.jmsys.spark.gwt.application.client.common.dialog.ConfirmHelper;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStopEvent;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStopHandler;
import lt.jmsys.spark.gwt.application.client.common.v2.event.HasActivityStopHandlers;
import lt.jmsys.spark.gwt.application.client.common.v2.view.PopupFormContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.ResettableEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PopupManager {

    public interface ActivitySource {

        void getActivity(AsyncCallback<Activity> callback);
    }

    private static final PopupManager instance = new PopupManager();

    private PopupRegistration lastRegistration;

    private List<PopupFormContainer> containersQueue = new ArrayList<PopupFormContainer>();
    private List<PopupFormContainer> containersReadyToShow = new ArrayList<PopupFormContainer>();

    private PopupManager() {

    }

    public static PopupManager getInstance() {
        return instance;
    }

    public <P> void showPopup(ActivitySource activitySource, final EventBus eventBus, final MessageContainer parentMessageContainer, final P parameters) {
        activitySource.getActivity(new AsyncCallback<Activity>() {

            @Override
            public void onFailure(Throwable caught) {
                handlePopupShowFailure(parentMessageContainer, caught);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Activity activity) {
                showPopup((FormActivity<?, P>) activity, eventBus, parentMessageContainer, parameters);
            }
        });
    }

    public <P> void showPopup(final FormActivity<?, P> activity, final EventBus eventBus, final MessageContainer parentMessageContainer, final P parameters) {
        if (lastRegistration == null || !lastRegistration.isFree()) {
            lastRegistration = new PopupRegistration();
        }
        final PopupRegistration popupRegistration = this.lastRegistration;
        final ResettableEventBus resettableEventBus = new ResettableEventBus(eventBus);
        final PopupFormContainer container = popupRegistration.getContainer();
        addToQueue(container);
        popupRegistration.setActivity(activity);
        final FormActivity<?, ?> parentActivity = FormActivity.getCurrentActivity();

        HasActivityStopHandlers stopSource = activity.getStopSource();
        HandlerRegistration registration = stopSource.addActivityStopHandler(new ActivityStopHandler() {

            @Override
            public void onStop(ActivityStopEvent event) {
                container.hidePopup();
                resettableEventBus.removeHandlers();
                popupRegistration.clean();
            }
        });

        popupRegistration.setCloseRegistration(registration);
        activity.start(container, eventBus, parameters, true, new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {
                handlePopupShowFailure(parentMessageContainer, caught);
                processQueue(container, true);
            }

            @Override
            public void onSuccess(Void result) {
                processQueue(container, false);
            }
        });

    }

    protected void addToQueue(PopupFormContainer container) {
        containersQueue.add(container);
    }

    protected void processQueue(PopupFormContainer container, boolean discard) {
        if (discard) {
            containersQueue.remove(container);
        } else {
            containersReadyToShow.add(container);
        }
        Iterator<PopupFormContainer> i = containersQueue.iterator();
        while (i.hasNext()) {
            PopupFormContainer c = i.next();
            if (containersReadyToShow.contains(c)) {
                containersReadyToShow.remove(c);
                i.remove();
                c.showPopup();
            } else {
                break;
            }
        }
    }

    public void handlePopupShowFailure(MessageContainer parentMessageContainer, Throwable caught) {
        ConfirmHelper.error(null, new Command() {

            @Override
            public void execute() {
                History.back();
            }
        }, caught);
    }

    private static class PopupRegistration {

        PopupFormContainer container = new PopupFormContainer();
        FormActivity<?, ?> activity;
        HandlerRegistration closeRegistration;

        public PopupRegistration() {
            container.getCloseClickSource().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (null != activity) {
                        activity.close(false);
                    }
                }
            });

        }

        public PopupFormContainer getContainer() {
            return container;
        }

        public void setCloseRegistration(HandlerRegistration closeRegistration) {
            this.closeRegistration = closeRegistration;
        }

        public void setActivity(FormActivity<?, ?> activity) {
            this.activity = activity;
        }

        public void clean() {
            activity = null;
            if (null != closeRegistration) {
                closeRegistration.removeHandler();
                closeRegistration = null;
            }
        }

        public boolean isFree() {
            return activity == null;
        }
    }

}
