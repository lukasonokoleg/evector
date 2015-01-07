package univ.evector.gwt.client.F003.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickHandler;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.gwt.client.F003.browse.F003FindNavaParagraphsBrowseForm;
import univ.evector.gwt.client.F003.presenter.F003NavaParagraphsPresenter.F003NavaParagraphsDisplay;

public class F003NavaParagraphsView extends BaseFormView<Book> implements F003NavaParagraphsDisplay {

    private F003FindNavaParagraphsBrowseForm findNavaParagraphsBrowseForm = new F003FindNavaParagraphsBrowseForm();

    public F003NavaParagraphsView() {
        getBodyContainer().add(findNavaParagraphsBrowseForm);
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
        findNavaParagraphsBrowseForm.setBookId(value.getBks_id());
    }

    @Override
    public void getValue(Book value) {
        // TODO Auto-generated method stub
    }

    @Override
    public Book newValue() {
        return new Book();
    }

    @Override
    public void addUpdateNavaParagraphValueClickHandler(ValueClickHandler<Long> handler) {
        findNavaParagraphsBrowseForm.addValueClickHandler(handler);
    }

}
