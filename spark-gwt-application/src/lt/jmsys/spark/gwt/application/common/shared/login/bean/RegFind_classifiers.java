package lt.jmsys.spark.gwt.application.common.shared.login.bean;

import java.io.Serializable;


public class RegFind_classifiers implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String domain;
    
    private String value_code;

    private String high_value;

    private String value_description;

    public RegFind_classifiers() {
    }

    public RegFind_classifiers(String domain, String classifier_code, String value_code, String high_value, String value_description) {
        this.domain = domain;
        this.value_code = value_code;
        this.high_value = high_value;
        this.value_description = value_description;
    }

    public String getDomain(){
        return this.domain;
    }
    
    public void setDomain(String domain){
        this.domain = domain;
    }
    
    public String getValue_code() {
        return this.value_code;
    }

    public void setValue_code(String value_code) {
        this.value_code = value_code;
    }

    public String getHigh_value() {
        return this.high_value;
    }

    public void setHigh_value(String high_value) {
        this.high_value = high_value;
    }

    public String getValue_description() {
        return this.value_description;
    }

    public void setValue_description(String value_description) {
        this.value_description = value_description;
    }

    @Override
    public String toString() {
        return RegFind_classifiers.class.getName() + "@[" + ", domain = " + domain + ", value_code = "+ value_code + ", high_value = " + high_value + ", value_description = " + value_description + "]";
    }

}
