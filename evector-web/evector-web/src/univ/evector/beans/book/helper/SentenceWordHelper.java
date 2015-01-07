package univ.evector.beans.book.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.Word;

public class SentenceWordHelper {

    public static void splitSentenceIntoWords(List<Sentence> sentences) {
        if (sentences != null) {
            for (Sentence sentence : sentences) {
                splitSentenceIntoWords(sentence);
            }
        }
    }

    public static void splitSentenceIntoWords(Sentence sentence) {
        if (sentence != null && !ConversionHelper.isEmpty(sentence.getSnt_value())) {
            List<Word> words = new ArrayList<Word>();
            List<String> wordsAsString = WordHelper.getWords(sentence.getSnt_value());
            if (!ConversionHelper.isEmpty(wordsAsString)) {
                Long seq = 0l;

                for (String wordAsString : wordsAsString) {
                    if (!ConversionHelper.isEmpty(wordAsString)) {
                        seq++;
                        Word word = new Word();

                        word.setWrd_seq(seq);
                        word.setWrd_snt_id(sentence.getSnt_id());
                        word.setWrd_value(wordAsString);

                        words.add(word);
                    }
                }
            }
            sentence.setWords(words);
        }
    }

    public static Set<String> getWordSet(String wordsAsString) {
        Set<String> retVal = new HashSet<>();
        if (!ConversionHelper.isEmpty(wordsAsString)) {
            List<String> sentences = SentenceHelper.getSentences(wordsAsString);
            for (String sentence : sentences) {
                List<String> words = WordHelper.getWords(sentence);
                if (words != null) {
                    retVal.addAll(words);
                }
            }
        }
        return retVal;
    }

}
