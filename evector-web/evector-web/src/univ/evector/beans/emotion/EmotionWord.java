package univ.evector.beans.emotion;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EmotionWord implements Serializable {

    private Long emw_id;

    private String emw_value;

    public EmotionWord() {

    }

    public Long getEmw_id() {
        return emw_id;
    }

    public void setEmw_id(Long emw_id) {
        this.emw_id = emw_id;
    }

    public String getEmw_value() {
        return emw_value;
    }

    public void setEmw_value(String emw_value) {
        this.emw_value = emw_value;
    }

    @Override
    public String toString() {
        return "EmotionWord [emw_id=" + emw_id + ", emw_value=" + emw_value + "]";
    }

}