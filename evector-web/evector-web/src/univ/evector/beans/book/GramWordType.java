package univ.evector.beans.book;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GramWordType implements Serializable {

    private Long gwt_id;

    private String gwt_value;

    public GramWordType() {

    }

    public Long getGwt_id() {
        return gwt_id;
    }

    public void setGwt_id(Long gwt_id) {
        this.gwt_id = gwt_id;
    }

    public String getGwt_value() {
        return gwt_value;
    }

    public void setGwt_value(String gwt_value) {
        this.gwt_value = gwt_value;
    }

    @Override
    public String toString() {
        return "GramWordType [gwt_id=" + gwt_id + ", gwt_value=" + gwt_value + "]";
    }

}