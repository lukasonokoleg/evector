package univ.evector.beans.book.helper;

import org.apache.tika.metadata.Metadata;

import univ.evector.beans.Author;
import univ.evector.beans.book.Book;
import univ.evector.beans.book.BookMetaData;
import univ.evector.beans.helper.AuthorHelper;

public class BookMetaDataHelper {

    public static String AUTHOR = "author";

    public static String TITLE = "title";

    public static String ISBN = "isbn";

    @SuppressWarnings("deprecation")
    public static BookMetaData getBookMetaData(Metadata metadata) {
        BookMetaData retVal = new BookMetaData();

        String title = metadata.get(Metadata.TITLE);
        String author = metadata.get(Metadata.AUTHOR);
        String isbn = metadata.get(Metadata.IDENTIFIER);

        retVal.addProperty(TITLE, title);
        retVal.addProperty(AUTHOR, author);
        retVal.addProperty(ISBN, isbn);

        return retVal;
    }

    public static void setBookMetaData(Book book, Metadata metadata) {
        String title = metadata.get(Metadata.TITLE);
        String authorAsString = metadata.get(Metadata.AUTHOR);
        String isbn = metadata.get(Metadata.IDENTIFIER);

        Author author = AuthorHelper.newInstance(authorAsString);

        book.setBks_title(title);
        book.setBks_isbn(isbn);
        book.setAuthor(author);
    }

}