package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Als_user_email_ot implements Serializable {

    private final static long serialVersionUID = 1;

    private String email;

    public Als_user_email_ot() {
    }

    public Als_user_email_ot(String email) {
        this.email = email;
    }

    public Als_user_email_ot(Als_user_email_ot src) {
        if (src == null)
            return;

        this.email = src.getEmail();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return Als_user_email_ot.class.getName() + "@[" + "email = " + email + "]";
    }

}
