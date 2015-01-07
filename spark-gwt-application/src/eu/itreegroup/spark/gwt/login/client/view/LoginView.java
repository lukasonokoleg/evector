package eu.itreegroup.spark.gwt.login.client.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.client.helper.ClickSourceHelper;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.application.common.client.widget.FeedbackMessage;
import lt.jmsys.spark.gwt.client.helper.ClickSource;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.CheckBoxField;
import lt.jmsys.spark.gwt.client.ui.form.field.PasswordField;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;

import eu.itreegroup.spark.application.bean.User;
import eu.itreegroup.spark.application.bean.UserBean;
import eu.itreegroup.spark.gwt.login.client.presenter.Layout;
import eu.itreegroup.spark.gwt.login.client.presenter.LoginPresenter.LoginDisplay;
import eu.itreegroup.spark.gwt.login.client.resource.LoginContstants;

public abstract class LoginView<USER extends User> extends BaseFormView<USER> implements LoginDisplay<USER> {

    private static final String FIELDS_WIDTH = "100%";

    private static final LoginContstants C = GWT.create(LoginContstants.class);
    protected TextField userField;
    protected TextField firstNameField;
    protected TextField lastNameField;

    // Oleg Lukašonok pagal mane bazėje klasėje telefonas nereikalingas.
    /*protected TextField phoneField;*/

    protected PasswordField passwordField;
    protected PasswordField newPassword1Field;
    protected PasswordField newPassword2Field;
    protected CheckBoxField rememberMeField;
    protected Anchor forgotPasswordLink;
    protected Anchor backToSignInLink;

    protected Button saveButton;
    protected Button cancelButton;
    protected Button signInButton;
    protected Button registerButton;
    protected Button resetPasswordButton;
    protected Button registerUserLink;

    private SimplePanel contentPanel;
    private Layout currentLayout;
    private Layout backLayout;

    private ClickSource signInClickSource;

    private USER value;

