package univ.evector.db.dao;

import org.springframework.stereotype.Repository;

import univ.evector.beans.LoginKey;
import univ.evector.beans.User;

@Repository
public interface LoginKeyDao  {

    LoginKey findByValue(String value);

    void setExpired(String loginKey, User user);

    void save(LoginKey loginKey);

    LoginKey createLoginKey(User user, String workPlaceUid);

}
