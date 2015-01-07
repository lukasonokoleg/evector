package eu.itreegroup.spark.application.bean;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserSessionBean implements UserSession, Serializable {

    private String key;
    private String ip;

    private Date accessed;
    private Date created;
    private Long timeout;

    private User user;

    private Long companyId;
    private Long companyName;

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Long companyName) {
        this.companyName = companyName;
    }

    public void setAccessed(Date accessed) {
        this.accessed = accessed;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Date getAccessed() {
        return accessed;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public User getUser() {
        return user;
    }

}
