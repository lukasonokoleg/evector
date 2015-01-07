package eu.itreegroup.spark.application.bean;

public interface User {

    /**
     * @return User login name.
     */
    String getUserName();

    /**
     * @return First user name.
     */
    String getFirstName();

    /**
     * @return Last user name.
     */
    String getLastName();

    /**
     * @return user email.
     */
    String getEmail();

    /**
     * @return Roles which are obtained by this user.
     */
    Role[] getRoles();

    /**
     * @param role
     * @return Answers the question: "Is specified role obtained by this user."
     */
    boolean hasRole(Role role);

}
