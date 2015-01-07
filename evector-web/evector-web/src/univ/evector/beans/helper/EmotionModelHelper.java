package univ.evector.beans.helper;

import java.util.List;

import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionModel;

public class EmotionModelHelper {

    public static void organizeEmotionModelWords(EmotionModel model) {
        if (model != null) {
            List<Emotion> emotions = model.getEmotions();
            EmotionHelper.organizeEmotionModelEmotionWords(emotions);
        }
    }

}