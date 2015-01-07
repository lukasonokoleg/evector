package lt.jmsys.spark.gwt.application.common.client.helper;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.PlaceHelper;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window.Location;

public class ApplicationRedirectHelper {

    public static void redirect(String url) {
        if (url != null) {
            String href = Location.getHref();
            int index = href.lastIndexOf('#');
            if (index != -1) {
                url += href.substring(index);
            }
            Location.assign(url);
        } else {
            throw new IllegalStateException("Please set redirect url where to redirect after succesfull login");
        }
    }

    public static void redirectToModulePlace(String moduleUrl, Place place) {
        redirectToModulePlace(moduleUrl, place, false);
    }

    public static void redirectToModulePlace(String moduleUrl, Place place, boolean forceReload) {
        if (moduleUrl != null) {
            if (forceReload) {
                String reloadParam = "reload=" + Long.toHexString(System.currentTimeMillis());
                if (moduleUrl.contains("?")) {
                    moduleUrl = moduleUrl.replaceAll("\\?", "?" + reloadParam + "&");
                } else {
                    moduleUrl = moduleUrl + "?" + reloadParam;
                }
            }
            String url = moduleUrl;
            if (null != place) {
                String placeToken = PlaceHelper.token(place);
                url = url + "#" + placeToken;
            }
            Location.assign(url);
        } else {
            throw new IllegalArgumentException("moduleUrl cannot be null");
        }
    }

}
