package univ.evector.beans.book;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Paragraph implements Serializable {

    private Long prg_id;

    private Long prg_bks_id;

    private Long prg_seq;

    private String prg_value;

    private List<Sentence> sentences;

    public Paragraph() {

    }

    public Long getPrg_bks_id() {
        return prg_bks_id;
    }

    public void setPrg_bks_id(Long prg_bks_id) {
        this.prg_bks_id = prg_bks_id;
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

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    @Override
    public String toString() {
        return "Paragraph [prg_id=" + prg_id + ", prg_bks_id=" + prg_bks_id + ", prg_seq=" + prg_seq + ", prg_value=" + prg_value + ", sentences=" + sentences + "]";
    }

}
