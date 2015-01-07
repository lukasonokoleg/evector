package lt.jmsys.spark.gwt.application.client.common.v2.event;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;

import com.google.gwt.event.shared.GwtEvent;

public class ActivityStartEvent extends GwtEvent<ActivityStartHandler> {

    private static Type<ActivityStartHandler> TYPE;
    private FormActivity<?, ?> activity;

    private ActivityStartEvent(FormActivity<?, ?> activity) {
        this.activity = activity;
    }

    public static <T> void fire(HasActivityStartHandlers source, FormActivity<?, ?> activity) {
        ActivityStartEvent event = new ActivityStartEvent(activity);
        source.fireEvent(event);
    }

    @Override
    protected void dispatch(ActivityStartHandler handler) {
        handler.onStart(this);
    }

    public FormActivity<?, ?> getActivity() {
        return activity;
    }

    @Override
    public Type<ActivityStartHandler> getAssociatedType() {
        return getType();
    }

    public static Type<ActivityStartHandler> getType() {
        if (null == TYPE) {
            TYPE = new Type<ActivityStartHandler>();
        }
        return TYPE;
    }

}
