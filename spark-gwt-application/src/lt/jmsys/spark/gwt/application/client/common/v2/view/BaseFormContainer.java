package lt.jmsys.spark.gwt.application.client.common.v2.view;

import lt.jmsys.spark.gwt.application.client.application.view.FormPlaceholderPanel;
import lt.jmsys.spark.gwt.application.client.common.resource.CommonConstants;
import lt.jmsys.spark.gwt.application.client.common.v2.presenter.FormActivity;
import lt.jmsys.spark.gwt.application.common.client.helper.StyleHelper;
import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BaseFormContainer implements IsWidget, FormContainer {

    private static CommonConstants CC = GWT.create(CommonConstants.class);

    private FormPlaceholderPanel containerPanel = new FormPlaceholderPanel(true, null);
    private FlowPanel buttonsPanel = new FlowPanel();
    private VerticalPanel panel = new VerticalPanel();

    private FocusWidget closeClickSource = createCloseClickSource();

    public BaseFormContainer() {
        panel.add(containerPanel);
        panel.add(buttonsPanel);

        getCloseClickSource().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                FormActivity<?, ?> currentActivity = FormActivity.getCurrentActivity();
                if (null != currentActivity) {
                    currentActivity.back(false);
                }
            }
        });

        StyleHelper.setFormBody(containerPanel);
        StyleHelper.fullWidth(panel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setWidget(IsWidget w) {
        buttonsPanel.clear();
        getCloseClickSource().removeFromParent();
        if (w instanceof FormDisplay<?>) {
            FormDisplay<?> formView = (FormDisplay<?>) w;
            if (formView.isButtonsContractSupported()) {
                formView.getButtonBar().addLeft(getCloseClickSource());
                buttonsPanel.add(formView.getButtonBar());
            }
            formView.setContext(new FormDisplayContext() {

                @Override
                public boolean isShownAsPopup() {
                    return false;
                }

                @Override
                public FormContainer getFormContainer() {
                    return BaseFormContainer.this;
                }
            });
        }
        containerPanel.setWidget(w);
    }

    @Override
    public FocusWidget getCloseClickSource() {
        return closeClickSource;
    }

    protected FocusWidget createCloseClickSource() {
        return ButtonHelper.getInstance().createBackButton(CC.btnBack());
    }

    @Override
    public void onChildSizeChanged() {

    }

}
