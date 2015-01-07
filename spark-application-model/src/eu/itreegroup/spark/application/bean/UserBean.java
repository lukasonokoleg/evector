package eu.itreegroup.spark.application.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserBean implements Serializable, User {

    private Long userId;

    private String userName;
    private String firstName;
    private String lastName;

    private String email;

    private Role[] roles;

    public UserBean() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public Role[] getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean hasRole(Role role) {
        boolean retVal = false;
        Role[] roles = getRoles();
        if (roles != null) {
            for (Role r : roles) {
                if (r.equals(role)) {
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

}
