package univ.evector.beans.helper;

import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper.FieldAccessor;
import univ.evector.beans.emotion.EmotionWord;

public class EmotionWordIdHelper {

    public final static FieldAccessor<Long, EmotionWord> EMOTION_WORD_ID_ACCESSOR = new FieldAccessor<Long, EmotionWord>() {

        @Override
        public Long getFieldValue(EmotionWord bean) {
            Long retVal = null;
            if (bean != null) {
                retVal = bean.getEmw_id();
            }
            return retVal;
        }
    };

    public static List<Long> collectIds(List<EmotionWord> words) {
        List<Long> retVal = ConversionHelper.getNotNullFieldList(words, EMOTION_WORD_ID_ACCESSOR);
        return retVal;
    }

    public static boolean hasId(EmotionWord word) {
        boolean retVal = word != null && word.getEmw_id() != null;
        return retVal;
    }

}
