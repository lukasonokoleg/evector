package univ.evector.beans.helper;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import univ.evector.beans.UserSession;

public class UserSessionHelper {

    public static boolean isClosed(UserSession session) {
        boolean retVal = true;
        if (session != null && session.getSes_logout() == null) {
            retVal = false;
        }
        return retVal;
    }

    public static boolean isExpired(UserSession session) {
        boolean retVal = true;
        if (session != null) {
            Long timeOut = session.getTimeout();
            Date accessed = session.getAccessed();
            Date currentDate = new Date();
            if (accessed != null) {
                DateTime start = new DateTime(accessed);
                DateTime end = new DateTime(currentDate);
                Interval interval = new Interval(start, end);
                if (interval.contains(timeOut)) {
                    retVal = false;
                }
            }
        }
        return retVal;
    }

}
