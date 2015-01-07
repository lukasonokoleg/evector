package univ.evector.beans.book;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Sentence implements Serializable {

    private Long snt_id;

    private Long snt_prg_id;

    private Long snt_seq;

    private String snt_value;

    private List<Word> words;

    public Sentence() {

    }

    public Long getSnt_prg_id() {
        return snt_prg_id;
    }

    public void setSnt_prg_id(Long snt_prg_id) {
        this.snt_prg_id = snt_prg_id;
    }

    public Long getSnt_id() {
        return snt_id;
    }

    public void setSnt_id(Long snt_id) {
        this.snt_id = snt_id;
    }

    public Long getSnt_seq() {
        return snt_seq;
    }

    public void setSnt_seq(Long snt_seq) {
        this.snt_seq = snt_seq;
    }

    public String getSnt_value() {
        return snt_value;
    }

    public void setSnt_value(String snt_value) {
        this.snt_value = snt_value;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "Sentence [snt_id=" + snt_id + ", snt_prg_id=" + snt_prg_id + ", snt_seq=" + snt_seq + ", snt_value=" + snt_value + ", words=" + words + "]";
    }

}