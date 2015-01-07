package eu.itreegroup.spark.application.shared.security;

public enum SparkDocumentFunction implements DocumentFunction {
    VIEW, EDIT;

    @Override
    public String getCode() {
        return name();
    }
}
