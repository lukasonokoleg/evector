package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.common.presenter.ModulePlace;
import lt.jmsys.spark.gwt.application.client.common.presenter.PopupPlace;

public class PlaceManager {

    public final static void goTo(ModulePlace place) {
        ClientFactory.getInstance().getPlaceController().goTo(place);
    }

    public final static void goToPopup(ModulePlace place) {
        goTo(wrapIntoPopup(place));
    }

    private final static PopupPlace wrapIntoPopup(ModulePlace place) {
        return new PopupPlace(place);
    }

}
