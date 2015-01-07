package univ.evector.gwt.client.F005.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.F005.browse.FindFacebookPostBrowseForm;
import univ.evector.gwt.client.F005.presenter.F005Presenter.F005Display;
import univ.evector.gwt.client.F005.resource.F005Constants;

import com.google.gwt.core.shared.GWT;

public class F005View extends BaseFormView<Void> implements F005Display {

    private final static F005Constants C = GWT.create(F005Constants.class);

    private FindFacebookPostBrowseForm browseForm = new FindFacebookPostBrowseForm();

    public F005View() {

        getBodyContainer().add(browseForm);

    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    @Override
    public String getFormCaption() {
        return C.formCaption();
    }

    @Override
    public void defaultFocus() {
        browseForm.defaultFocus();
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean retVal = true;

        return retVal;
    }

    @Override
    protected void setFormValue(Void value) {
        browseForm.refresh();
    }

    @Override
    public void getValue(Void value) {

    }

    @Override
    public Void newValue() {
        return null;
    }

}
