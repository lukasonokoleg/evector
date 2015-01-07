package univ.evector.gwt.client.L001.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.EmailField;
import lt.jmsys.spark.gwt.client.ui.form.field.PasswordField;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.L001.bean.LoginType;
import univ.evector.gwt.client.L001.presenter.L001Presenter.L001Display;
import univ.evector.gwt.client.L001.resource.L001Constants;
import univ.evector.gwt.client.helper.EvectorButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class L001View extends BaseFormView<LoginType> implements L001Display {

    private final static L001Constants CONSTANTS = GWT.create(L001Constants.class);

    private HTML captionH1 = ThemeHelper.createHeadingH1(CONSTANTS.caption());

    private EmailField userNameField = new EmailField(CONSTANTS.userNameField());
    private PasswordField passwordField = new PasswordField(CONSTANTS.passwordField());
    {
        userNameField.setRequired(true);
        passwordField.setRequired(true);
    }

    protected Button signInBtn = EvectorButtonHelper.getInstance().createForwardButton(CONSTANTS.singInBtn());
    protected Anchor registerAnch = EvectorButtonHelper.getInstance().createAnchor(CONSTANTS.registerAnch());

    protected Button facebookLoginButton = EvectorButtonHelper.getInstance().createForwardButton(CONSTANTS.buttonFacebookLogin());

    private FlexTable skeleton = new FlexTable();

    public L001View() {

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

        row++;
        column = 0;

        skeleton.setWidget(row, column, userNameField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, userNameField);

        StyleHelper.textLeft(userNameField.getLabelWidget());

        row++;
        column = 0;

        skeleton.setWidget(row, column, passwordField.getLabelWidget());
        column++;
        skeleton.setWidget(row, column, passwordField);

        StyleHelper.textLeft(passwordField.getLabelWidget());

        row++;
        column = 1;

        skeleton.setWidget(row, column, signInBtn);

        row++;
        column = 1;

        skeleton.setWidget(row, column, facebookLoginButton);

        row++;
        column = 1;

        skeleton.setWidget(row, column, registerAnch);

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
        userNameField.setFocus(true);
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean retVal = true;
        return retVal;
    }

    @Override
    protected void setFormValue(LoginType value) {
        userNameField.setValue(null);
        passwordField.setValue(null);
    }

    @Override
    public void getValue(LoginType value) {
        // TODO Auto-generated method stub

    }

    @Override
    public LoginType newValue() {
        return LoginType.LOGIN_BY_PASS;
    }

    @Override
    public HasClickHandlers getRegisterUserClickSrc() {
        return registerAnch;
    }

    @Override
    public HasClickHandlers getSignInUserClickSrc() {
        return signInBtn;
    }

    @Override
    public String getUserName() {
        return userNameField.getValue();
    }

    @Override
    public String getPassword() {
        return passwordField.getValue();
    }

    @Override
    public HasClickHandlers getFaceBookSignInClickSrc() {
        return facebookLoginButton;
    }

}
