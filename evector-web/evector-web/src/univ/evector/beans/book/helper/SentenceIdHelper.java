package univ.evector.beans.book.helper;

import java.util.List;

import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;

public class SentenceIdHelper {

    public static void ensureHasPrgId(List<Paragraph> paragraphs) {
        if (paragraphs != null) {
            for (Paragraph paragraph : paragraphs) {
                ensureHasPrgId(paragraph);
            }
        }
    }

    public static void ensureHasPrgId(Paragraph paragraph) {
        if (paragraph != null && paragraph.getSentences() != null) {
            for (Sentence sentence : paragraph.getSentences()) {
                sentence.setSnt_prg_id(paragraph.getPrg_id());
            }
        }
    }

}
