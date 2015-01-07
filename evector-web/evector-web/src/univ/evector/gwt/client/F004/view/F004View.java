package univ.evector.gwt.client.F004.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.F004.browse.FindUserEmotionModelsBrowseForm;
import univ.evector.gwt.client.F004.presenter.F004Presenter.F004Display;
import univ.evector.gwt.client.F004.resource.F004Constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlexTable;

public class F004View extends BaseFormView<Void> implements F004Display {

    public final static F004Constants C = GWT.create(F004Constants.class);

    private FlexTable flexTable = new FlexTable();

    private FindUserEmotionModelsBrowseForm browseForm = new FindUserEmotionModelsBrowseForm();

    public F004View() {
        getBodyContainer().add(flexTable);
        createForm();
    }

    private void createForm() {

        int row = 0;
        int column = 0;

        flexTable.setWidget(row, column, browseForm);
    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    @Override
    public String getFormCaption() {
        return null;
    }

    @Override
    public void defaultFocus() {

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

    @Override
    public HasClickHandlers getCreateNewModelClickSrc() {
        return browseForm.getCreateNewModelClickSrc();
    }

}
