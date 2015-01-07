package univ.evector.internal.service.impl;

import java.util.Date;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.callback.LoginException;
import lt.jmsys.spark.gwt.client.callback.LoginException.Code;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.LoginKey;
import univ.evector.beans.Password;
import univ.evector.beans.User;
import univ.evector.beans.UserSession;
import univ.evector.beans.UserSessionHolder;
import univ.evector.beans.helper.PasswordHelper;
import univ.evector.beans.helper.UserSessionHelper;
import univ.evector.db.dao.FbAccessTokenDao;
import univ.evector.db.dao.LoginKeyDao;
import univ.evector.db.dao.PasswordDao;
import univ.evector.db.dao.UserDao;
import univ.evector.db.dao.UserSessionDao;
import univ.evector.facebook.helper.EvectorFacebookHelper;
import univ.evector.internal.service.UserService;
import univ.evector.security.SelfSecured;
import eu.itreegroup.spark.application.bean.LogoutCause;
import eu.itreegroup.spark.application.error.ErrorHelper;
import eu.itreegroup.spark.application.error.SparkLoginErrorCode;
import eu.itreegroup.spark.application.helper.SecureKeyUtils;
import eu.itreegroup.spark.application.service.SettingsService;
import eu.itreegroup.spark.application.shared.security.DocumentId;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private ApplicationContext springContext;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordDao passwordDao;

    @Autowired
    private LoginKeyDao loginKeyDao;

    @Autowired
    private UserSessionDao userSessionDao;

    @Autowired
    private FbAccessTokenDao fbAccessTokenDao;

    // NOTE: is it thread safe to use jdbcTemplate as instance vars:
    // yes, see: http://forum.spring.io/forum/spring-projects/data/97092-rowmapper-parameterizedrowmapper-thread-safety

    // TODO: transactions - reikia patestuoti realioje konfiguracijoje - kai kazkas iterpiama/keiciama per web'a (Matui
    // dar reikia sukurti mechanizmą, kaip patikrinti ar ant beans'o uždėta Transactional anotacija...

    @Override
    @SelfSecured
    public UserSession authenticateByUserName(String userName, String passwordAsString, String ip) throws SparkBusinessException {
        Password password = passwordDao.findByEmail(userName);
        authenticate(password, passwordAsString, ip);

        User user = userDao.findByEmail(userName);

        // 4. update user record if needed: if password correct we could reset login failure count and save last
        // successfull login date

        UserSession retVal = createUserSession(user, ip);
        userSessionDao.insertNewSession(retVal);
        return retVal;
    }

    @Override
    @SelfSecured
    public UserSession authenticateByUserId(DocumentId userId, String passwordAsString, String ip) throws SparkBusinessException {
        User user = userDao.find(userId);

        Password password = passwordDao.findByUsrId(user.getUsr_id());
        authenticate(password, passwordAsString, ip);

        // 4. update user record if needed: if password correct we could reset login failure count and save last
        // successfull login date

        UserSession retVal = createUserSession(user, ip);
        userSessionDao.insertNewSession(retVal);
        return retVal;
    }

    @Override
    @SelfSecured
    public UserSession authenticateByLoginKey(String value, String workplaceUid, String ip) throws SparkBusinessException {
        LoginKey loginKey = loginKeyDao.findByValue(value);
        validateLoginKey(loginKey);
        UserSession retVal = createUserSession(loginKey.getUser(), ip);
        userSessionDao.insertNewSession(retVal);
        return retVal;
    }

    private static void validateLoginKey(LoginKey loginKey) {

    }

    private static void validateBeforeRegistering(User user) throws SparkBusinessException {
        if (user == null) {
            throw new IllegalArgumentException("Given user is NULL valued.");
        }

    }

    private static void authenticate(Password password, String passwordAsString, String ip) throws SparkBusinessException {
        if (!isPassedPasswordValid(password, passwordAsString)) {
            ErrorHelper.throwLoginException(SparkLoginErrorCode.LOGIN_ERROR_WRONG_USERNAME_PASSWORD);
        }
    }

    private static boolean isPassedPasswordValid(Password password, String passwordAsString) {
        boolean retVal = false;
        if (ConversionHelper.isNull(password)) {
            LOGGER.debug("Given password is NULL valued.");
        } else if (!PasswordHelper.hasHash(password)) {
            LOGGER.debug("Given password.psw_hash is NULL valued.");
        } else if (!PasswordHelper.hasSalt(password)) {
            LOGGER.debug("Given password.psw_salt is NULL valued.");
        } else {
            String passwordHash = SecureKeyUtils.toSHA256DigestHexString(passwordAsString, password.getPsw_salt());
            if (PasswordHelper.hasHash(password, passwordHash)) {
                retVal = true;
            }
        }
        return retVal;
    }

    private UserSession createUserSession(User user, String sesIp) {
        UserSession retVal = new UserSession();

        String sesKey = SecureKeyUtils.generateSessionKey();

        Date sesCreated = new Date();

        retVal.setSes_key(sesKey);
        retVal.setSes_ip(sesIp);
        retVal.setSes_created(sesCreated);
        retVal.setUser(user);

        return retVal;
    }

    @Override
    public String createLoginKey(String sesionKey, String workPlaceUid) throws SparkBusinessException {
        UserSession userSession = userSessionDao.findOpenUserSessionByKey(sesionKey);
        if (userSession == null) {
            ErrorHelper.throwLoginException(SparkLoginErrorCode.LOGIN_ERROR_SESSION_EXPIRED);
        }
        LoginKey loginKey = loginKeyDao.createLoginKey(userSession.getUser(), workPlaceUid);
        return loginKey.getLgk_key();
    }

    @Override
    public void changePassword(String username, String password, String newPassword) throws SparkBusinessException {
        // TODO Auto-generated method stub

        // 1) check if user has same

    }

    @Override
    public void changePassword(String resetPasswordTicket, String newPassword) throws SparkBusinessException {
        // TODO Auto-generated method stub
    }

    @Override
    @SelfSecured
    public void touchSession(String sessionKey) throws SparkBusinessException {
        UserSession session = userSessionDao.findByKey(sessionKey);
        if (session == null) {
            ErrorHelper.throwLoginException(SparkLoginErrorCode.LOGIN_ERROR_SESSION_EXPIRED);
        }
        if (UserSessionHelper.isClosed(session)) {
            userSessionDao.closeSession(session);
            ErrorHelper.throwLoginException(SparkLoginErrorCode.LOGIN_ERROR_SESSION_EXPIRED);
        }
        if (UserSessionHelper.isExpired(session)) {
            userSessionDao.closeSession(session);
            ErrorHelper.throwLoginException(SparkLoginErrorCode.LOGIN_ERROR_SESSION_EXPIRED);
        }

        userSessionDao.updateLastActionTime(session);
    }

    @Override
    @SelfSecured
    public UserSession currentSession() throws SparkBusinessException {
        UserSession session = getCurrentSession();
        if (null == session) {
            throw new LoginException(Code.NOAUTH);
        }
        return session;
    }

    private UserSession getCurrentSession() {
        UserSession retVal = getSessionHolderUserSession();
        return retVal;
    }

    @Override
    public boolean hasCurrentSession() {
        boolean retVal = getCurrentSession() != null;
        return retVal;
    }

    @Override
    public String forgotPassword(String email) throws SparkBusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SelfSecured
    public void closeSession(String sessionKey, LogoutCause logoutCause) throws SparkBusinessException {
        UserSession session = getSessionHolderUserSession();
        if (null != session && sessionKey.equals(session.getKey())) {
            clearUserSessionHolderUserSession();
        }
    }

    private void clearUserSessionHolderUserSession() {
        setUserSessionHolderUserSession(null);
    }

    private void setUserSessionHolderUserSession(UserSession session) {
        UserSessionHolder userSessionHolder = getUserSessionHolder();
        if (userSessionHolder != null) {
            userSessionHolder.setUserSession(session);
        }
    }

    @Override
    @SelfSecured
    public void registerUser(User user, String password) throws SparkBusinessException {
        validateBeforeRegistering(user);

        userDao.saveUser(user);

    }

    private UserSession getSessionHolderUserSession() {
        UserSessionHolder sessionHolder = getUserSessionHolder();
        UserSession retVal = null;
        if (sessionHolder != null) {
            retVal = sessionHolder.getUserSession();
        }
        return retVal;
    }

    private UserSessionHolder getUserSessionHolder() {
        UserSessionHolder retVal = springContext.getBean(UserSessionHolder.class);
        return retVal;
    }

    @Override
    @SelfSecured
    public void setLoginKeyExpired(String loginKey) {
        UserSessionHolder sessionHolder = getUserSessionHolder();
        if (sessionHolder != null) {
            UserSession userSession = sessionHolder.getUserSession();
            if (userSession != null) {
                User user = userSession.getUser();
                loginKeyDao.setExpired(loginKey, user);
            }
        }
    }

    @Override
    public void validateUserEmail(String userId, String token) {
        // TODO Auto-generated method stub
    }

    @Override
    public Long currentUserId() throws SparkBusinessException {
        Long retVal = currentSession().getUser().getUsr_id();
        return retVal;
    }

    private static void validateFacebookBeforeAuthentication(Facebook facebook) throws FacebookException {
        if (facebook == null) {
            throw new IllegalStateException("Facebook must be initialized, before this method is called");
        }
        if (facebook.getMe() == null) {
            throw new IllegalStateException("Facebook getMe user has NULL value.");
        }
    }

    @Override
    public UserSession authenticateByFacebook(Facebook facebook) throws SparkBusinessException {
        UserSession retVal = null;
        try {
            validateFacebookBeforeAuthentication(facebook);
            UserSessionHolder holder = getUserSessionHolder();
            facebook4j.User facebookUser = facebook.getMe();
            User user = null;

            if (userDao.hasUser(facebookUser.getEmail())) {
                user = userDao.findByEmail(facebookUser.getEmail());
            } else {
                user = EvectorFacebookHelper.createByFaceBookUser(facebookUser);
                userDao.saveUser(user);
            }

            retVal = createUserSession(user, null);
            holder.setFacebook(facebook);
            holder.setUserSession(retVal);
            userSessionDao.insertNewSession(retVal);

            AccessToken accessToken = facebook.getOAuthAccessToken();

            fbAccessTokenDao.saveAccessToken(user.getUsr_id(), accessToken);

        } catch (IllegalStateException e) {
            String message = "Cought IllegalStateException";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        } catch (FacebookException e) {
            String message = "Cought FacebookException.";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
        return retVal;
    }

    @Override
    public void setSessionFacebook(Facebook facebook) {
        UserSessionHolder holder = getUserSessionHolder();
        holder.setFacebook(facebook);
    }

    /*   private void testFacebookPossibility() {
           try {
               UserSessionHolder holder = getUserSessionHolder();
               if (holder != null) {
                   Facebook facebook = holder.getFacebook();
                   if (facebook != null) {
                       ResponseList<Post> homeResult = facebook.getHome();

                       Iterator<Post> homeIterator = homeResult.iterator();

                       while (homeIterator.hasNext()) {
                           Post homePost = homeIterator.next();
                           if (homePost != null) {
                               String message = homePost.getMessage();
                               LOGGER.info(message);
                               System.out.println(message);
                           }
                       }
                   }
               }
           } catch (FacebookException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       }*/
}
