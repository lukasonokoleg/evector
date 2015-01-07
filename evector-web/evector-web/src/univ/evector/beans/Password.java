package univ.evector.beans;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Password implements Serializable {

    private Long psw_id;

    private String psw_hash;
    private String psw_salt;

    private Date psw_created;
    private Date psw_next_change;

    private PasswordStatus psw_status;

    public Password() {

    }

    public Long getPsw_id() {
        return psw_id;
    }

    public void setPsw_id(Long psw_id) {
        this.psw_id = psw_id;
    }

    public String getPsw_hash() {
        return psw_hash;
    }

    public void setPsw_hash(String psw_hash) {
        this.psw_hash = psw_hash;
    }

    public String getPsw_salt() {
        return psw_salt;
    }

    public void setPsw_salt(String psw_salt) {
        this.psw_salt = psw_salt;
    }

    public Date getPsw_created() {
        return psw_created;
    }

    public void setPsw_created(Date psw_created) {
        this.psw_created = psw_created;
    }

    public Date getPsw_next_change() {
        return psw_next_change;
    }

    public void setPsw_next_change(Date psw_next_change) {
        this.psw_next_change = psw_next_change;
    }

    public PasswordStatus getPsw_status() {
        return psw_status;
    }

    public void setPsw_status(PasswordStatus psw_status) {
        this.psw_status = psw_status;
    }

    @Override
    public String toString() {
        return "Password [psw_id=" + psw_id + ", psw_hash=" + psw_hash + ", psw_salt=" + psw_salt + ", psw_created=" + psw_created + ", psw_next_change=" + psw_next_change
                + ", psw_status=" + psw_status + "]";
    }

}