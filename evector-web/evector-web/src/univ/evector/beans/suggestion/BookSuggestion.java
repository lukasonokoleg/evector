package univ.evector.beans.suggestion;

import lt.jmsys.spark.gwt.client.suggest.oracle.ServiceSuggestOracle.AbstractHighlightedSuggestion;
import univ.evector.beans.book.Book;
import univ.evector.beans.helper.BookSuggestionHelper;

public class BookSuggestion extends AbstractHighlightedSuggestion {

    private Book book;

    public BookSuggestion(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String getDisplayString() {
        return BookSuggestionHelper.getDisplayString(book);
    }

    @Override
    public String getReplacementString() {
        return BookSuggestionHelper.getReplacementString(book);
    }

    @Override
    public String getPlainTextDisplayString() {
        return BookSuggestionHelper.getPlainTextDisplayString(book);
    }

}
