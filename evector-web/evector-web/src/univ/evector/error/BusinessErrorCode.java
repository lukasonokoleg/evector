package univ.evector.error;

import eu.itreegroup.spark.application.error.ErrorCode;

public enum BusinessErrorCode implements ErrorCode {

    EMAIL_VALIDATION_TOKEN_MISMATCH,

    ERROR_BUSINESS1,

    ERROR_BUSINESS2,

    WARNING_BUSINESS1,
    
    ERR_PASSWORD_STRENGTH_IS_LOW;

    public String asString() {
        return name();
    }

}
