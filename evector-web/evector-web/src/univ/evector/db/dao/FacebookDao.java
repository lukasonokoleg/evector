package univ.evector.db.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface FacebookDao {

    String getFaceBookLoginUrl();

    String getAuthApplicationId();

    String getAuthApplicationSecret();

    boolean isDebugEnabled();

    boolean isJsonStoreEnabled();

    String getAuthPermissions();

}
