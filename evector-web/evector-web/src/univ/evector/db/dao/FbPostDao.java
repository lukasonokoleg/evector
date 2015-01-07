package univ.evector.db.dao;

import java.util.Date;
import java.util.List;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import univ.evector.beans.User;
import univ.evector.beans.facebook.FbPost;

@Repository
public interface FbPostDao {

    public void saveFbPosts(User user, List<FbPost> posts) throws SparkBusinessException;

    public void saveFbPosts(Long usrId, List<FbPost> posts) throws SparkBusinessException;

    public Date lastUserFbPostDate(User user) throws SparkBusinessException;

    public Date lastUserFbPostDate(Long usrId) throws SparkBusinessException;

    public List<FbPost> getUserFbPosts(Long usrId) throws SparkBusinessException;

}