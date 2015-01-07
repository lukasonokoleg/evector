package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Residence implements Serializable {

    private Long rsd_id;

    private String rsd_name;

    private Boolean rsd_has_streets;

    public Residence() {

    }

    public Residence(Long rsd_id, String rsd_name) {
        this.rsd_id = rsd_id;
        this.rsd_name = rsd_name;
    }

    public Long getRsd_id() {
        return rsd_id;
    }

    public void setRsd_id(Long rsd_id) {
        this.rsd_id = rsd_id;
    }

    public String getRsd_name() {
        return rsd_name;
    }

    public void setRsd_name(String rsd_name) {
        this.rsd_name = rsd_name;
    }

    public Boolean getRsd_has_streets() {
        return rsd_has_streets;
    }

    public void setRsd_has_streets(Boolean rsd_has_streets) {
        this.rsd_has_streets = rsd_has_streets;
    }

    @Override
    public String toString() {
        return "Residence [rsd_id=" + rsd_id + ", rsd_name=" + rsd_name + ", rsd_has_streets=" + rsd_has_streets + "]";
    }

}
