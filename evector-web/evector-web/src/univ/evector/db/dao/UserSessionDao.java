package univ.evector.db.dao;

import org.springframework.stereotype.Repository;

import univ.evector.beans.UserSession;

@Repository
public interface UserSessionDao {

    void closeSession(UserSession session);

    void insertNewSession(UserSession session);

    void updateLastActionTime(UserSession session);

    UserSession findByKey(String key);

    UserSession findOpenUserSessionByKey(String key);

}
