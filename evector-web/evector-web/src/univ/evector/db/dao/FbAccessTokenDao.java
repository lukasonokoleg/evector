package univ.evector.db.dao;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.facebook.FbAccessToken;
import facebook4j.auth.AccessToken;

@Repository
public interface FbAccessTokenDao {

    void saveAccessToken(Long usrId, AccessToken accessToken) throws SparkBusinessException;

    FbAccessToken lastUserFbAccessToken(Long usrId) throws SparkBusinessException;

}
