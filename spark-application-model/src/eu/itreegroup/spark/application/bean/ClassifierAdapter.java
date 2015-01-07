package eu.itreegroup.spark.application.bean;

public interface ClassifierAdapter<T> {

    String getCode(T value);

    String getDisplayValue(T value);
}
