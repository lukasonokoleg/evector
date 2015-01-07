package eu.itreegroup.spark.application.error;

public enum SparkLoginErrorCode implements LoginErrorCode {
    LOGIN_ERROR_SESSION_EXPIRED,

    LOGIN_ERROR_WRONG_USERNAME_PASSWORD,

    LOGIN_ERROR_USER_LOCKED,

    LOGIN_ERROR_PASSWORD_EXPIRED,
    
    LOGIN_ERROR_NOT_AUTHENTICATED;

    public String asString() {
        return name();
    }

}
