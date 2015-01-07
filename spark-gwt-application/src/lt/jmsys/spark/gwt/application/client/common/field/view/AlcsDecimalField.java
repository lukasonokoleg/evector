package lt.jmsys.spark.gwt.application.client.common.field.view;

import lt.jmsys.spark.gwt.application.client.common.field.resource.AlcsDecimalConstants;
import lt.jmsys.spark.gwt.client.ui.form.field.DecimalField;
import lt.jmsys.spark.gwt.client.ui.form.i18n.FormatException;

import com.google.gwt.core.client.GWT;

public class AlcsDecimalField extends DecimalField {

    private Integer fractionDigits;
    private Double minValue;
    private Double maxValue;

    protected static final AlcsDecimalConstants C = GWT.create(AlcsDecimalConstants.class);

    public AlcsDecimalField() {
        this(0.00, 999999.99, 2);
    }

    public AlcsDecimalField(Double minValue, Double maxValue, Integer fractionDigits) {
        super(generateFormat(fractionDigits));
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.fractionDigits = fractionDigits;
        setStyleName("spark-DecimalField");
        getTextBox().setStyleName("spark-DecimalField-textBox");
        getTextBox().addStyleName("spark-Field-textBox");
        addStyleName("spark-Field");
    }

    private static String generateFormat(Integer fractionDigits) {
        StringBuilder f = new StringBuilder("#,##0.");
        if (fractionDigits != null) {
            for (int i=0; i < fractionDigits; i++) {
                f.append("#");
            }
        }
        return f.toString();
    }

    private boolean isNegative(Double value) {
        boolean valid = false;
        if (value != null && minValue != null && value < minValue && minValue == 0) {
            valid = true;
        }
        return valid;
    }

    private boolean isWrongRange(Double value) {
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
    public Double getValueOrThrow() throws FormatException {
        final Double doubleValue = super.getValueOrThrow();

        if (doubleValue != null && isNegative(doubleValue)) {
            FormatException formatException = new FormatException(null, null, this, null);
            formatException.setLocalizedMessage(C.errorNegativeValue());
            throw formatException;
        }
        if (doubleValue != null && isWrongRange(doubleValue)) {
            FormatException formatException = new FormatException(null, null, this, null);

            if (minValue != null && maxValue != null) {
                formatException.setLocalizedMessage(C.errorRange(Double.toString(minValue), Double.toString(maxValue)));
            }

            if (minValue == null && maxValue != null) {
                formatException.setLocalizedMessage(C.errorRange("negative value", Double.toString(maxValue)));
            }

            if (minValue != null && maxValue == null) {
                formatException.setLocalizedMessage(C.errorRange(Double.toString(minValue), "positive value"));
            }

            throw formatException;
        }
        return doubleValue;
    }

}
