package lt.jmsys.spark.gwt.application.shared.bean;

import eu.itreegroup.spark.application.bean.Classifier;

public class ClassifierBean implements Classifier {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public ClassifierBean() {

    }

    public ClassifierBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getCode() {
        return id;
    }
    
    @Override
    public String getDisplayValue() {
        return name;
    }

}
