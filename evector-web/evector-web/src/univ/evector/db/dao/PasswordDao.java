package univ.evector.db.dao;

import org.springframework.stereotype.Repository;

import univ.evector.beans.Password;

@Repository
public interface PasswordDao {

    Password findByEmail(String userName);

    Password findByUsrId(Long usrId);

    Password find(Long pswId);

    void save(Password password, Long usrId);

}
