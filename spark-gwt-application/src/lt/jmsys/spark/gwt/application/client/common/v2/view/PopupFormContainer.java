package lt.jmsys.spark.gwt.application.client.common.v2.view;

import lt.jmsys.spark.gwt.application.client.application.view.FormPlaceholderPanel;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.helper.ClickSource;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TheDialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupFormContainer implements FormContainer {

    private static CommonConstants CC = GWT.create(CommonConstants.class);

    private PopupDialog dialogBox = createPopup();
    private FormPlaceholderPanel containerPanel = new FormPlaceholderPanel(false, null);
    private FlowPanel buttonsPanel = new FlowPanel();
    private VerticalPanel panel = new VerticalPanel();

    private FormDisplay<?> formView;

    private ClickSource closeClickSource = createCloseClickSource();
    private Widget closeButton = createCloseButton();

    public PopupFormContainer() {

        panel.add(containerPanel);
        panel.add(buttonsPanel);

        ScrollPanel scroll = new ScrollPanel();
        scroll.add(panel);

        dialogBox.setWidget(panel);

        dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(CloseEvent<PopupPanel> event) {
                clear();
            }
        });
        StyleHelper.setPopupBody(scroll);
    }

    public void clear() {
        buttonsPanel.clear();
        containerPanel.clear();
        getCloseButton().removeFromParent();
    }

    public void hidePopup() {
        getPopUpBox().hidePoup();
    }

    public void showPopup() {
        getPopUpBox().center();
    }

    @Override
    public void setWidget(IsWidget w) {
        buttonsPanel.clear();
        if (w instanceof FormDisplay<?>) {
            formView = (FormDisplay<?>) w;
            formView.setContext(new FormDisplayContext() {

                @Override
                public boolean isShownAsPopup() {
                    return true;
                }

                @Override
                public FormContainer getFormContainer() {
                    return PopupFormContainer.this;
                }
            });
            if (formView.isButtonsContractSupported()) {
                formView.getButtonBar().addRight(getCloseButton());
            }
            buttonsPanel.add(formView.getButtonBar());
            setPopupCaption(formView.getFormCaption());
        } else {
            setPopupCaption(null);
            formView = null;
        }
        containerPanel.setWidget(w);
    }

    @Override
    public HasClickHandlers getCloseClickSource() {
        return closeClickSource;
    }

    protected Widget getCloseButton() {
        return closeButton;
    }

    protected ClickSource createCloseClickSource() {
        return new ClickSource();
    }

    protected Widget createCloseButton() {
        FocusWidget closeButton = ButtonHelper.getInstance().createCloseButton(CC.btnClose());
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                closeClickSource.click(null);
            }
        });
        return closeButton;
    }

    protected PopupDialog getPopUpBox() {
        return dialogBox;
    }

    protected PopupDialog createPopup() {
        return new PopupDialog(false);
    }

    public void setPopupCaption(String caption) {
        getPopUpBox().setText(caption);
    }

    protected class PopupDialog extends TheDialogBox {

        public PopupDialog(boolean autoHide) {
            super(autoHide);
        }

        protected void requestHidePopup() {
            closeClickSource.click(null);
        }

        @Override
        public void hide() {
            requestHidePopup();
        }

        public void hidePoup() {
            super.hide();
        }

    }

    @Override
    public void onChildSizeChanged() {
        if (getPopUpBox().isShowing()) {
            getPopUpBox().center();
        }
    }

}
