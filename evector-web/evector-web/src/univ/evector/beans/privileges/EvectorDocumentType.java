package univ.evector.beans.privileges;

import eu.itreegroup.spark.application.shared.security.DocumentType;

public enum EvectorDocumentType implements DocumentType {

    MESSAGE, BOOK, USER;

    @Override
    public String getCode() {
        return name();
    }

}
