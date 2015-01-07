package lt.jmsys.spark.gwt.application.client.common.dialog;

import lt.jmsys.spark.gwt.application.client.common.presenter.PopupRestoreManager;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.client.view.FormView.FormButtonBar;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import lt.jmsys.spark.gwt.client.ui.message.MessagePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TheDialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfirmationDialog extends Composite {

    protected static final ButtonHelper buttonHelper = ButtonHelper.getInstance();

    private MessagePanel messageContainer = new MessagePanel(false);
    private TheDialogBox dialogBox;
    private HTML messageWidget = new HTML();
    private VerticalPanel panel = new VerticalPanel();
    private VerticalPanel bodyContainer = new VerticalPanel();
    private String caption;

    private Button btnConfirm;
    private Button btnCancel;

    private boolean focusConfirm = true;
    private PopupRestoreManager<Void> restoreManager = new PopupRestoreManager<Void>();

    protected static final CommonConstants CC = GWT.create(CommonConstants.class);

    public ConfirmationDialog(String confirmMessage, String btnConfirmText) {
        this(confirmMessage, okButton(btnConfirmText), null);
    }

    public ConfirmationDialog(String confirmMessage) {
        this(confirmMessage, (String) null, (String) null);
    }

    public VerticalPanel getBodyContainer() {
        return bodyContainer;
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }

    public ConfirmationDialog(String confirmMessage, String btnConfirmText, String btnCancelText) {
        this(confirmMessage, okButton(btnConfirmText), cancelButton(btnCancelText));
    }

    private static Button okButton(String btnConfirmText) {
        return ButtonHelper.getInstance().createOkButton(btnConfirmText != null ? btnConfirmText : CC.msgYes());
    }

    private static Button cancelButton(String btnCancelText) {
        return ButtonHelper.getInstance().createCloseButton(btnCancelText != null ? btnCancelText : CC.msgNo());
    }

    public ConfirmationDialog(String confirmMessage, Button btnConfirm, Button btnCancel) {
        /*btnCancel = ThemeHelper.createCancelButton(btnCancelText != null ? btnCancelText : CC.msgNo());*/

        this.btnConfirm = btnConfirm;
        this.btnCancel = btnCancel;

        panel.add(messageContainer);
        panel.add(messageWidget);
        panel.setCellHorizontalAlignment(messageWidget, HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(bodyContainer);
        panel.setSpacing(5);

        setMessage(confirmMessage);

        FormButtonBar _buttonBar = new FormButtonBar();
        if (null != btnConfirm) {
            _buttonBar.add(btnConfirm);
            btnConfirm.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    setConfirmClickHandler();
                }

            });
        }
        if (null != btnCancel) {
            _buttonBar.add(btnCancel);
            btnCancel.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    setCancelClickHandler();
                }
            });
        }
        FlowPanel buttonsPanel = new FlowPanel();
        buttonsPanel.add(_buttonBar);
        panel.add(buttonsPanel);
        panel.setCellHorizontalAlignment(buttonsPanel, HasHorizontalAlignment.ALIGN_CENTER);

        initWidget(panel);

        setStyleName("spark-ConfirmationDialog");
    }

    protected void setConfirmClickHandler() {
        getMessageContainer().clear();
        if (validate(getMessageContainer())) {
            onConfirm();
            hidePopup(false);
        } else {
            getMessageContainer().show();
        }
    }

    protected void setCancelClickHandler() {
        onCancel();
        hidePopup(false);
    }

    protected TheDialogBox getDialogBox() {
        if (null == dialogBox) {
            dialogBox = new TheDialogBox(false);
            dialogBox.setWidget(this);
            dialogBox.setText(caption);
        }
        return dialogBox;
    }

    public void confirm() {
        getMessageContainer().clear();
        show();
    }

    protected void show() {
        restoreManager.attach(this);
        getDialogBox().center();
    }

    public boolean isPopupShowing() {
        return null != dialogBox ? dialogBox.isShowing() : false;
    }

    public void hidePopup(boolean autoclose) {
        restoreManager.detach();
        if (null != dialogBox) {
            dialogBox.hide(autoclose);
        }
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setMessage(String confirmMessage) {
        messageWidget.setText(confirmMessage);
    }

    public void setMesageAsSafeHtml(SafeHtml safeHtml) {
        messageWidget.setHTML(safeHtml);
    }

    public boolean validate(MessageContainer container) {
        return true;
    }

    public void onConfirm() {

    }

    public void onCancel() {

    }

    public String getValue() {
        return null;
    }

    public void setFocusConfirmButton(boolean focusConfirm) {
        this.focusConfirm = focusConfirm;
    }

    @Override
    protected void onLoad() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                if (focusConfirm && null != btnConfirm) {
                    btnConfirm.setFocus(true);
                }
                if (!focusConfirm && null != btnCancel) {
                    btnCancel.setFocus(true);
                }
            }
        });
    }

    public HandlerRegistration addCloseHandler(CloseHandler<PopupPanel> handler) {
        return getDialogBox().addCloseHandler(handler);
    }

}
