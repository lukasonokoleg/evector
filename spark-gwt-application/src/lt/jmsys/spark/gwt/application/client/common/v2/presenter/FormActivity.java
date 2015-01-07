package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.common.dialog.ConfirmHelper;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStartEvent;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStartSource;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStopEvent;
import lt.jmsys.spark.gwt.application.client.common.v2.event.ActivityStopSource;
import lt.jmsys.spark.gwt.application.client.common.v2.event.HasActivityStartHandlers;
import lt.jmsys.spark.gwt.application.client.common.v2.event.HasActivityStopHandlers;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.client.messages.MessagesByCode;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class FormActivity<T, P> extends AbstractActivity {

    private static FormActivity<?, ?> currentActivity;

    private FormPresenter<T, P> presenter;
    private FormPresenterCallback presenterCallback;
    private boolean stopConfirmed;
    private boolean active;

    private HasActivityStartHandlers startSource = new ActivityStartSource();
    private HasActivityStopHandlers stopSource = new ActivityStopSource();

    public FormActivity(FormPresenter<T, P> presenter) {
        this.presenter = presenter;
    }

    public static FormActivity<?, ?> getCurrentActivity() {
        return currentActivity;
    }

    public void back(boolean force) {
        if (force) {
            confirmStop();
        }
        String message = mayStop();
        if (null == message) {
            ClientFactory.getInstance().getPlaceController().back();
        } else {
            ConfirmHelper.confirm(message, new Command() {

                @Override
                public void execute() {
                    confirmStop();
                    ClientFactory.getInstance().getPlaceController().back();
                }
            });
        }
    }

    public void close(boolean force) {
        back(force);
        /*        if (force) {
                    confirmStop();
                }
                String message = mayStop();
                if (null == message) {
                    onStop();
                } else {
                    ConfirmHelper.confirm(message, new Command() {

                        @Override
                        public void execute() {
                            onStop();
                        }
                    });
                }*/
    }

    protected void confirmStop() {
        stopConfirmed = true;
    }

    public boolean isStopConfirmed() {
        return stopConfirmed;
    }

    @Override
    public String mayStop() {
        return !isStopConfirmed() ? presenter.mayClose() : null;
    }

    @Override
    public void onCancel() {
        presenter.cancelLoad();
    }

    @Override
    public void onStop() {
        active = false;
        presenter.onClose();
        ActivityStopEvent.fire(getStopSource(), FormActivity.this);  
    }

    public void onStart() {
        presenter.onStart();
        ActivityStartEvent.fire(getStartSource(), this);
    }

    public EventBus getEventBus() {
        return presenter.getEventBus();
    }

    public FormPresenter<T, P> getPresenter() {
        return presenter;
    }

    @Override
    public final void start(final AcceptsOneWidget panel, EventBus eventBus) {
        start(panel, eventBus, null);
    }

    public final void start(final AcceptsOneWidget panel, final EventBus eventBus, final AsyncCallback<Void> callback) {
        Place place = ClientFactory.getInstance().getPlaceController().getWhere();
        P p = presenter.getParameters(place);
        start(panel, eventBus, p, false, callback);
    }

    public final void start(final AcceptsOneWidget panel, final EventBus eventBus, final P parameters, final boolean shownAsPopup, final AsyncCallback<Void> callback) {
        active = true;
        currentActivity = this;
        presenterCallback = new FormPresenterCallback() {

            @Override
            public boolean isShownAsPopup() {
                return shownAsPopup;
            }

            @Override
            public void closeForm(boolean warnIfModified) {
                if (isShownAsPopup()) {
                    close(!warnIfModified);
                } else {
                    back(!warnIfModified);
                }
            }
        };
        presenter.setCallback(presenterCallback);
        final MessageContainer messageContainer = presenter.getView().getMessageContainer();
        MessagesByCode[] messages = presenter.customMessages();

        new CommonProgressShowingCallback<T>(messageContainer, messages) {

            @Override
            protected void call() {
                presenter.loadByParameters(parameters, eventBus, this);
            }

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                panel.setWidget(presenter.getView());
                if (null != callback) {
                    callback.onFailure(caught);
                }                
            }

            public void onSuccess(T result) {
                super.onSuccess(result);
                if (panel != null) {
                    panel.setWidget(presenter.getView());
                }
                if (null != callback) {
                    callback.onSuccess(null);
                }
                onStart();
            }

        }.perform();
    }

    public HasActivityStopHandlers getStopSource() {
        return stopSource;
    }

    public HasActivityStartHandlers getStartSource() {
        return startSource;
    }

    public boolean isActive() {
        return active;
    }

}
