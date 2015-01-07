package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Street implements Serializable {

    private Long str_id;

    private String str_name;

    public Street() {

    }

    public Street(Long str_id, String str_name) {
        this.str_id = str_id;
        this.str_name = str_name;
    }

    public Long getStr_id() {
        return str_id;
    }

    public void setStr_id(Long str_id) {
        this.str_id = str_id;
    }

    public String getStr_name() {
        return str_name;
    }

    public void setStr_name(String str_name) {
        this.str_name = str_name;
    }

    @Override
    public String toString() {
        return "Street [str_id=" + str_id + ", str_name=" + str_name + "]";
    }

}