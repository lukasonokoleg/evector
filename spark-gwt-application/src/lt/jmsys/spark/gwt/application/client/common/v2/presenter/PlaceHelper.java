package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.common.presenter.PlaceWithId;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class PlaceHelper {

    public static boolean isStateEqual(Place placeA, Place placeB, boolean unwrapComposite) {
        String stateA = getStateId(placeA, unwrapComposite);
        String stateB = getStateId(placeB, unwrapComposite);
        return ConversionHelper.isEqual(stateA, stateB);
    }

    private static String getStateId(Place place, boolean unwrapComposite) {
        if (unwrapComposite && place instanceof CompositePlace) {
            place = ((CompositePlace) place).getWhere();
        }
        if (place instanceof PlaceWithId) {
            return ((PlaceWithId) place).getStateId();
        } else if (place instanceof CompositePlace) {
            return Long.toString(((CompositePlace) place).getStateId());
        } else {
            return null;
        }
    }

    public static boolean isEqual(Place placeA, Place placeB) {
        return ConversionHelper.isEqual(token(placeA), token(placeB));
    }

    public static String token(Place place) {
        if (null != place) {
            PlaceHistoryMapper mapper = ClientFactory.getInstance().getHistoryMapper();
            return mapper.getToken(place);
        } else {
            return null;
        }
    }

}
