package univ.evector.gwt.client.F001.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.client.helper.ThemeHelper;
import lt.jmsys.spark.gwt.application.common.shared.event.HasValueClickHandlers;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.gwt.client.F001.browse.F001FindDocumentsBrowseForm;
import univ.evector.gwt.client.F001.presenter.F001Presenter.F001Display;
import univ.evector.gwt.client.F001.resource.F001Constants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class F001View extends BaseFormView<Void> implements F001Display {

    private final static F001Constants CONSTANTS = GWT.create(F001Constants.class);

    private FlexTable skeleton = new FlexTable();

    private HTML formHeadingHtml = ThemeHelper.createHeadingH1(CONSTANTS.formHeadingHtml());

    private F001FindDocumentsBrowseForm findDocumentsBrowseForm = new F001FindDocumentsBrowseForm();

    public F001View() {
        getBodyContainer().add(skeleton);
        constructBody();
    }

    private void constructBody() {

        int row = 0;
        int column = 0;

        skeleton.setWidget(row, column, formHeadingHtml);

        row++;

        skeleton.setWidget(row, column, findDocumentsBrowseForm);

    }

    @Override
    public boolean isButtonsContractSupported() {
        return false;
    }

    @Override
    public String getFormCaption() {
        return CONSTANTS.formCaption();
    }

    @Override
    public void defaultFocus() {
        findDocumentsBrowseForm.defaultFocus();
    }

    @Override
    public boolean validate(MessageContainer container) {
        boolean valid = true;
        return valid;
    }

    @Override
    protected void setFormValue(Void value) {
        findDocumentsBrowseForm.refresh();
    }

    @Override
    public void getValue(Void value) {

    }

    @Override
    public Void newValue() {
        return null;
    }

    @Override
    public HasClickHandlers getUploadDocumentsClickSrc() {
        return findDocumentsBrowseForm.getUploadDocumentsClickSrc();
    }

    @Override
    public HasValueClickHandlers<Long> getConvertDocumentClickSrc() {
        return findDocumentsBrowseForm.getConvertDocumentClickSrc();
    }

}
