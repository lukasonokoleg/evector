package univ.evector.facebook.jobs;

import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import univ.evector.beans.User;
import univ.evector.db.dao.UserDao;

public class FacebookPostImportStarter {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private UserDao userDao;

    public void run() {
        try {
            List<User> users = userDao.findAllUsers();
            if (users != null) {
                for (User user : users) {
                    FacebookPostImportJob job = new FacebookPostImportJob(user);
                    beanFactory.autowireBean(job);
                    job.run();
                }
            }
        } catch (SparkBusinessException e) {
            e.printStackTrace();
        }
    }

}