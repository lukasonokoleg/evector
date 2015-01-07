package univ.evector.beans.book;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Word implements Serializable {

    private Long wrd_id;

    private Long wrd_snt_id;

    private Long wrd_seq;

    private Long wrd_gwrd_id;

    private String wrd_value;

    public Word() {

    }

    public Long getWrd_id() {
        return wrd_id;
    }

    public void setWrd_id(Long wrd_id) {
        this.wrd_id = wrd_id;
    }

    public Long getWrd_snt_id() {
        return wrd_snt_id;
    }

    public void setWrd_snt_id(Long wrd_snt_id) {
        this.wrd_snt_id = wrd_snt_id;
    }

    public Long getWrd_seq() {
        return wrd_seq;
    }

    public void setWrd_seq(Long wrd_seq) {
        this.wrd_seq = wrd_seq;
    }

    public String getWrd_value() {
        return wrd_value;
    }

    public void setWrd_value(String wrd_value) {
        this.wrd_value = wrd_value;
    }

    public Long getWrd_gwrd_id() {
        return wrd_gwrd_id;
    }

    public void setWrd_gwrd_id(Long wrd_gwrd_id) {
        this.wrd_gwrd_id = wrd_gwrd_id;
    }

    @Override
    public String toString() {
        return "Word [wrd_id=" + wrd_id + ", wrd_snt_id=" + wrd_snt_id + ", wrd_seq=" + wrd_seq + ", wrd_gwrd_id=" + wrd_gwrd_id + ", wrd_value=" + wrd_value + "]";
    }

}
