package univ.evector.internal.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import univ.evector.beans.User;
import univ.evector.beans.UserSession;
import eu.itreegroup.spark.application.service.SessionService;
import facebook4j.Facebook;

public interface UserService extends SessionService {

    Long currentUserId() throws SparkBusinessException;

    UserSession currentSession() throws SparkBusinessException;

    UserSession authenticateByFacebook(Facebook facebook) throws SparkBusinessException;

    UserSession authenticateByUserName(String username, String password, String ip) throws SparkBusinessException;

    UserSession authenticateByLoginKey(String loginKey, String workplaceUid, String ip) throws SparkBusinessException;

    void setLoginKeyExpired(String loginKey);

    String createLoginKey(String sesionKey, String workplaceUid) throws SparkBusinessException;

    void touchSession(String sessionKey) throws SparkBusinessException;

    /**
     * 
     * @param user
     * @throws SparkBusinessException
     */
    void registerUser(User user, String password) throws SparkBusinessException;

    void validateUserEmail(String userId, String token);

    void setSessionFacebook(Facebook facebook);

}