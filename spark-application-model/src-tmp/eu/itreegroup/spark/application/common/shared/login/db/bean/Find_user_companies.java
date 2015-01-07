package eu.itreegroup.spark.application.common.shared.login.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Find_user_companies implements Serializable {

    private final static long serialVersionUID = 1;

    private Double com_id;

    private String com_name;

    private String com_type;

    public Find_user_companies() {
    }

    public Find_user_companies(Double com_id, String com_name, String com_type) {
        this.com_id = com_id;
        this.com_name = com_name;
        this.com_type = com_type;
    }

    public Find_user_companies(Find_user_companies src) {
        if (src == null)
            return;

        this.com_id = src.getCom_id();
        this.com_name = src.getCom_name();
        this.com_type = src.getCom_type();
    }

    public Double getCom_id() {
        return this.com_id;
    }

    public void setCom_id(Double com_id) {
        this.com_id = com_id;
    }

    public String getCom_name() {
        return this.com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getCom_type() {
        return this.com_type;
    }

    public void setCom_type(String com_type) {
        this.com_type = com_type;
    }

    @Override
    public String toString() {
        return Find_user_companies.class.getName() + "@[" + "com_id = " + com_id + ", com_name = " + com_name + ", com_type = " + com_type + "]";
    }

}
