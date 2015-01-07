package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Country implements Serializable {

    private String cnt_iso_code;

    private String cnt_name;

    public Country() {

    }

    public Country(String cnt_iso_code, String cnt_name) {
        this.cnt_iso_code = cnt_iso_code;
        this.cnt_name = cnt_name;
    }

    public String getCnt_iso_code() {
        return cnt_iso_code;
    }

    public void setCnt_iso_code(String cnt_iso_code) {
        this.cnt_iso_code = cnt_iso_code;
    }

    public String getCnt_name() {
        return cnt_name;
    }

    public void setCnt_name(String cnt_name) {
        this.cnt_name = cnt_name;
    }

}
