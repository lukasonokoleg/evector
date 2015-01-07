package lt.jmsys.spark.gwt.application.client.application.view;

import lt.jmsys.spark.gwt.application.client.application.presenter.ReloginPresenter.ReloginDisplay;
import lt.jmsys.spark.gwt.application.client.application.resource.ReloginConstants;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.client.ui.form.field.PasswordField;
import lt.jmsys.spark.gwt.client.ui.form.field.TextField;
import lt.jmsys.spark.gwt.client.ui.form.validation.ValidationHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessagePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TheDialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReloginView extends Composite implements ReloginDisplay {

    private static final ReloginConstants C = GWT.create(ReloginConstants.class);
    private static final CommonConstants CC = GWT.create(CommonConstants.class);

    private PasswordField passwordField = new PasswordField(C.labelPassword());
    private Button loginButton = new Button(C.btnLogin());
    private Button cancelButton = new Button(CC.btnCancel());
    private boolean buttonClicked;

    private MessagePanel messages = new MessagePanel();
    private TextField userNameField = new TextField(C.labelUsername());

    private TheDialogBox dialog = new TheDialogBox(false, true) {

        public void hide() {
            if (!buttonClicked) {
                cancelButton.click();
            } else {
                super.hide();
            }
        };
    };

    public ReloginView() {

        userNameField.setRequired(true);
        userNameField.setReadOnly(true);
        passwordField.setRequired(true);

        VerticalPanel panel = new VerticalPanel();
        panel.add(messages);

        Grid grid = new Grid(4, 2);
        grid.getColumnFormatter().setWidth(0, "170px");
        grid.getColumnFormatter().setWidth(1, "170px");
        int row = 0;
        grid.setWidget(row, 0, userNameField.getLabelWidget());
        grid.setWidget(row, 1, userNameField);
        row++;
        grid.setWidget(row, 0, passwordField.getLabelWidget());
        grid.setWidget(row, 1, passwordField);
        row++;
        row++;

        grid.setWidget(row, 0, loginButton);
        grid.setWidget(row, 1, cancelButton);
        grid.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
        grid.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);

        panel.add(grid);
        initWidget(panel);

        passwordField.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    loginButton.click();
                }
            }
        });

        dialog.add(this);
    }

    @Override
    public String getPassword() {
        return passwordField.getValue();
    }

    @Override
    public void clearPassword() {
        passwordField.setValue(null);
    }

    @Override
    public HasClickHandlers getLoginButton() {
        return loginButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    @Override
    public MessageContainer getMessageContainer() {
        return messages;
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;
        valid = valid & ValidationHelper.validateField(passwordField, container);
        return valid;
    }

    @Override
    public void hidePopup() {
        buttonClicked = true;
        dialog.hide();
    }

    @Override
    public void showPopup(String username, String message) {
        buttonClicked = false;
        clearPassword();
        dialog.setText(message);
        userNameField.setValue(username);
        dialog.center();
        passwordField.setFocus(true);
    }

    @Override
    public boolean isPopupShowing() {
        return dialog.isShowing();
    }

}
