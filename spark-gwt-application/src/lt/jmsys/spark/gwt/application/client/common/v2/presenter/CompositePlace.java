package lt.jmsys.spark.gwt.application.client.common.v2.presenter;

import java.util.List;

import lt.jmsys.spark.gwt.application.client.application.ClientFactory;
import lt.jmsys.spark.gwt.application.client.helper.State;
import lt.jmsys.spark.gwt.application.client.helper.StateManager;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CompositePlace extends Place {

    private static long _id_gen = System.currentTimeMillis();

    private Place mainPlace;
    private List<Place> popups;
    private long stateId;

    public CompositePlace() {

    }

    public CompositePlace(Place mainPlace, List<Place> popups) {
        stateId = _id_gen++;
        this.mainPlace = mainPlace;
        this.popups = popups;
    }

    public Place getMainPlace() {
        return mainPlace;
    }

    public void setMainPlace(Place mainPlace) {
        this.mainPlace = mainPlace;
    }

    public List<Place> getPopups() {
        return popups;
    }

    public void setPopups(List<Place> popups) {
        this.popups = popups;
    }

    
    
    public Place getWhere() {
        if (null != popups && popups.size() != 0) {
            return popups.get(popups.size() - 1);
        } else {
            return mainPlace;
        }
    }

    public long getStateId() {
        return stateId;
    }

    void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public boolean isPopupPlace() {
        return null != popups && popups.size() != 0;
    }

    public static boolean isPopupPlace(Place place) {
        return place instanceof CompositePlace && ((CompositePlace) place).isPopupPlace();
    }

    public boolean isChild(CompositePlace parentPlace) {
        boolean child = false;
        if (null != parentPlace) {
            int parentSize = parentPlace.getPopups() != null ? parentPlace.getPopups().size() : 0;
            int childSize = getPopups() != null ? getPopups().size() : 0;
            child = PlaceHelper.isEqual(parentPlace.getMainPlace(), getMainPlace()) && childSize == parentSize + 1;
            if (child) {
                for (int i = 0; i < parentSize; i++) {
                    child = child && PlaceHelper.isEqual(getPopups().get(i), parentPlace.getPopups().get(i));
                }
            }
        }
        return child;
    }

    public boolean isParent(CompositePlace child) {
        return null != child && child.isChild(this);
    }

    /*    public boolean isBackFromPopup(CompositePlace lastPlace){
            return false;
        }
        
        public boolean isBackToPopup(CompositePlace lastPlace){
            return false;
        }*/

    @Prefix("C")
    public static class Tokenizer implements PlaceTokenizer<CompositePlace> {

        private static StateManager<List<Place>> stateManager = new StateManager<List<Place>>();
        private static int RADIX = Character.MAX_RADIX;

        @Override
        public CompositePlace getPlace(String token) {
            PlaceHistoryMapper mapper = ClientFactory.getInstance().getHistoryMapper();
            CompositePlace place = new CompositePlace();
            String stateId = token.split(":")[0];
            place.setStateId(Long.parseLong(stateId, RADIX));
            String mainPlaceToken = token.substring(stateId.length() + 1);
            place.setMainPlace(mapper.getPlace(mainPlaceToken));
            State<List<Place>> state = stateManager.getState(stateId);
            if (null != state) {
                place.setPopups(state.getItem());
            }
            if (null == place.getMainPlace()){
                place = null;
            }
            return place;
        }

        @Override
        public String getToken(CompositePlace place) {
            PlaceHistoryMapper mapper = ClientFactory.getInstance().getHistoryMapper();
//            Window.alert("CompositePlace.getToken place:"+place
//                    +"\nCompositePlace.getToken mainplace:"+place.getMainPlace()
//                    +"\nCompositePlace.getToken mapper:"+mapper);
            StringBuilder buff = new StringBuilder();
            String stateId = Long.toString(place.getStateId(), RADIX).toUpperCase();
            buff.append(stateId);
            buff.append(":");
            if (null != place.getMainPlace()) {
                String placeToken = mapper.getToken(place.getMainPlace());
//                Window.alert("CompositePlace.getToken placeToken:"+placeToken);
                buff.append(placeToken);
            }
            stateManager.updateState(stateId, null, place.getPopups());
            return buff.toString();
        }
    }

  

    @Override
    public String toString() {
        return CompositePlace.class.getName() + "@[mainPlace = " + mainPlace + ", popups = " + popups + "]";
    }
}
