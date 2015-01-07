package lt.jmsys.spark.gwt.application.common.shared.login.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginResult implements Serializable {

    public static final String COOKIE_LOGIN_KEY = "session-key";
    public static final String COOKIE_WORKPLACE_UID = "workplace-uid";

    private String redirectUrl;
    private String workplaceUid;
    private String loginKey;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getWorkplaceUid() {
        return workplaceUid;
    }

    public void setWorkplaceUid(String workplaceUid) {
        this.workplaceUid = workplaceUid;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

}
