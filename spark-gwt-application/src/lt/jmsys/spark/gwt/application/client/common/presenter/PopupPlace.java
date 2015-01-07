package lt.jmsys.spark.gwt.application.client.common.presenter;

import com.google.gwt.place.shared.Place;

public class PopupPlace extends ModulePlace {

    private ModulePlace place;

    public PopupPlace(ModulePlace place) {
        super(place.getModule(), null);
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    @Override
    public String getHelpId() {
        return place.getHelpId();
    }

}
