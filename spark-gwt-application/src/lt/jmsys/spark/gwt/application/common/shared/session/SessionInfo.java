package lt.jmsys.spark.gwt.application.common.shared.session;

import java.io.Serializable;

import lt.jmsys.spark.gwt.application.shared.security.Role;

@SuppressWarnings("serial")
public class SessionInfo implements Serializable {

    private boolean keepFresh;
    private Double timeout;
    private Double userId;
    private Double dbSessionid;
    private String userName;
    private String firstName;
    private String lastName;

    private Double companyId;
    private String companyName;
    private String[] roles;
    private String language;
    private String notificationId;

    public SessionInfo() {

    }

    public Double getUserId() {
        return userId;
    }

    public void setUserId(Double userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Double companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public boolean isKeepFresh() {
        return keepFresh;
    }

    public void setKeepFresh(boolean keepFresh) {
        this.keepFresh = keepFresh;
    }

    public Double getTimeout() {
        return timeout;
    }

    public void setTimeout(Double timeout) {
        this.timeout = timeout;
    }

    public boolean hasRole(String role) {
        if (null != roles) {
            for (String r : roles) {
                if (role.equals(r)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasRole(Role... role) {
        String[] roles = getRoles();
        if (null != roles) {
            for (String c : roles) {
                for (Role r : role) {
                    if (r.getCode().equals(c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public Double getDbSessionid() {
        return dbSessionid;
    }

    public void setDbSessionid(Double dbSessionid) {
        this.dbSessionid = dbSessionid;
    }

}
