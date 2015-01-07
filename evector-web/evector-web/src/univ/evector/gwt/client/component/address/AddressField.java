package univ.evector.gwt.client.component.address;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.TextBoxField;
import lt.jmsys.spark.gwt.client.ui.form.i18n.FormatException;
import lt.jmsys.spark.gwt.client.ui.message.ValidationErrorMessage;
import univ.evector.beans.Address;
import univ.evector.shared.formatter.AddressFormatter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

public class AddressField extends TextBoxField<Address, AddressField> {

    private Address value;
    private AddressFieldPopup popup = new AddressFieldPopup();

    public AddressField(String labelText, boolean required) {
        super(labelText, required, null);
        getTextBox().addFocusHandler(new FocusHandler() {

            @Override
            public void onFocus(FocusEvent event) {
                showPopup();
            }
        });

        getTextBox().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                showPopup();
            }
        });

        popup.getOkClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                popup.getMessageContainer().clear();
                if (popup.validate(popup.getMessageContainer())) {
                    setValue(popup.getValue(), true);
                    popup.hide();
                } else {
                    popup.getMessageContainer().show();
                }
            }
        });

        popup.setRequired(required);
        getTextBox().setReadOnly(true);
    }

    @Override
    public void setValue(Address value, boolean fireEvents) {
        this.value = value;
        setStringValue(AddressFormatter.toString(value));
    }

    @Override
    public Address getValueOrThrow() throws FormatException {
        return value;
    }

    public void showPopup() {
        popup.setValue(value);
        popup.show(this);
    }

    @Override
    public void setRequired(boolean required) {
        super.setRequired(required);
        if (null != popup) {
            popup.setRequired(required);
        }
    }

    @Override
    public ValidationErrorMessage validate() {
        Address value = getValue();
        String stringValue = getStringValue();
        if (isRequired() && (value == null || ConversionHelper.isEmpty(stringValue))) {
            return errorFieldIsRequired();
        }
        return super.validate();
    }

    public boolean isStrictAddressInput() {
        return popup.isStrictAddressInput();
    }

    public void setStrictAddressInput(boolean strictAddressInput) {
        popup.setStrictAddressInput(strictAddressInput);
    }

    @Override
    protected String validateInteractive(boolean format) {
        return null;
    }

}
