package eu.itreegroup.spark.application.bean;

import java.util.Date;

public interface UserSession {

    /**
     * @return Session key.
     */
    String getKey();

    /**
     * 
     * @return Session timeout in milliseconds
     */
    Long getTimeout();

    /**
     * @return Last session access date.
     */
    Date getAccessed();

    /**
     * @return Session creation date.
     */
    Date getCreated();

    /*    Long getUserId();

        String getUserName();
    */

    /**
     * @return User who is authenticated within this session.
     */
    User getUser();

    /*    Long getPersonId();

        String getPersonName();

        Long getCompanyId();

        Long getCompanyName();
    */
    
  /*  Role[] getRoles();

    boolean isUserInRole(Role role);*/

}
