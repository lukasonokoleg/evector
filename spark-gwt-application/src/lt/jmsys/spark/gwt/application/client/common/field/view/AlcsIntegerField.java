package lt.jmsys.spark.gwt.application.client.common.field.view;

import lt.jmsys.spark.gwt.application.client.common.field.resource.AlcsDecimalConstants;
import lt.jmsys.spark.gwt.client.ui.form.field.IntegerField;
import lt.jmsys.spark.gwt.client.ui.form.i18n.FormatException;

import com.google.gwt.core.client.GWT;

public class AlcsIntegerField extends IntegerField {

    private Integer minValue;
    private Integer maxValue;

    protected static final AlcsDecimalConstants C = GWT.create(AlcsDecimalConstants.class);

    public AlcsIntegerField() {
        this(0, null);
    }

    public AlcsIntegerField(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        setStyleName("spark-IntegerField");
        getTextBox().setStyleName("spark-IntegerField-textBox");
        getTextBox().addStyleName("spark-Field-textBox");
        addStyleName("spark-Field");
    }

    public boolean isNegative(Integer value) {
        boolean valid = false;
        if (value != null && minValue != null && value < minValue && minValue == 0) {
            valid = true;
        }
        return valid;
    }

    private boolean isWrongRange(Integer value) {
        boolean valid = false;

        if (minValue != null && maxValue != null) {
            if (value != null && minValue != null && maxValue != null) {
                if (value < minValue || value > maxValue) {
                    valid = true;
                }
            }
        }

        if (minValue == null && maxValue != null && value != null && value > maxValue) {
            valid = true;
        }

        if (minValue != null && maxValue == null && value != null && value < minValue) {
            valid = true;
        }

        return valid;
    }

    @Override
    protected String getValidationErrorMessage(Exception e) {
        if (e instanceof FormatException && e.getLocalizedMessage() != null) {
            return e.getLocalizedMessage();
        }
        return super.getValidationErrorMessage(e);
    }

    @Override
    public Integer getValueOrThrow() throws FormatException {
        Integer intValue = super.getValueOrThrow();
        validateRange(intValue);
        return intValue;
    }

    protected void validateRange(Integer intValue) throws FormatException {
        if (intValue != null && isNegative(intValue)) {
            FormatException formatException = new FormatException(null, null, this, null);
            formatException.setLocalizedMessage(C.errorNegativeValue());
            throw formatException;
        }

        if (intValue != null && isWrongRange(intValue)) {
            FormatException formatException = new FormatException(null, null, this, null);

            if (minValue != null && maxValue != null) {
                formatException.setLocalizedMessage(C.errorRange(Integer.toString(minValue), Integer.toString(maxValue)));
            }

            if (minValue == null && maxValue != null) {
                formatException.setLocalizedMessage(C.errorRange("negative value", Integer.toString(maxValue)));
            }

            if (minValue != null && maxValue == null) {
                formatException.setLocalizedMessage(C.errorRange(Integer.toString(minValue), "positive value"));
            }
            if (minValue == null && maxValue == null) {

            } else {
                throw formatException;
            }
        }
    }

}
