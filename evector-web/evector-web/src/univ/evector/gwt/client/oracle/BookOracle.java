package univ.evector.gwt.client.oracle;

import univ.evector.beans.book.Book;
import univ.evector.beans.suggestion.BookSuggestion;
import univ.evector.gwt.client.service.BookService;
import univ.evector.gwt.client.service.BookServiceAsync;

import com.google.gwt.core.shared.GWT;

public class BookOracle extends EvectorServiceSuggestOracle<Book> {

    private BookServiceAsync bookService = GWT.create(BookService.class);

    public BookOracle() {

    }

    @Override
    protected void callService(Request request, Callback callback) {
        bookService.getBooksList(request.getQuery(), request.getLimit() + 1, asyncResponse(request, callback));
    }

    @Override
    public Book toValue(Suggestion suggestion) {
        Book retVal = null;
        if (suggestion instanceof BookSuggestion) {
            retVal = ((BookSuggestion) suggestion).getBook();
        }
        return retVal;
    }

    @Override
    public Suggestion toSuggestion(Book value) {
        BookSuggestion retVal = new BookSuggestion(value);
        return retVal;
    }

}