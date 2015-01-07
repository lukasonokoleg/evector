package univ.evector.beans.browse;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FindNavaParagraphs implements Serializable {

    private Long prg_id;

    private Long prg_seq;

    private String prg_value;

    private String nava_value;

    public FindNavaParagraphs() {

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

    public String getNava_value() {
        return nava_value;
    }

    public void setNava_value(String nava_value) {
        this.nava_value = nava_value;
    }

    @Override
    public String toString() {
        return "FindNavaParagraphs [prg_id=" + prg_id + ", prg_seq=" + prg_seq + ", prg_value=" + prg_value + ", nava_value=" + nava_value + "]";
    }

}