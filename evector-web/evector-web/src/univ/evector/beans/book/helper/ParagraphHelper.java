package univ.evector.beans.book.helper;

import univ.evector.beans.book.Paragraph;

public class ParagraphHelper {

    public static void cleanGwrdIds(Paragraph paragraph) {
        if (paragraph != null) {
            SentenceHelper.cleanGwrdIds(paragraph.getSentences());
        }
    }

    public static boolean hasPrgId(Paragraph paragraph) {
        boolean retVal = paragraph != null && paragraph.getPrg_id() != null;
        return retVal;
    }
}
