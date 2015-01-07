package univ.evector.beans.facebook;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class FbPost implements Serializable {

    private Long pst_id;

    private String pst_fb_id;
    private Date pst_date;
    private String pst_message;

    public FbPost() {

    }

    public Long getPst_id() {
        return pst_id;
    }

    public void setPst_id(Long pst_id) {
        this.pst_id = pst_id;
    }

    public String getPst_fb_id() {
        return pst_fb_id;
    }

    public void setPst_fb_id(String pst_fb_id) {
        this.pst_fb_id = pst_fb_id;
    }

    public Date getPst_date() {
        return pst_date;
    }

    public void setPst_date(Date pst_date) {
        this.pst_date = pst_date;
    }

    public String getPst_message() {
        return pst_message;
    }

    public void setPst_message(String pst_message) {
        this.pst_message = pst_message;
    }

    @Override
    public String toString() {
        return "FbPost [pst_id=" + pst_id + ", pst_fb_id=" + pst_fb_id + ", pst_date=" + pst_date + ", pst_message=" + pst_message + "]";
    }

}
