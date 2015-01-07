package univ.evector.beans.book.helper;

import univ.evector.beans.book.Book;
import univ.evector.beans.book.Paragraph;

public class ParagraphIdHelper {

    public static void ensureParagraphHasBksId(Book book) {
        if (book != null && book.getParagraphs() != null) {
            for (Paragraph paragraph : book.getParagraphs()) {
                paragraph.setPrg_bks_id(book.getBks_id());
            }
        }
    }
}
