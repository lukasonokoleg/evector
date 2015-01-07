package univ.evector.gwt.client.L002.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.EmailField;
import lt.jmsys.spark.gwt.client.ui.form.field.PasswordField;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationError;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.User;
import univ.evector.gwt.client.L002.presenter.L002Presenter.L002Display;
import univ.evector.gwt.client.L002.resource.L002Constants;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class L002View extends BaseFormView<User> implements L002Display {

    private final static L002Constants CONSTANTS = GWT.create(L002Constants.class);

    private FlexTable skeleton = new FlexTable();

    private HTML captionH1 = ThemeHelper.createHeadingH1(CONSTANTS.caption());

    private TextField firstNameField = new TextField(CONSTANTS.firstNameField());
    private TextField lastNameField = new TextField(CONSTANTS.lastNameField());

    private EmailField emailField = new EmailField(CONSTANTS.emailField());

    private PasswordField passwordField = new PasswordField(CONSTANTS.passwordField());
    private PasswordField passwordRepeatField = new PasswordField(CONSTANTS.passwordRepeatField());

    {

        emailField.setRequired(true);

        passwordField.setRequired(true);
        passwordRepeatField.setRequired(true);
    }

    protected Button registerBtn = EvectorButtonHelper.getInstance().createForwardButton(CONSTANTS.registerBtn());

    public L002View() {

        getBodyContainer().add(skeleton);
        constructView();
    }

    private void constructView() {
        skeleton.clear();

        int row = 0;
        int column = 0;

        skeleton.getColumnFormatter().setWidth(1, "100%");

        skeleton.setWidget(row, column, captionH1);
        skeleton.getFlexCellFormatter().setColSpan(row, column, 10);
        column = 0;
        row++;

        skeleton.setWidget(row, column, firstNameField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, firstNameField);

        column = 0;
        row++;

        skeleton.setWidget(row, column, lastNameField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, lastNameField);

        column = 0;
        row++;

        skeleton.setWidget(row, column, emailField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, emailField);

        column = 0;
        row++;

        skeleton.setWidget(row, column, passwordField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, passwordField);

        column = 0;
        row++;

        skeleton.setWidget(row, column, passwordRepeatField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, passwordRepeatField);

        column = 1;
        row++;

        skeleton.setWidget(row, column, registerBtn);

    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    @Override
    public String getFormCaption() {
        return CONSTANTS.caption();
    }

    @Override
    public void defaultFocus() {
        firstNameField.setFocus(true);
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;

        valid = valid & ValidationHelper.validateField(firstNameField, container);
        valid = valid & ValidationHelper.validateField(lastNameField, container);
        valid = valid & ValidationHelper.validateField(emailField, container);

        valid = valid
                & ValidationHelper.validateRequiredBoth(passwordField, CONSTANTS.errPassswordRequiredIfRepeated(), passwordRepeatField,
                        CONSTANTS.errRepeatRequiredIfPasswordSpecified(), container);
        if (valid) {
            valid = valid & ValidationHelper.validateField(passwordField, container);
            valid = valid & ValidationHelper.validateField(passwordRepeatField, container);
        }
        if (valid && !ConversionHelper.isEmpty(passwordField.getValue())) {
            String password = passwordField.getValue();
            String passwordRepeat = passwordRepeatField.getValue();
            if (!password.equals(passwordRepeat)) {
                valid = false;
                container.addMessage(new ValidationError(CONSTANTS.errPasswordMissmatch(), passwordRepeatField));
            }
        }

        return valid;
    }

    @Override
    protected void setFormValue(User value) {
        value = value != null ? value : newValue();

        firstNameField.setValue(value.getUsr_first_name());
        lastNameField.setValue(value.getUsr_last_name());
        emailField.setValue(value.getUsr_email());
    }

    @Override
    public void getValue(User value) {

        value.setUsr_first_name(firstNameField.getValue());
        value.setUsr_last_name(lastNameField.getValue());
        value.setUsr_email(emailField.getValue());
    }

    @Override
    public User newValue() {
        return new User();
    }

    @Override
    public String getPassword() {
        return passwordField.getValue();
    }

    @Override
    public HasClickHandlers getRegisterUserClickSrc() {
        return registerBtn;
    }

}