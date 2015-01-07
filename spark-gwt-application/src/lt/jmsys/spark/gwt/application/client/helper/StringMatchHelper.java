package lt.jmsys.spark.gwt.application.client.helper;

import lt.jmsys.spark.gwt.client.suggest.oracle.TransliterationHelper;

public class StringMatchHelper {

    public static boolean contains(String filter, String data) {
        if (filter == null || filter.length() == 0) {
            return true;
        } else if (data == null) {
            return false;
        } else {
            data = TransliterationHelper.transliterate(data);
            for (String f : filter.split(" ")) {
                f = TransliterationHelper.transliterate(f);
                if (!data.contains(f)) {
                    return false;
                }
            }
            return true;
        }
    }

}
