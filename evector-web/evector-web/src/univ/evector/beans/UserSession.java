package univ.evector.beans;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserSession implements Serializable, eu.itreegroup.spark.application.bean.UserSession {

    public static Long NO_SESSION_ID = 0L;

    private Long ses_id;

    private String ses_key;

    private String ses_ip;

    private Date ses_accessed;

    private Date ses_created;

    private Date ses_logout;

    private User user;

    public UserSession() {

    }

    public Long getSes_id() {
        return ses_id;
    }

    public void setSes_id(Long ses_id) {
        this.ses_id = ses_id;
    }

    public String getSes_key() {
        return ses_key;
    }

    public void setSes_key(String ses_key) {
        this.ses_key = ses_key;
    }

    public String getSes_ip() {
        return ses_ip;
    }

    public void setSes_ip(String ses_ip) {
        this.ses_ip = ses_ip;
    }

    public Date getSes_accessed() {
        return ses_accessed;
    }

    public void setSes_accessed(Date ses_accessed) {
        this.ses_accessed = ses_accessed;
    }

    public Date getSes_created() {
        return ses_created;
    }

    public void setSes_created(Date ses_created) {
        this.ses_created = ses_created;
    }

    public Date getSes_logout() {
        return ses_logout;
    }

    public void setSes_logout(Date ses_logout) {
        this.ses_logout = ses_logout;
    }

    @Override
    public String toString() {
        return "UserSession [ses_id=" + ses_id + ", ses_key=" + ses_key + ", ses_ip=" + ses_ip + ", ses_accessed=" + ses_accessed + ", ses_created=" + ses_created
                + ", ses_logout=" + ses_logout + "]";
    }

    @Override
    public String getKey() {
        return getSes_key();
    }

    @Override
    public Long getTimeout() {
        return Long.MAX_VALUE;
    }

    @Override
    public Date getAccessed() {
        return getSes_accessed();
    }

    @Override
    public Date getCreated() {
        return getSes_created();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

}
