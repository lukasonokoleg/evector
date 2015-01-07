package lt.jmsys.spark.gwt.application.client.common.v2.event;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;

import com.google.gwt.event.shared.GwtEvent;

public class ActivityStopEvent extends GwtEvent<ActivityStopHandler> {

    private static Type<ActivityStopHandler> TYPE;
    private FormActivity<?, ?> activity;

    private ActivityStopEvent(FormActivity<?, ?> activity) {
        this.activity = activity;
    }

    public static <T> void fire(HasActivityStopHandlers source, FormActivity<?, ?> activity) {
        ActivityStopEvent event = new ActivityStopEvent(activity);
        source.fireEvent(event);
    }

    @Override
    protected void dispatch(ActivityStopHandler handler) {
        handler.onStop(this);
    }

    public FormActivity<?, ?> getActivity() {
        return activity;
    }   

    @Override
    public Type<ActivityStopHandler> getAssociatedType() {
        return getType();
    }
    
    public static Type<ActivityStopHandler> getType() {
        if (null == TYPE) {
            TYPE = new Type<ActivityStopHandler>();
        }
        return TYPE;
    }

}
