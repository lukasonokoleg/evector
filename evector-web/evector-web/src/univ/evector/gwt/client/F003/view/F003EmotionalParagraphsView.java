package univ.evector.gwt.client.F003.view;

import lt.jmsys.spark.gwt.application.client.common.v2.view.BaseFormView;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickHandler;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.gwt.client.F003.browse.F003FindEmotionalParagraphsBrowseForm;

public class F003EmotionalParagraphsView extends BaseFormView<Book> {

    private F003FindEmotionalParagraphsBrowseForm findEmotionalParagraphsBrowseForm = new F003FindEmotionalParagraphsBrowseForm();

    public F003EmotionalParagraphsView() {
        getBodyContainer().add(findEmotionalParagraphsBrowseForm);
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
        findEmotionalParagraphsBrowseForm.setBookId(value.getBks_id());
    }

    @Override
    public void getValue(Book value) {

    }

    @Override
    public Book newValue() {
        return new Book();
    }

    public void addValueClickHandler(ValueClickHandler<Long> handler) {
        findEmotionalParagraphsBrowseForm.addValueClickHandler(handler);
    }

}
