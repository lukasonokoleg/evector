package eu.itreegroup.spark.application.common.shared.login.db.bean;

import java.io.Serializable;
import java.util.Date;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Als_session_ot implements Serializable {

    private final static long serialVersionUID = 1;

    private Double ses_id;

    private String session_key;

    private Double user_id;

    private Date login_time;

    private Date logout_time;

    private Date last_action_time;

    private String ip;

    private String role;

    private String role_name;

    private String person_username;

    private String person_name;

    private String person_surname;

    private Double org_id;

    private String org_name;

    private Double session_timeout;

    private Double psub_id;

    private String spln_code;

    private String spln_title;

    private String language;

    public Als_session_ot() {
    }

    public Als_session_ot(Double ses_id, String session_key, Double user_id, Date login_time, Date logout_time, Date last_action_time, String ip, String role, String role_name,
            String person_username, String person_name, String person_surname, Double org_id, String org_name, Double session_timeout, Double psub_id, String spln_code,
            String spln_title, String language) {
        this.ses_id = ses_id;
        this.session_key = session_key;
        this.user_id = user_id;
        this.login_time = login_time;
        this.logout_time = logout_time;
        this.last_action_time = last_action_time;
        this.ip = ip;
        this.role = role;
        this.role_name = role_name;
        this.person_username = person_username;
        this.person_name = person_name;
        this.person_surname = person_surname;
        this.org_id = org_id;
        this.org_name = org_name;
        this.session_timeout = session_timeout;
        this.psub_id = psub_id;
        this.spln_code = spln_code;
        this.spln_title = spln_title;
        this.language = language;
    }

    public Als_session_ot(Als_session_ot src) {
        if (src == null)
            return;

        this.ses_id = src.getSes_id();
        this.session_key = src.getSession_key();
        this.user_id = src.getUser_id();
        this.login_time = src.getLogin_time();
        this.logout_time = src.getLogout_time();
        this.last_action_time = src.getLast_action_time();
        this.ip = src.getIp();
        this.role = src.getRole();
        this.role_name = src.getRole_name();
        this.person_username = src.getPerson_username();
        this.person_name = src.getPerson_name();
        this.person_surname = src.getPerson_surname();
        this.org_id = src.getOrg_id();
        this.org_name = src.getOrg_name();
        this.session_timeout = src.getSession_timeout();
        this.psub_id = src.getPsub_id();
        this.spln_code = src.getSpln_code();
        this.spln_title = src.getSpln_title();
        this.language = src.getLanguage();
    }

    public Double getSes_id() {
        return this.ses_id;
    }

    public void setSes_id(Double ses_id) {
        this.ses_id = ses_id;
    }

    public String getSession_key() {
        return this.session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public Double getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Double user_id) {
        this.user_id = user_id;
    }

    public Date getLogin_time() {
        return this.login_time;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }

    public Date getLogout_time() {
        return this.logout_time;
    }

    public void setLogout_time(Date logout_time) {
        this.logout_time = logout_time;
    }

    public Date getLast_action_time() {
        return this.last_action_time;
    }

    public void setLast_action_time(Date last_action_time) {
        this.last_action_time = last_action_time;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole_name() {
        return this.role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPerson_username() {
        return person_username;
    }

    public void setPerson_username(String person_username) {
        this.person_username = person_username;
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

    public Double getOrg_id() {
        return this.org_id;
    }

    public void setOrg_id(Double org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return this.org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public Double getSession_timeout() {
        return this.session_timeout;
    }

    public void setSession_timeout(Double session_timeout) {
        this.session_timeout = session_timeout;
    }

    public Double getPsub_id() {
        return this.psub_id;
    }

    public void setPsub_id(Double psub_id) {
        this.psub_id = psub_id;
    }

    public String getSpln_code() {
        return this.spln_code;
    }

    public void setSpln_code(String spln_code) {
        this.spln_code = spln_code;
    }

    public String getSpln_title() {
        return this.spln_title;
    }

    public void setSpln_title(String spln_title) {
        this.spln_title = spln_title;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return Als_session_ot.class.getName() + "@[" + "ses_id = " + ses_id + ", session_key = " + session_key + ", user_id = " + user_id + ", login_time = " + login_time
                + ", logout_time = " + logout_time + ", last_action_time = " + last_action_time + ", ip = " + ip + ", role = " + role + ", role_name = " + role_name
                + ", person_name = " + person_name + ", person_surname = " + person_surname + ", org_id = " + org_id + ", org_name = " + org_name + ", session_timeout = "
                + session_timeout + ", psub_id = " + psub_id + ", spln_code = " + spln_code + ", spln_title = " + spln_title + ", language = " + language + "]";
    }

}
