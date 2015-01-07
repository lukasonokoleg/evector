package univ.evector.beans.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import univ.evector.beans.book.helper.SentenceWordHelper;
import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionWord;

public class EmotionHelper {

    public static void organizeEmotionModelEmotionWords(List<Emotion> emotions) {
        if (emotions != null) {
            for (Emotion emotion : emotions) {
                organizeEmotionModelEmotionWords(emotion);
            }
        }
    }

    public static void organizeEmotionModelEmotionWords(Emotion emotion) {
        if (emotion != null) {
            String wordsAsString = emotion.getValue();
            wordsAsString = wordsAsString != null ? wordsAsString : "";
            List<EmotionWord> words = emotion.getWords();
            words = words != null ? words : new ArrayList<EmotionWord>();

            Set<String> newNotNullWordSet = SentenceWordHelper.getWordSet(wordsAsString);
            Set<String> oldNotNullWordSet = EmotionWordHelper.collectNotNullWordSet(words);

            newNotNullWordSet = newNotNullWordSet != null ? newNotNullWordSet : new HashSet<String>();
            oldNotNullWordSet = oldNotNullWordSet != null ? oldNotNullWordSet : new HashSet<String>();

            newNotNullWordSet.removeAll(oldNotNullWordSet);

            for (String wordAsString : newNotNullWordSet) {
                EmotionWord emotionWord = new EmotionWord();

                emotionWord.setEmw_value(wordAsString);

                words.add(emotionWord);
            }
            emotion.setWords(words);
        }
    }
}
