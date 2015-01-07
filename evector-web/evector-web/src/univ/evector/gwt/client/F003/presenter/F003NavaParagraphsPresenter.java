package univ.evector.gwt.client.F003.presenter;

import lt.jmsys.spark.gwt.application.client.common.v2.presenter.BaseFormPresenter;
import lt.jmsys.spark.gwt.application.client.common.v2.view.FormDisplay;
import lt.jmsys.spark.gwt.application.common.client.callback.CommonProgressShowingCallback;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickEvent;
import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickHandler;
import lt.jmsys.spark.gwt.client.ui.message.MessageContainer;
import univ.evector.beans.book.Book;
import univ.evector.beans.book.helper.BookHelper;
import univ.evector.gwt.client.service.NavaParagraphService;
import univ.evector.gwt.client.service.NavaParagraphServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.gwt.application.client.security.FormPrivileges;

public class F003NavaParagraphsPresenter extends BaseFormPresenter<Book, F003Place> {

    private NavaParagraphServiceAsync navaParagraphService = GWT.create(NavaParagraphService.class);
    private Book book;

    public interface F003NavaParagraphsDisplay extends FormDisplay<Book> {

        void addUpdateNavaParagraphValueClickHandler(ValueClickHandler<Long> handler);

    }

    public F003NavaParagraphsPresenter(F003NavaParagraphsDisplay view) {
        super(view);
        setLocalEventHandlers();
    }

    private void setLocalEventHandlers() {
        getView().addUpdateNavaParagraphValueClickHandler(new ValueClickHandler<Long>() {

            @Override
            public void onClick(ValueClickEvent<Long> event) {
                if (event != null) {
                    updateBookNavaParagraph(event.getValue());
                }
            }
        });
    }

    private void updateBookNavaParagraph(final Long prgId) {
        CommonProgressShowingCallback<Void> cb = new CommonProgressShowingCallback<Void>(getMessageContainer()) {

            @Override
            protected void call() {
                navaParagraphService.updateBookNavaParagraph(prgId, this);
            }

            @Override
            public void onSuccess(Void result) {
                super.onSuccess(result);
            }

        };
        cb.perform();
    }

    @Override
    public F003NavaParagraphsDisplay getView() {
        return (F003NavaParagraphsDisplay) super.getView();
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
