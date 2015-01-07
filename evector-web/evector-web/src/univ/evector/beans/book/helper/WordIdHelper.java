package univ.evector.beans.book.helper;

import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.Word;

public class WordIdHelper {

    public static void ensureHasSntId(List<Sentence> sentences) {
        if (sentences != null) {
            for (Sentence sentence : sentences) {
                ensureHasSntId(sentence);
            }
        }
    }

    public static void ensureHasSntId(Sentence sentence) {
        if (sentence != null && !ConversionHelper.isEmpty(sentence.getWords())) {
            for (Word word : sentence.getWords()) {
                if (word != null) {
                    word.setWrd_snt_id(sentence.getSnt_id());
                }
            }
        }
    }

}
