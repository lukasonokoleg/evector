package univ.evector.beans.book.helper;

import java.util.ArrayList;
import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;

public class ParagraphSentenceHelper {

    public static void splitParagraphValueIntoSentence(List<Paragraph> paragraphs) {
        if (paragraphs != null) {
            for (Paragraph paragraph : paragraphs) {
                splitParagraphValueIntoSentence(paragraph);
            }
        }
    }

    public static void splitParagraphValueIntoSentence(Paragraph paragraph) {
        if (paragraph != null && !ConversionHelper.isEmpty(paragraph.getPrg_value())) {
            List<Sentence> sentences = new ArrayList<Sentence>();
            List<String> sentencesAsString = SentenceHelper.getSentences(paragraph.getPrg_value());
            if (sentencesAsString != null) {
                Long seq = 0L;
                for (String sentenceAsString : sentencesAsString) {
                    if (!ConversionHelper.isEmpty(sentenceAsString)) {
                        seq++;
                        Sentence sentence = new Sentence();
                        sentence.setSnt_prg_id(paragraph.getPrg_id());
                        sentence.setSnt_seq(seq);
                        sentence.setSnt_value(sentenceAsString);
                        sentences.add(sentence);
                    }
                }
            }
            paragraph.setSentences(sentences);
        }
    }
}
