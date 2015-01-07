package univ.evector.beans.helper;

import lt.jmsys.spark.gwt.application.shared.helper.AppendHelper;
import univ.evector.beans.book.Book;

public class BookSuggestionHelper {

    public static String getDisplayString(Book invoice) {
        return asString(invoice);
    }

    public static String getReplacementString(Book invoice) {
        return asString(invoice);
    }

    public static String getPlainTextDisplayString(Book invoice) {
        return asString(invoice);
    }

    private static String asString(Book book) {
        StringBuilder builder = new StringBuilder();

        Long bksId = null;

        String bksIdAsString = null;
        String bksTitle = null;

        if (book != null) {
            bksId = book.getBks_id();
            bksTitle = book.getBks_title();

            if (bksId != null) {
                bksIdAsString = String.valueOf(bksId);
            }
        }

        AppendHelper.append(builder, bksIdAsString);
        AppendHelper.append(builder, bksTitle, " - ");

        return builder.toString();
    }

}
