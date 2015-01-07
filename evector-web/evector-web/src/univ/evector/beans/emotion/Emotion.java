package univ.evector.beans.emotion;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Emotion implements Serializable {

    private Long emo_id;

    private String emo_name;

    private String value;

    private List<EmotionWord> words;

    public Emotion() {

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Emotion [emo_id=" + emo_id + ", emo_name=" + emo_name + ", value=" + value + ", words=" + words + "]";
    }

}
