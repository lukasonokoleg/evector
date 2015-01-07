package univ.evector.beans.privileges;

import eu.itreegroup.spark.application.shared.security.DocumentFunction;

public enum EvectorDocumentFunction implements DocumentFunction {

    NEW, EDIT, VIEW, DELETE;

    @Override
    public String getCode() {
        return name();
    }

}