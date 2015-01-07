package lt.jmsys.spark.gwt.application.common.client.helper;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window.Navigator;

public class AlcsBrowserHelper {

    private static final String IE_VERSION_REGEX = "(Trident)(.*)(rv:)(\\d*\\.\\d*)";
    private final static RegExp regExp = RegExp.compile(IE_VERSION_REGEX);

    public static final String USER_AGENT_IE_11 = "IE_11";
    private static final String USER_AGENT_OTHER = "OTHER";

    private static final Integer VERSION_GROUP = 4;

    public static String getUserAgentVersion(String userAgentHeader) {
        if (_isIe11OrNewer(userAgentHeader)) {
            return USER_AGENT_IE_11;
        } else {
            return USER_AGENT_OTHER;
        }

    }

    private static boolean _isIe11OrNewer(String userAgentHeader) {
        if (userAgentHeader != null) {
            MatchResult matcher = regExp.exec(userAgentHeader);
            return matcher != null;
        }
        return false;
    }

    public static boolean isIe10(String userAgentHeader) {
        if (userAgentHeader != null) {
            return userAgentHeader.contains("Trident/6.0");
        }
        return false;
    }

    public static boolean isChrome() {
        String versionStr = Navigator.getAppVersion();
        return versionStr.toLowerCase().indexOf("chrome") > -1;
    }

}
