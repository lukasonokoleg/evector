package univ.evector.beans.book.helper;

import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper.BaseFieldAccessor;
import univ.evector.beans.book.GramWord;

public class GramWordHelper {

    public static final String SPACE_STRING = " ";

    public static BaseFieldAccessor<String, GramWord> GRAM_WORD_VALUE_ACCESSOR = new BaseFieldAccessor<String, GramWord>() {

        @Override
        public String getFieldValue(GramWord bean) {
            String retVal = null;
            if (bean != null) {
                retVal = bean.getGwrd_value();
            }
            return retVal;
        }
    };

    public static BaseFieldAccessor<String, GramWord> GRAM_WORD_ORIGIN_ACCESSOR = new BaseFieldAccessor<String, GramWord>() {

        @Override
        public String getFieldValue(GramWord bean) {
            StringBuilder builder = new StringBuilder();
            if (bean != null && bean.getGwrd_origin() != null) {
                builder.append(bean.getGwrd_origin());
            }
            return builder.toString();
        }
    };

    public static String getGramWordsOriginAsString(List<GramWord> words) {
        StringBuilder builder = new StringBuilder();
        if (words != null) {
            for (GramWord word : words) {
                if (GRAM_WORD_ORIGIN_ACCESSOR.hasFieldValue(word)) {
                    builder.append(SPACE_STRING);
                    builder.append(GRAM_WORD_ORIGIN_ACCESSOR.getFieldValue(word));
                }
            }
        }
        return builder.toString();
    }

}
