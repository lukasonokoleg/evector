package eu.itreegroup.spark.application.bean;

import java.io.Serializable;


public interface Classifier extends Serializable{
    String getCode();
    String getDisplayValue();
}
