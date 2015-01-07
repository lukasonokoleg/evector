package univ.evector.beans.helper;

import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper.FieldAccessor;
import univ.evector.beans.emotion.Emotion;

public class EmotionIdHelper {

    public final static FieldAccessor<Long, Emotion> EMOTION_ID_ACCESSOR = new FieldAccessor<Long, Emotion>() {

        @Override
        public Long getFieldValue(Emotion bean) {
            return bean.getEmo_id();
        }
    };

    public static List<Long> collectIds(List<Emotion> emotions) {
        List<Long> retVal = ConversionHelper.getNotNullFieldList(emotions, EMOTION_ID_ACCESSOR);
        return retVal;
    }

    public static boolean hasId(Emotion emotion) {
        boolean retVal = emotion != null && emotion.getEmo_id() != null;
        return retVal;
    }

}
