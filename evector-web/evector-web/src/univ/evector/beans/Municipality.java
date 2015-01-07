package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Municipality implements Serializable {

    private Long mncp_id;

    private String mncp_name;

    public Municipality() {

    }

    public Municipality(Long mncp_id, String mncp_name) {
        this.mncp_id = mncp_id;
        this.mncp_name = mncp_name;
    }

    public Long getMncp_id() {
        return mncp_id;
    }

    public void setMncp_id(Long mncp_id) {
        this.mncp_id = mncp_id;
    }

    public String getMncp_name() {
        return mncp_name;
    }

    public void setMncp_name(String mncp_name) {
        this.mncp_name = mncp_name;
    }

    @Override
    public String toString() {
        return "Municipality [mncp_id=" + mncp_id + ", mncp_name=" + mncp_name + "]";
    }

}
