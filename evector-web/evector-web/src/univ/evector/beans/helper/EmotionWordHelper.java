package univ.evector.beans.helper;

import java.util.List;
import java.util.Set;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper.FieldAccessor;
import univ.evector.beans.emotion.EmotionWord;

public class EmotionWordHelper {

    public static FieldAccessor<String, EmotionWord> EMOTION_WORD_VALUE_ACCESSOR = new FieldAccessor<String, EmotionWord>() {

        @Override
        public String getFieldValue(EmotionWord bean) {
            String retVal = null;
            if (bean != null) {
                retVal = bean.getEmw_value();
            }
            return retVal;
        }
    };

    public static Set<String> collectNotNullWordSet(List<EmotionWord> words) {
        Set<String> retVal = ConversionHelper.getNotNullFieldSet(words, EMOTION_WORD_VALUE_ACCESSOR);
        return retVal;
    }

}
