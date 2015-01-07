package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;

import eu.itreegroup.spark.application.bean.Classifier;

public class Als_role_ot implements Classifier, Serializable {

    private final static long serialVersionUID = 1;

    private String name;

    private String display_name;

    private Double company_id;

    private transient String code;// dummy
    private transient String displayValue;// dummy

    public Als_role_ot() {
    }

    public Als_role_ot(String name, String display_name, Double company_id) {
        this.name = name;
        this.display_name = display_name;
        this.company_id = company_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return this.display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Double getCompany_id() {
        return this.company_id;
    }

    public void setCompany_id(Double company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return Als_role_ot.class.getName() + "@[" + "name = " + name + ", display_name = " + display_name + ", company_id = " + company_id + "]";
    }

    @Override
    public String getCode() {
        return this.name;
    }

    @Override
    public String getDisplayValue() {
        return this.display_name;
    }

}
