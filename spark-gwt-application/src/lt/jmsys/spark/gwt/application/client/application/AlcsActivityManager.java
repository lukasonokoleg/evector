package lt.jmsys.spark.gwt.application.client.application;

import lt.jmsys.spark.gwt.application.common.client.application.AsyncActivityManager;
import lt.jmsys.spark.gwt.application.common.client.application.AsyncActivityMapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.web.bindery.event.shared.EventBus;

public class AlcsActivityManager extends AsyncActivityManager {

    private AsyncActivityMapper mapper;

    public AlcsActivityManager(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public void onPlaceChange(final PlaceChangeEvent event) {
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onSuccess() {
                if (null == mapper) {
                    mapper = GWT.create(ApplicationActivityMapper.class);
                }
                onPlaceChangeAsync(mapper, event);
            }

            @Override
            public void onFailure(Throwable reason) {
                handleRunAsyncFailure(event, reason);
            }
        });
    }
}
