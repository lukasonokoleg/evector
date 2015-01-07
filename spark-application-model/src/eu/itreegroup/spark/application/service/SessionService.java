package eu.itreegroup.spark.application.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import eu.itreegroup.spark.application.bean.LogoutCause;
import eu.itreegroup.spark.application.bean.UserSession;
import eu.itreegroup.spark.application.shared.security.DocumentId;

public interface SessionService {

    /**
     * 
     * @return
     * @throws SparkBusinessException if no session or session is expired
     */
    UserSession currentSession() throws SparkBusinessException;

    boolean hasCurrentSession();

    UserSession authenticateByUserName(String userName, String password, String ip) throws SparkBusinessException;

    UserSession authenticateByUserId(DocumentId userId, String password, String ip) throws SparkBusinessException;

    UserSession authenticateByLoginKey(String loginKey, String workplaceUid, String ip) throws SparkBusinessException;

    String createLoginKey(String sesionKey, String workplaceUid) throws SparkBusinessException;

    /**
     * 
     * @param email
     * @return resetPasswordTicket
     * @throws SparkBusinessException
     */
    String forgotPassword(String email) throws SparkBusinessException;

    void changePassword(String resetPasswordTicket, String newPassword) throws SparkBusinessException;

    void changePassword(String username, String password, String newPassword) throws SparkBusinessException;

    void touchSession(String sessionKey) throws SparkBusinessException;

    void closeSession(String sessionKey, LogoutCause logoutCause) throws SparkBusinessException;
}
