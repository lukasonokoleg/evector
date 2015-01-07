package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Setting implements Serializable {

    private Long set_id;
    private String set_guid;
    private String set_name;
    private String set_value;
    private String set_type;

    public Setting() {

    }

    public Long getSet_id() {
        return set_id;
    }

    public void setSet_id(Long set_id) {
        this.set_id = set_id;
    }

    public String getSet_guid() {
        return set_guid;
    }

    public void setSet_guid(String set_guid) {
        this.set_guid = set_guid;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getSet_type() {
        return set_type;
    }

    public void setSet_type(String set_type) {
        this.set_type = set_type;
    }

    public String getSet_value() {
        return set_value;
    }

    public void setSet_value(String set_value) {
        this.set_value = set_value;
    }

    @Override
    public String toString() {
        return "Setting [set_id=" + set_id + ", set_guid=" + set_guid + ", set_name=" + set_name + ", set_type=" + set_type + "]";
    }

}