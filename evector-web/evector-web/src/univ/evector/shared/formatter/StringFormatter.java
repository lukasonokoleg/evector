package univ.evector.shared.formatter;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

public class StringFormatter {

    public static String joinOnlyNotEmpty(String separator, String... values) {
        StringBuilder builder = new StringBuilder();
        if (!ConversionHelper.isEmpty(separator) && values != null) {
            boolean isFirst = true;
            for (String value : values) {
                if (!ConversionHelper.isEmpty(value)) {
                    if (isFirst) {
                        isFirst = false;
                        builder.append(value);
                    } else {
                        builder.append(separator);
                        builder.append(value);
                    }
                }
            }
        }
        return builder.toString();
    }

    public static StringBuilder append(StringBuilder text, String separator, String... textToAppend) {
        if (null != textToAppend) {
            for (String s : textToAppend) {
                append(text, separator, s);
            }
        }
        return text;
    }

    public static StringBuilder append(StringBuilder text, String separator, String textToAppend) {
        if (!ConversionHelper.isEmpty(textToAppend)) {
            if (text.length() != 0 && !ConversionHelper.isEmpty(separator)) {
                text.append(separator);
            }
            text.append(textToAppend);
        }
        return text;
    }

    public static String capitalizeFirstWord(String text) {
        if (null != text && !text.isEmpty()) {
            text = text.substring(0, 1).toUpperCase() + text.substring(1);
        }
        return text;
    }

}
