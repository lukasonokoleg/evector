package univ.evector.beans.emotion;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class EmotionModel implements Serializable {

    private Long emm_id;

    private String emm_name;

    private List<Emotion> emotions;

    public EmotionModel() {

    }

    public Long getEmm_id() {
        return emm_id;
    }

    public void setEmm_id(Long emm_id) {
        this.emm_id = emm_id;
    }

    public String getEmm_name() {
        return emm_name;
    }

    public void setEmm_name(String emm_name) {
        this.emm_name = emm_name;
    }

    public List<Emotion> getEmotions() {
        return emotions;
    }

    public void setEmotions(List<Emotion> emotions) {
        this.emotions = emotions;
    }

    @Override
    public String toString() {
        return "EmotionModel [emm_id=" + emm_id + ", emm_name=" + emm_name + ", emotions=" + emotions + "]";
    }

}
