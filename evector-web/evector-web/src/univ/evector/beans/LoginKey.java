package univ.evector.beans;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class LoginKey implements Serializable {

    private Long lgk_id;

    private String lgk_key;

    private String lgk_workplace;

    private Date lgk_created;
    private Date lgk_updated;
    private Date lgk_expired;

    private User user;

    public LoginKey() {

    }

    public Long getLgk_id() {
        return lgk_id;
    }

    public void setLgk_id(Long lgk_id) {
        this.lgk_id = lgk_id;
    }

    public String getLgk_key() {
        return lgk_key;
    }

    public void setLgk_key(String lgk_key) {
        this.lgk_key = lgk_key;
    }

    public String getLgk_workplace() {
        return lgk_workplace;
    }

    public void setLgk_workplace(String lgk_workplace) {
        this.lgk_workplace = lgk_workplace;
    }

    public Date getLgk_created() {
        return lgk_created;
    }

    public void setLgk_created(Date lgk_created) {
        this.lgk_created = lgk_created;
    }

    public Date getLgk_updated() {
        return lgk_updated;
    }

    public void setLgk_updated(Date lgk_updated) {
        this.lgk_updated = lgk_updated;
    }

    public Date getLgk_expired() {
        return lgk_expired;
    }

    public void setLgk_expired(Date lgk_expired) {
        this.lgk_expired = lgk_expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginKey [lgk_id=" + lgk_id + ", lgk_key=" + lgk_key + ", lgk_workplace=" + lgk_workplace + ", lgk_created=" + lgk_created + ", lgk_updated=" + lgk_updated
                + ", lgk_expired=" + lgk_expired + ", user=" + user + "]";
    }

}