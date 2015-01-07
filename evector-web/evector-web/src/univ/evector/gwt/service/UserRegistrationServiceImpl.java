package univ.evector.gwt.service;

import java.util.Date;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.application.helper.SecureKeyUtils;
import univ.evector.beans.Password;
import univ.evector.beans.PasswordStatus;
import univ.evector.beans.User;
import univ.evector.beans.UserStatus;
import univ.evector.db.dao.PasswordDao;
import univ.evector.db.dao.UserDao;
import univ.evector.gwt.client.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordDao passwordDao;

    @Override
    public User defaultRegistrationUser() throws SparkBusinessException {
        return userDao.defaultRegistrationUser();
    }

    @Override
    public void registerUser(User user, String passwordAsString) throws SparkBusinessException {

        user.setUsr_status(UserStatus.CREATED);

        userDao.saveUser(user);

        Password password = new Password();

        Date psw_created = new Date();

        String psw_salt = SecureKeyUtils.generateRanfomSalt128();
        String psw_hash = SecureKeyUtils.toSHA256DigestHexString(passwordAsString, psw_salt);

        password.setPsw_created(psw_created);
        password.setPsw_hash(psw_hash);
        password.setPsw_salt(psw_salt);
        password.setPsw_status(PasswordStatus.CREATED);

        passwordDao.save(password, user.getUsr_id());
    }

}
