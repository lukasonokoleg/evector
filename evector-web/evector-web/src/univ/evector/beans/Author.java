package univ.evector.beans;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Author implements Serializable {

    private Long auth_id;

    private String auth_first_name;
    private String auth_last_name;
    private Date auth_birth_date;

    public Author() {

    }

    public Long getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(Long auth_id) {
        this.auth_id = auth_id;
    }

    public String getAuth_first_name() {
        return auth_first_name;
    }

    public void setAuth_first_name(String auth_first_name) {
        this.auth_first_name = auth_first_name;
    }

    public String getAuth_last_name() {
        return auth_last_name;
    }

    public void setAuth_last_name(String auth_last_name) {
        this.auth_last_name = auth_last_name;
    }

    public Date getAuth_birth_date() {
        return auth_birth_date;
    }

    public void setAuth_birth_date(Date auth_birth_date) {
        this.auth_birth_date = auth_birth_date;
    }

    @Override
    public String toString() {
        return "Author [auth_id=" + auth_id + ", auth_first_name=" + auth_first_name + ", auth_last_name=" + auth_last_name + ", auth_birth_date=" + auth_birth_date + "]";
    }

}