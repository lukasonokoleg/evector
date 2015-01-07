package univ.evector.beans.browse;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class FindUserEmotionModels implements Serializable {

    private Long emm_id;

    private Date emm_created;

    private String emm_name;

    public FindUserEmotionModels() {

    }

    public Long getEmm_id() {
        return emm_id;
    }

    public void setEmm_id(Long emm_id) {
        this.emm_id = emm_id;
    }

    public String getEmm_name() {
        return emm_name;
    }

    public void setEmm_name(String emm_name) {
        this.emm_name = emm_name;
    }

    public Date getEmm_created() {
        return emm_created;
    }

    public void setEmm_created(Date emm_created) {
        this.emm_created = emm_created;
    }

    @Override
    public String toString() {
        return "FindUserEmotionModels [emm_id=" + emm_id + ", emm_created=" + emm_created + ", emm_name=" + emm_name + "]";
    }

}