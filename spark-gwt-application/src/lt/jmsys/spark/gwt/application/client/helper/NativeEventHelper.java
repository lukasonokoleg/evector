package lt.jmsys.spark.gwt.application.client.helper;

import com.google.gwt.dom.client.EventTarget;

public class NativeEventHelper {

    public native static String getButtonId(EventTarget target)/*-{
		return target.name;
    }-*/;

    public native static String getElementId(EventTarget target)/*-{
		return target.id;
    }-*/;

    public native static EventTarget getParentTarget(EventTarget target)/*-{
		return target.parentElement;
    }-*/;
}
