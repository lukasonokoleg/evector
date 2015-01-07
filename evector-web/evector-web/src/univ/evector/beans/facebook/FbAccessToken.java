package univ.evector.beans.facebook;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class FbAccessToken implements Serializable {

    private Long act_id;

    private String act_code;

    private Date act_date;

    public FbAccessToken() {

    }

    public Long getAct_id() {
        return act_id;
    }

    public void setAct_id(Long act_id) {
        this.act_id = act_id;
    }

    public String getAct_code() {
        return act_code;
    }

    public void setAct_code(String act_code) {
        this.act_code = act_code;
    }

    public Date getAct_date() {
        return act_date;
    }

    public void setAct_date(Date act_date) {
        this.act_date = act_date;
    }

    @Override
    public String toString() {
        return "FbAccessToken [act_id=" + act_id + ", act_code=" + act_code + ", act_date=" + act_date + "]";
    }

}