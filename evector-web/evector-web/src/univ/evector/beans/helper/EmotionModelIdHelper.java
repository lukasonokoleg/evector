package univ.evector.beans.helper;

import univ.evector.beans.emotion.EmotionModel;

public class EmotionModelIdHelper {

    public static boolean hasId(EmotionModel model) {
        boolean retVal = model != null && model.getEmm_id() != null;
        return retVal;
    }

}
