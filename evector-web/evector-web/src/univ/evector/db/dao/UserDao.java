package univ.evector.db.dao;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.User;
import eu.itreegroup.spark.application.shared.security.DocumentId;
import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;

@Repository
public interface UserDao {

    DocumentPrivileges findUserPrivileges(Long userId, Long currentUserId) throws SparkBusinessException;

    User defaultRegistrationUser();

    void saveUser(User user);

    boolean hasUser(String email);

    User findByEmail(String email);

    User find(Long id);

    User find(DocumentId id);

    void deleteUser(User user);

    List<User> findAllUsers() throws SparkBusinessException;

}