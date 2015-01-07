package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Als_user_ot implements Serializable {

    private final static long serialVersionUID = 1;

    private Double usr_id;

    private String person_name;

    private String person_surname;

    private String language;

    private String phone;

    private Date lock_date;

    private Als_role_ot[] user_roles;

    private Als_user_email_ot[] user_emails;

    public Als_user_ot() {
    }

    public Als_user_ot(Double usr_id, String person_name, String person_surname, String language, String phone,
            Date lock_date, Als_role_ot[] user_roles, Als_user_email_ot[] user_emails) {
        this.usr_id = usr_id;
        this.person_name = person_name;
        this.person_surname = person_surname;
        this.language = language;
        this.phone = phone;
        this.lock_date = lock_date;
        this.user_roles = user_roles;
        this.user_emails = user_emails;
    }

    public Double getUsr_id() {
        return this.usr_id;
    }

    public void setUsr_id(Double usr_id) {
        this.usr_id = usr_id;
    }

    public String getPerson_name() {
        return this.person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_surname() {
        return this.person_surname;
    }

    public void setPerson_surname(String person_surname) {
        this.person_surname = person_surname;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLock_date() {
        return this.lock_date;
    }

    public void setLock_date(Date lock_date) {
        this.lock_date = lock_date;
    }

    public Als_role_ot[] getUser_roles() {
        return this.user_roles;
    }

    public void setUser_roles(Als_role_ot[] user_roles) {
        this.user_roles = user_roles;
    }

    public Als_user_email_ot[] getUser_emails() {
        return this.user_emails;
    }

    public void setUser_emails(Als_user_email_ot[] user_emails) {
        this.user_emails = user_emails;
    }

    @Override
    public String toString() {
        return Als_user_ot.class.getName() + "@[" + "usr_id = " + usr_id + ", person_name = " + person_name
                + ", person_surname = " + person_surname + ", language = " + language + ", phone = " + phone
                + ", lock_date = " + lock_date + ", user_roles = " + Arrays.toString(user_roles) + ", user_emails = "
                + Arrays.toString(user_emails) + "]";
    }
}
