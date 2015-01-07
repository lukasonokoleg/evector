package lt.jmsys.spark.gwt.application.client.application;

import lt.jmsys.spark.gwt.application.common.client.session.SessionHolder;
import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import lt.jmsys.spark.gwt.application.shared.security.Role;

public class User {

    public static boolean hasRole(Role... role) {
        SessionInfo session = SessionHolder.getSessionInfo();
        return session.hasRole(role);
    }

    public static boolean hasSystemRole() {
        return User.hasRole(Role.SYS_ADMIN, Role.SYS_DIRECTOR, Role.SYS_FINANCE, Role.SYS_OPER, Role.SYS_QUALITY);
    }

    public static boolean hasSystemAdminRole() {
        return User.hasRole(Role.SYS_ADMIN);
    }

    public static boolean hasSystemDirectorRole() {
        return User.hasRole(Role.SYS_DIRECTOR);
    }

    public static Double getSessionComId() {
        SessionInfo session = SessionHolder.getSessionInfo();
        return session.getCompanyId();
    }

    public static String getSesionComName() {
        SessionInfo session = SessionHolder.getSessionInfo();
        return session.getCompanyName();
    }
}
