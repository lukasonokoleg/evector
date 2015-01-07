package univ.evector.beans.browse;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FindEmotionalParagraphs implements Serializable {

    private Long prg_id;

    private Long prg_seq;

    private String prg_value;

    private String emotional_value;

    public FindEmotionalParagraphs() {

    }

    public Long getPrg_id() {
        return prg_id;
    }

    public void setPrg_id(Long prg_id) {
        this.prg_id = prg_id;
    }

    public Long getPrg_seq() {
        return prg_seq;
    }

    public void setPrg_seq(Long prg_seq) {
        this.prg_seq = prg_seq;
    }

    public String getPrg_value() {
        return prg_value;
    }

    public void setPrg_value(String prg_value) {
        this.prg_value = prg_value;
    }

    public String getEmotional_value() {
        return emotional_value;
    }

    public void setEmotional_value(String emotional_value) {
        this.emotional_value = emotional_value;
    }

    @Override
    public String toString() {
        return "FindEmotionalParagraphs [prg_id=" + prg_id + ", prg_seq=" + prg_seq + ", prg_value=" + prg_value + ", emotional_value=" + emotional_value + "]";
    }

}