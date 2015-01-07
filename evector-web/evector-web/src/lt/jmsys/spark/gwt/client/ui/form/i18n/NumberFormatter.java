package lt.jmsys.spark.gwt.client.ui.form.i18n;

import java.math.BigDecimal;

import lt.jmsys.spark.gwt.client.ui.form.field.DecimalField;
import lt.jmsys.spark.gwt.client.ui.form.field.Field;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;

public class NumberFormatter {

    private static NumberFormat decimalFormat;// = NumberFormat.getDecimalFormat();
    private static NumberFormat integerFormat;
    static {
        I18nConstants c = GWT.create(I18nConstants.class);
        decimalFormat = NumberFormat.getFormat(c.decimalFormat());
        integerFormat = NumberFormat.getFormat(c.integerFormat());
    }

    public static String formatDecimal(double number) {
        return decimalFormat.format(number);
    }

    public static String formatDecimal(BigDecimal number) {
        return decimalFormat.format(number);
    }

    public static String formatInteger(double number) {
        return integerFormat.format(number);
    }

    public static double parseDecimal(String number, Field<?, ?> field) throws FormatException {
        try {
            return decimalFormat.parse(number);
        } catch (NumberFormatException e) {
            throw new FormatException(decimalFormat.getPattern(), number, field, e);
        }
    }

    public static long parseLong(String number, Field<?, ?> field) throws FormatException {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new FormatException(decimalFormat.getPattern(), number, field, e);
        }
    }

    public static void setDecimalFormat(NumberFormat decimalFormat) {
        NumberFormatter.decimalFormat = decimalFormat;
    }

    public static void setIntegerFormat(NumberFormat integerFormat) {
        NumberFormatter.integerFormat = integerFormat;
    }

    public static String getDecimalFormat() {
        return NumberFormatter.decimalFormat.getPattern();
    }

    public static String getIntegerFormat() {
        return NumberFormatter.integerFormat.getPattern();
    }

    public static NumberFormat getCustomFormat(int digits) {
        return getCustomFormat(digits);
    }

    public static NumberFormat getCustomFormat(int digits, boolean displayZeros) {
        String format = decimalFormat.getPattern();
        int dot = format.lastIndexOf(".");
        if (dot != -1) {
            format = format.substring(0, dot + 1);
        }
        StringBuilder buf = new StringBuilder(format);
        for (int i = 0; i < digits; i++) {
            buf.append(displayZeros ? "0" : "#");
        }
        return new DecimalField.DecimalFormat(buf.toString());
    }
}
