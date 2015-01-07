package lt.jmsys.spark.gwt.application.shared.helper;

public class AppendHelper {

    public final static String DELIMETER_SPACE = " ";

    public final static String DELIMETER_COMMA_SPACED = ", ";

    private final static int INT_ZERO = 0;

    public static void append(StringBuilder buff, Integer value, String delimiter) {
        if (null != value) {
            append(buff, value.toString(), delimiter, null, null);
        }
    }

    public static void append(StringBuilder buff, String value) {
        append(buff, value, null, null, null);
    }

    public static void append(StringBuilder buff, String value, String delimiter) {
        append(buff, value, delimiter, null, null);
    }

    public static void append(StringBuilder buff, Integer value, String delimiter, String postfixDelimiter, String postfix) {
        if (null != value) {
            append(buff, value.toString(), delimiter, postfixDelimiter, postfix);
        }
    }

    public static void append(StringBuilder buff, String value, String delimiter, String postfixDelimiter, String postfix) {
        if (!ConversionHelper.isEmpty(value)) {
            if (buff.length() != 0 && null != delimiter) {
                buff.append(delimiter);
            }
            buff.append(value);
            if (null != postfixDelimiter) {
                buff.append(postfixDelimiter);
            }
            if (null != postfix) {
                buff.append(postfix);
            }
        }
    }

    public static void appendInteger(StringBuilder buff, Integer value, String delimiter) {
        if (null != value) {
            append(buff, value.intValue(), delimiter, null, null);
        }
    }

    public static void appendInteger(StringBuilder buff, Double value, String delimiter, String postfixDelimiter, String postfix) {
        if (null != value) {
            append(buff, value.intValue(), delimiter, postfixDelimiter, postfix);
        }
    }

    public static String append(String stringA, String stringB, String delimiter) {
        StringBuilder buff = new StringBuilder();
        append(buff, stringA, null);
        append(buff, stringB, delimiter);
        return buff.toString();
    }

    private static void appendNotEmptyValueToBuffer(StringBuilder buffer, String value, String delimiter) {
        if (buffer != null && !isEmpty(value)) {
            int bufferSize = buffer.length();
            if (bufferSize > INT_ZERO) {
                append(buffer, value, delimiter);
            } else {
                append(buffer, value, null);
            }
        }
    }

    public static String concatenate(String delimiter, String... values) {
        StringBuilder buff = new StringBuilder();
        if (values != null) {
            for (String value : values) {
                appendNotEmptyValueToBuffer(buff, value, delimiter);
            }
        }
        String retVal = null;
        if (buff.length() > INT_ZERO) {
            retVal = buff.toString();
        }
        return retVal;
    }

    private static boolean isEmpty(String value) {
        if (value == null) {
            return true;
        } else {
            return value.isEmpty();
        }
    }
}