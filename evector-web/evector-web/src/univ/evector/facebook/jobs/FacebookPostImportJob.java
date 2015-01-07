package univ.evector.facebook.jobs;

import java.util.Date;
import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.beans.User;
import univ.evector.beans.facebook.FbAccessToken;
import univ.evector.beans.facebook.FbPost;
import univ.evector.beans.facebook.helper.FbPostHelper;
import univ.evector.db.dao.FbAccessTokenDao;
import univ.evector.db.dao.FbPostDao;
import univ.evector.facebook.EvectorFacebookSettingsProvider;
import univ.evector.facebook.reading.FacebookReadingBuilder;
import eu.itreegroup.spark.facebook.helper.FacebookHelper;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

public class FacebookPostImportJob {

    public static final Logger LOGGER = Logger.getLogger(FacebookPostImportJob.class);

    @Autowired
    private FbAccessTokenDao fbAccessTokenDao;

    @Autowired
    private EvectorFacebookSettingsProvider settingsProvider;

    @Autowired
    private FbPostDao fbPostDao;

    private final User user;
    private Facebook facebook;

    public FacebookPostImportJob(User user) {
        this.user = user;
    }

    public void run() {
        if (user == null) {
            String message = "Class member user must be initialized.";
            throw new NullPointerException(message);
        }
        try {
            facebook = retrieveFacebook();
            if (facebook != null) {
                Date fromDate = fbPostDao.lastUserFbPostDate(user);
                if (fromDate != null) {
                    importFbPosts(fromDate);
                } else {
                    importFbPosts();
                }
            }
        } catch (SparkBusinessException e) {
            e.printStackTrace();
        }
    }

    private void importFbPosts(Date sinceDate) throws SparkBusinessException {
        try {
            Reading reading = new FacebookReadingBuilder().setSinceDate(sinceDate).build();
            ResponseList<Post> response = facebook.getHome(reading);
            List<FbPost> posts = FbPostHelper.convert(response);
            fbPostDao.saveFbPosts(user, posts);
        } catch (FacebookException e) {
            String message = "Cought FacebookException while importing user posts.";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }

    }

    private void importFbPosts() throws SparkBusinessException {
        try {
            ResponseList<Post> response = facebook.getHome();
            List<FbPost> posts = FbPostHelper.convert(response);
            fbPostDao.saveFbPosts(user, posts);
        } catch (FacebookException e) {
            String message = "Cought FacebookException while importing user posts.";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    protected Facebook retrieveFacebook() throws SparkBusinessException {
        Facebook retVal = null;
        FbAccessToken token = fbAccessTokenDao.lastUserFbAccessToken(user.getUsr_id());
        if (token != null) {
            retVal = FacebookHelper.getFacebook(settingsProvider);
            AccessToken accessToken = new AccessToken(token.getAct_code());
            retVal.setOAuthAccessToken(accessToken);
        }
        return retVal;
    }

}
