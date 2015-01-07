package univ.evector.gwt.client.F003.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.gwt.client.F003.browse.F003FindParagraphsBrowseForm;
import univ.evector.gwt.client.F003.presenter.F003ParagraphPresenter.F003ParagraphDisplay;

public class F003ParagraphsView extends BaseFormView<Book> implements F003ParagraphDisplay {

    private F003FindParagraphsBrowseForm findParagraphsBrowseForm = new F003FindParagraphsBrowseForm();

    public F003ParagraphsView() {
        getBodyContainer().add(findParagraphsBrowseForm);
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
        boolean valid = true;
        return valid;
    }

    @Override
    protected void setFormValue(Book value) {
        value = value != null ? value : newValue();
        findParagraphsBrowseForm.setBookId(value.getBks_id());

    }

    @Override
    public void getValue(Book value) {

    }

    @Override
    public Book newValue() {
        return new Book();
    }

}