    public LoginView() {

        setStyleName("spark-LoginView");

        contentPanel = new SimplePanel();

        userField = new TextField(C.labelUser(), true, 100);
        firstNameField = new TextField(C.labelName(), true, 60);
        lastNameField = new TextField(C.labelSurname(), true, 60);
        passwordField = new PasswordField(C.labelPassword());
        passwordField.setRequired(true);

        newPassword1Field = new PasswordField(C.labelNewPassword1());
        newPassword1Field.setRequired(true);

        newPassword2Field = new PasswordField(C.labelNewPassword2());
        newPassword2Field.setRequired(true);

        /*    phoneField = new PhoneField(C.labelPhone());*/

        userField.setWidth(FIELDS_WIDTH);
        firstNameField.setWidth(FIELDS_WIDTH);
        /* phoneField.setWidth(FIELDS_WIDTH);*/
        lastNameField.setWidth(FIELDS_WIDTH);
        passwordField.setWidth(FIELDS_WIDTH);
        newPassword1Field.setWidth(FIELDS_WIDTH);
        newPassword2Field.setWidth(FIELDS_WIDTH);

        userField.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    String password = passwordField.getValue();
                    if (password != null && !password.isEmpty()) {

                    } else {
                        passwordField.setFocus(true);
                    }
                }

            }
        });

        ThemeHelper.alignLeft(userField.getLabelWidget());
        ThemeHelper.alignLeft(firstNameField.getLabelWidget());
        /*  ThemeHelper.alignLeft(phoneField.getLabelWidget());*/
        ThemeHelper.alignLeft(lastNameField.getLabelWidget());
        ThemeHelper.alignLeft(passwordField.getLabelWidget());
        ThemeHelper.alignLeft(newPassword1Field.getLabelWidget());
        ThemeHelper.alignLeft(newPassword2Field.getLabelWidget());

        rememberMeField = new CheckBoxField(C.msgRememberMe());

        saveButton = ButtonHelper.getInstance().createOkButton(CC.btnSave());
        cancelButton = ButtonHelper.getInstance().createCancelButton(CC.btnCancel());
        signInButton = ButtonHelper.getInstance().createButton(C.buttonSignIn());
        registerButton = ButtonHelper.getInstance().createButton(C.buttonRegisterUser());
        resetPasswordButton = ButtonHelper.getInstance().createButton(C.buttonResetPassword());

        forgotPasswordLink = ButtonHelper.getInstance().createAnchor(C.linkForgotPassword());
        backToSignInLink = ButtonHelper.getInstance().createAnchor(C.linkBackToSignIn());
        registerUserLink = ButtonHelper.getInstance().createButton(C.linkRegisterUser());
        // backToSignInLink.getElement().getStyle().setFloat(Float.RIGHT);

        backToSignInLink.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (Layout.CHANGE_PASSWORD.equals(backLayout)) {
                    layoutChangePassword();
                } else {
                    layoutSignIn();
                }
            }
        });

        /*        registerUserLink.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        layoutRegisterUser();
                    }
                });*/

        signInClickSource = ClickSourceHelper.createAgregatedClickSource(signInButton, passwordField.getTextBox());

        getBodyContainer().add(contentPanel);
        getSaveClickSource().setVisible(false);

        forgotPasswordLink.setTabIndex(1);
    }

    @Override
    public void layoutEditUser() {
        currentLayout = Layout.EDIT_USER;
        getMessageContainer().clear();
        setPopupCaption(C.captionEditUser());

        FlexTable table = new FlexTable();
        table.setWidth("100%");
        int r = 0;
        userField.setReadOnly(true);
        table.setWidget(r++, 0, userField.getLabelWidget());
        table.setWidget(r, 0, userField);
        r++;
        table.setWidget(r++, 0, firstNameField.getLabelWidget());
        table.setWidget(r, 0, firstNameField);
        r++;
        table.setWidget(r++, 0, lastNameField.getLabelWidget());
        table.setWidget(r, 0, lastNameField);
        /* r++;
         table.setWidget(r++, 0, phoneField.getLabelWidget());
         table.setWidget(r, 0, phoneField);*/

        ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;
        FlowPanel fp = new FlowPanel();
        fp.add(getSaveClickSource());
        fp.add(getCancelClickSource());
        table.setWidget(r, 0, fp);

        ThemeHelper.createEmptyRows(table, ++r, 1);

        contentPanel.setWidget(table);
        hideButtons();
        getSaveClickSource().setVisible(true);
        getCancelClickSource().setVisible(true);
        center();
        defaultFocus();
    }

    @Override
    public void layoutRegisterUser() {
        currentLayout = Layout.REGISTER_USER;
        getMessageContainer().clear();
        setPopupCaption(C.captionRegisterUser());

        FlexTable table = new FlexTable();
        table.setWidth("100%");
        int r = 0;

        /*        table.setWidget(r, 0, new HTML(C.htmlCreatePersonalAccount()));
                r++;*/
        userField.setReadOnly(false);
        table.setWidget(r++, 0, userField.getLabelWidget());
        // table.setWidget(r++, 0, new HTML(C.htmlHintRegistrationEmail()));
        table.setWidget(r, 0, userField);
        r++;
        table.setWidget(r++, 0, firstNameField.getLabelWidget());
        table.setWidget(r, 0, firstNameField);
        r++;
        table.setWidget(r++, 0, lastNameField.getLabelWidget());
        table.setWidget(r, 0, lastNameField);
        /*  r++;
          table.setWidget(r++, 0, phoneField.getLabelWidget());
          table.setWidget(r, 0, phoneField);*/

        ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;
        table.setWidget(r, 0, new HTML(C.htmlAgreeWithRules()));

        ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;
        FlowPanel fp = new FlowPanel();
        fp.add(registerButton);
        fp.add(backToSignInLink);
        table.setWidget(r, 0, fp);

        ThemeHelper.createEmptyRows(table, ++r, 1);
        /*        r++;
                table.setWidget(r, 0, new HTML(C.htmlHintPrivateToCorporate()));*/
        /*        r++;
                FlowPanel fp = new FlowPanel();
                fp.add(forgotPasswordLink);
                fp.add(new InlineLabel(" / "));
                fp.add(backToSignInLink);
                table.setWidget(r, 0, fp);
                table.getFlexCellFormatter().setColSpan(r, 0, 2);
                table.getCellFormatter().setHorizontalAlignment(r, 0, HasHorizontalAlignment.ALIGN_CENTER);
                ThemeHelper.createEmptyRows(table, ++r, 1);
                */

        contentPanel.setWidget(table);
        hideButtons();
        registerButton.setVisible(true);
        center();
        defaultFocus();
    }

    @Override
    public void layoutSignIn() {
        currentLayout = Layout.SIGN_IN;
        getMessageContainer().clear();
        setPopupCaption(C.captionSignIn());
        FlexTable table = new FlexTable();
        table.setWidth("100%");
        int r = 0;
        userField.setReadOnly(false);
        table.setWidget(r++, 0, userField.getLabelWidget());
        table.setWidget(r, 0, userField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        r++;
        table.setWidget(r, 0, passwordField.getLabelWidget());
        table.setWidget(r, 1, forgotPasswordLink);
        table.getCellFormatter().setHorizontalAlignment(r, 1, HasHorizontalAlignment.ALIGN_RIGHT);
        r++;
        table.setWidget(r, 0, passwordField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        r++;
        table.setWidget(r, 0, rememberMeField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;

        ThemeHelper.createEmptyRows(table, ++r, 1);

        FlowPanel buttons = new FlowPanel();
        buttons.add(signInButton);
        buttons.add(registerUserLink);
        table.setWidget(r, 0, buttons);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        // table.getCellFormatter().setHorizontalAlignment(r, 0, HasHorizontalAlignment.ALIGN_CENTER);
        // table.getCellFormatter().setHorizontalAlignment(r, 1, HasHorizontalAlignment.ALIGN_CENTER);

        ThemeHelper.createEmptyRows(table, ++r, 1);

        contentPanel.setWidget(table);
        hideButtons();
        signInButton.setVisible(true);
        center();
        defaultFocus();
    }

    @Override
    public void layoutChangePassword() {
        currentLayout = Layout.CHANGE_PASSWORD;
        setPopupCaption(C.captionSignIn());

        FlexTable table = new FlexTable();
        table.setWidth("100%");
        int r = 0;
        userField.setReadOnly(false);
        table.setWidget(r++, 0, userField.getLabelWidget());
        table.setWidget(r, 0, userField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        r++;
        table.setWidget(r++, 0, passwordField.getLabelWidget());
        table.setWidget(r, 0, passwordField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        r++;
        table.setWidget(r++, 0, newPassword1Field.getLabelWidget());
        table.setWidget(r, 0, newPassword1Field);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        r++;
        table.setWidget(r++, 0, newPassword2Field.getLabelWidget());
        table.setWidget(r, 0, newPassword2Field);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        r++;
        table.setWidget(r, 0, rememberMeField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);
        ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;

        ThemeHelper.createEmptyRows(table, ++r, 1);

        FlowPanel buttons = new FlowPanel();
        buttons.add(signInButton);
        buttons.add(registerUserLink);
        table.setWidget(r, 0, buttons);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);

        ThemeHelper.createEmptyRows(table, ++r, 1);

        contentPanel.setWidget(table);
        hideButtons();
        signInButton.setVisible(true);

        center();
        newPassword1Field.setFocus(true);
    }

    @Override
    public void layoutForgotPassword() {
        backLayout = currentLayout;
        currentLayout = Layout.FORGOT_PASSWORD;
        getMessageContainer().clear();
        setPopupCaption(C.captionForgotPassword());

        FlexTable table = new FlexTable();
        table.setWidth("100%");
        int r = 0;
        userField.setReadOnly(false);
        table.setWidget(r++, 0, userField.getLabelWidget());
        table.setWidget(r, 0, userField);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);

        // ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;
        table.setWidget(r, 1, backToSignInLink);
        table.getCellFormatter().setHorizontalAlignment(r, 1, HasHorizontalAlignment.ALIGN_RIGHT);

        ThemeHelper.createEmptyRows(table, ++r, 1);
        r++;
        FlowPanel buttons = new FlowPanel();
        buttons.add(resetPasswordButton);
        buttons.add(registerUserLink);
        table.setWidget(r, 0, buttons);
        table.getFlexCellFormatter().setColSpan(r, 0, 2);

        contentPanel.setWidget(table);
        hideButtons();
        resetPasswordButton.setVisible(true);

        center();
        defaultFocus();
    }

    protected void hideButtons() {
        registerButton.setVisible(false);
        signInButton.setVisible(false);
        resetPasswordButton.setVisible(false);
        getSaveClickSource().setVisible(false);
        getCancelClickSource().setVisible(false);
    }

    @Override
    public Anchor getForgotPasswordClickSource() {
        return forgotPasswordLink;
    }

    @Override
    public HasClickHandlers getResetPasswordClickSource() {
        return resetPasswordButton;
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean editUser = Layout.EDIT_USER.equals(currentLayout);
        boolean registration = Layout.REGISTER_USER.equals(currentLayout) || editUser;
        boolean changePassword = Layout.CHANGE_PASSWORD.equals(currentLayout);
        boolean signin = Layout.SIGN_IN.equals(currentLayout) || changePassword;
        boolean valid = true;
        valid = valid & ValidationHelper.validateField(userField, container);
        valid = valid & (!signin || ValidationHelper.validateField(passwordField, container));
        valid = valid & (!changePassword || ValidationHelper.validateField(newPassword1Field, container));
        valid = valid & (!changePassword || ValidationHelper.validateField(newPassword2Field, container));

        /*valid = valid & (!registration || ValidationHelper.validateField(phoneField, container));*/

        valid = valid & (!registration || ValidationHelper.validateField(firstNameField, container));
        valid = valid & (!registration || ValidationHelper.validateField(lastNameField, container));
        return valid;
    }

    @Override
    public void setFormValue(USER value) {
        this.value = value;
        value = null != value ? value : newValue();
        passwordField.setValue(null);
        rememberMeField.setValue(null);

        firstNameField.setValue(value.getFirstName());
        lastNameField.setValue(value.getLastName());

        /*  phoneField.setValue(value.getPhone());*/
        userField.setValue(value.getEmail());
    }

    @Override
    public boolean isModified() {
        return super.isModified() && Layout.EDIT_USER.equals(currentLayout);
    }

    @Override
    public USER getValue() {
        USER retVal = value != null ? value : newValue();
        getValue(value);
        return retVal;
    }

    @Override
    public void getValue(USER value) {
        /*  setUserEmail(value, userField.getValue());
        */
        if (value instanceof UserBean) {
            getValue((UserBean) value);
        }
    }

    private void getValue(UserBean value) {
        if (Layout.REGISTER_USER.equals(currentLayout) || Layout.EDIT_USER.equals(currentLayout)) {
            value.setFirstName(firstNameField.getValue());
            value.setLastName(lastNameField.getValue());

            /*            value.setPhone(phoneField.getValue());*/
        } else {
            value.setFirstName(null);
            value.setLastName(null);
            /*            value.setPhone(null);*/
        }
    }

    @Override
    public HasClickHandlers getSignInClickSource() {
        return signInClickSource;
    }

    @Override
    public HasClickHandlers getRegistrationClickSource() {
        return registerButton;
    }

    @Override
    public String getEmail() {
        return userField.getValue();
    }

    @Override
    public String getPassword() {
        return passwordField.getValue();
    }

    @Override
    public String getNewPassword1() {
        return newPassword1Field.getValue();
    }

    @Override
    public String getNewPassword2() {
        return newPassword2Field.getValue();
    }

    @Override
    public boolean getRememberMe() {
        return Boolean.TRUE.equals(rememberMeField.getValue());
    }

    @Override
    public void defaultFocus() {
        if (Layout.REGISTER_USER.equals(currentLayout)) {
            firstNameField.setFocus(true);
        } else if (Layout.REGISTER_USER.equals(currentLayout)) {
            userField.setFocus(true);
        } else if (Layout.SIGN_IN.equals(currentLayout)) {
            if (null == userField.getValue()) {
                userField.setFocus(true);
            } else {
                passwordField.setFocus(true);
            }
        } else if (Layout.FORGOT_PASSWORD.equals(currentLayout)) {
            if (null == userField.getValue()) {
                userField.setFocus(true);
            } else {
                resetPasswordButton.setFocus(true);
            }
        }
    }

    public Button getSaveClickSource() {
        return saveButton;
    }

    public Button getCancelClickSource() {
        return cancelButton;
    }

    @Override
    public void showSaveFeedbackMessage() {
        FeedbackMessage.showMessage(getSaveClickSource(), C.msgUserUpdated());
    }

    /*    private static String getUserEmail(UserBean user) {
            if (null != user && !ConversionHelper.isEmpty(user.getUser_emails())) {
                return user.getUser_emails()[0].getEmail();
            } else {
                return null;
            }
        }*/

    /*   private static void setUserEmail(Als_user_ot user, String email) {
           if (ConversionHelper.isEmpty(email)) {
               user.setUser_emails(null);
           } else {
               user.setUser_emails(new Als_user_email_ot[] { new Als_user_email_ot(email) });
           }
       }*/

    private void setPopupCaption(String caption) {

    }

    private void center() {
        resizeFormContainer();
    }

    @Override
    public String getFormCaption() {
        return null;
    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    public Button getRegisterUserLink() {
        return registerUserLink;
    }

}
