package univ.evector.gwt.client.F003.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.beans.book.helper.BookHelper;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F003ParagraphPresenter extends BaseFormPresenter<Book, F003Place> {

    public interface F003ParagraphDisplay extends FormDisplay<Book> {

    }

    private Book book;

    public F003ParagraphPresenter(F003ParagraphDisplay view) {
        super(view);
        setLocalEventHandlers();
    }

    private void setLocalEventHandlers() {

    }

    @Override
    public F003Place getParameters(Place place) {
        return (F003Place) place;
    }

    @Override
    public F003Place getParameters(Book value) {
        return null;
    }

    @Override
    public F003ParagraphDisplay getView() {
        return (F003ParagraphDisplay) super.getView();
    }

    @Override
    protected void findValue(F003Place parameters, AsyncCallback<Book> callback) {
        callback.onSuccess(book);
    }

    @Override
    protected void setValue(Book value, F003Place parameters, FormPrivileges privileges, AsyncCallback<Book> callback) {
        getView().setValue(value);
        callback.onSuccess(value);
    }

    @Override
    protected void attachHandlers(EventBus eventBus) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean validate(MessageContainer container, Book value) {
        boolean valid = true;
        return valid;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean hasBook(Book book) {
        boolean retVal = BookHelper.hasSameBksIds(this.book, book);
        return retVal;
    }

}
