package lt.jmsys.spark.gwt.application.client.helper;

import com.google.gwt.core.client.JavaScriptObject;


public class JsniHelper {
    public static native int getArrayLength(JavaScriptObject array)/*-{
        return array.length;
    }-*/;
    
    public static native JavaScriptObject getArrayElement(JavaScriptObject array, int index)/*-{
        return array[index];
    }-*/;
}
