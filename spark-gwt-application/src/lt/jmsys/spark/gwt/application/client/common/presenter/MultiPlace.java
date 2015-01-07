package lt.jmsys.spark.gwt.application.client.common.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class MultiPlace extends Place {

    private List<Place> places;

    public MultiPlace(List<Place> places) {
        super();
        this.places = places;
    }

    Collection<Place> getPlaces() {
        return places;
    }

    public static class Tokenizer {

        private PlaceHistoryMapper historyMapper;

        public Tokenizer(PlaceHistoryMapper historyMapper) {
            super();
            this.historyMapper = historyMapper;
        }

        public String getToken(MultiPlace place) {
            StringBuilder token = new StringBuilder();
            for (Place p : place.getPlaces()) {
                if (token.length() != 0) {
                    token.append(",");
                }
                token.append(historyMapper.getToken(p));
            }
            return token.toString();
        }

        public MultiPlace getPlace(String token) {
            List<Place> places = new ArrayList<Place>();
            for (String t : token.split(",")) {
                places.add(historyMapper.getPlace(t));
            }
            return new MultiPlace(places);
        }

    }
}
