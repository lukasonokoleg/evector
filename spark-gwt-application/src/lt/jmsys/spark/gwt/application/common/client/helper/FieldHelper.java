package lt.jmsys.spark.gwt.application.common.client.helper;

import java.util.Date;

import lt.jmsys.spark.gwt.client.ui.form.field.DateField;
import lt.jmsys.spark.gwt.client.ui.form.field.DateListboxField;
import lt.jmsys.spark.gwt.client.ui.form.field.DecimalField;
import lt.jmsys.spark.gwt.client.ui.form.field.IntegerField;

public class FieldHelper {

    public static Date value(DateListboxField field) {
        try {
            return field.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static Date value(DateField field) {
        try {
            return field.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static Double value(DecimalField field) {
        try {
            return field.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer value(IntegerField field) {
        try {
            return field.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static Double doubleValue(IntegerField field) {
        try {
            return field.getValue() != null ? field.getValue().doubleValue() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
