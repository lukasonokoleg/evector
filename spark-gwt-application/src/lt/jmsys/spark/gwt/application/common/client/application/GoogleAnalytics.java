package lt.jmsys.spark.gwt.application.common.client.application;

import com.google.gwt.place.shared.Place;

public class GoogleAnalytics {

    public static void trackPlace(final Place place) {
        if (null != place) {
            try {
                String className = place.getClass().getName();
                className = className.substring(className.lastIndexOf('.') + 1);
                trackPlace(className);
            } catch (Throwable ignore) {
            }
        }
    }

    public static void trackPlace(final String place) {
        if (null != place) {
            try {
                _trackPlace(place);
            } catch (Throwable ignore) {
            }
        }

    }

    private static native void _trackPlace(String placeName) /*-{
		$wnd._GA_trackPlace(placeName);
    }-*/;
}
