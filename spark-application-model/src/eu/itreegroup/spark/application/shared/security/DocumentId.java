package eu.itreegroup.spark.application.shared.security;

import java.io.Serializable;

public interface DocumentId extends Serializable {

    // Oleg Lukašonok -> šitoje vietoje tai gal geriau būtų padaryti IdWrapper -> generika interface'a.

    boolean isIdAsLong();

    boolean isIdAsString();

    Long getIdAsLong();

    String getIdAsString();

}