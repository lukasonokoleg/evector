package eu.itreegroup.spark.application.bean;

public enum SparkLogoutCause implements LogoutCause {

    CLOSED_BY_USER("U"), // U - closed by (U)ser

    CLOSED_BY_ADMINISTRATOR("A"), // (A)dministrator

    EXPIRED("X"), // e(X)pired

    ERROR("E");// (E)rror

    final String cause;

    private SparkLogoutCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String asString() {
        return cause;
    }

}
