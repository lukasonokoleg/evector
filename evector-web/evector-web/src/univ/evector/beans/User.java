package univ.evector.beans;

import java.io.Serializable;

import eu.itreegroup.spark.application.bean.UserBean;

@SuppressWarnings("serial")
public class User extends UserBean implements Serializable {

    private Long usr_id;

    private String usr_first_name;

    private String usr_last_name;

    private String usr_email;

    private UserStatus usr_status;

    public User() {

    }

    public Long getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(Long usr_id) {
        this.usr_id = usr_id;
    }


    public String getUsr_last_name() {
        return usr_last_name;
    }

    public void setUsr_last_name(String usr_last_name) {
        this.usr_last_name = usr_last_name;
    }

    public String getUsr_email() {
        return usr_email;
    }

    public void setUsr_email(String usr_email) {
        this.usr_email = usr_email;
    }

    public String getUsr_first_name() {
        return usr_first_name;
    }

    public void setUsr_first_name(String usr_first_name) {
        this.usr_first_name = usr_first_name;
    }

    public UserStatus getUsr_status() {
        return usr_status;
    }

    public void setUsr_status(UserStatus usr_status) {
        this.usr_status = usr_status;
    }

    @Override
    public String toString() {
        return "User [usr_id=" + usr_id + ", usr_first_name=" + usr_first_name + ", usr_last_name=" + usr_last_name + ", usr_email=" + usr_email + ", usr_status=" + usr_status
                + "]";
    }



}