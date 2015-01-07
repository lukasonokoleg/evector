package lt.jmsys.spark.gwt.application.common.shared.login.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogoutResult implements Serializable {

    private String redirectUrl;

    public LogoutResult() {

    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "LogoutResult [redirectUrl=" + redirectUrl + "]";
    }

}
