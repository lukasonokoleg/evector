package univ.evector.beans.browse;

import java.io.Serializable;
import java.util.List;

import univ.evector.beans.emotion.EmotionWord;

@SuppressWarnings("serial")
public class FindUserEmotions implements Serializable {

    private Long emo_id;

    private String emo_name;

    private List<EmotionWord> words;

    public FindUserEmotions() {

    }

    public Long getEmo_id() {
        return emo_id;
    }

    public void setEmo_id(Long emo_id) {
        this.emo_id = emo_id;
    }

    public String getEmo_name() {
        return emo_name;
    }

    public void setEmo_name(String emo_name) {
        this.emo_name = emo_name;
    }

    public List<EmotionWord> getWords() {
        return words;
    }

    public void setWords(List<EmotionWord> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "FindUserEmotions [emo_id=" + emo_id + ", emo_name=" + emo_name + ", words=" + words + "]";
    }

}