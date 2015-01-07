package univ.evector.gwt.client.index;

import lt.jmsys.spark.gwt.application.common.shared.event.EntityEvent;
import lt.jmsys.spark.gwt.application.shared.messaging.MessageCountMessage;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import univ.evector.beans.Message;
import univ.evector.gwt.client.service.MessageService;
import univ.evector.gwt.client.service.MessageServiceAsync;

/**
 * New messages count timer. Refreshes new message count every 5 minutes.
 */
public class IndexMessageCounter {

    private static final int DELAY = 5 * 60 * 1000;

    private MessageServiceAsync service = GWT.create(MessageService.class);

    private EventBus eventBus;

    private int count = 0;

    Timer timer = new Timer() {

        @Override
        public void run() {
            doMessageCheck();
        }
    };

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.addHandler(EntityEvent.getType(Message.class), new EntityEvent.Handler<Message>() {

            @Override
            public void afterUpdate(final EntityEvent<Message> event) {
                doMessageCheck();
            }

            @Override
            public void afterDelete(EntityEvent<Message> event) {
                doMessageCheck();
            }

        });
        start();
    }

    public void start() {
        if (!timer.isRunning()) {
            timer.scheduleRepeating(DELAY);
        }
    }

    public void stop() {
        timer.cancel();
    }

    private void doMessageCheck() {
        doMessageCheck(null);
    }

    public void doMessageCheck(final AsyncCallback<Void> callback) {
        service.countUnreadMessages(new AsyncCallback<Long>() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Long result) {
                onMessagesCheck(result != null ? result.intValue() : 0);
                if (callback != null) {
                    callback.onSuccess(null);
                }
            }
        });
    }

    private void onMessagesCheck(int count) {
        this.count = count;
        new MessageCountMessage(count).fireMessageEvent(eventBus);
    }

    public int getMessageCount() {
        return count;
    }

}
