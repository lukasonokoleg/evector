package eu.itreegroup.spark.application.bean;

public interface Adapter<T, C> {

    C adapt(T value);
}
