package univ.evector.beans.book.helper;

import univ.evector.beans.book.Book;

public class BookHelper {

    public static boolean hasSameBksIds(Book book1, Book book2) {
        boolean retVal = false;
        if (book1 != null && book2 != null && book1.getBks_id() != null) {
            retVal = book1.getBks_id().equals(book2.getBks_id());
        }
        return retVal;
    }

    public static boolean hasBksId(Book book) {
        boolean retVal = book != null && book.getBks_id() != null;
        return retVal;
    }
}
