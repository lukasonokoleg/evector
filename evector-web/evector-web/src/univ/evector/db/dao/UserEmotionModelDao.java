package univ.evector.db.dao;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.stereotype.Repository;

import univ.evector.beans.browse.FindUserEmotionModels;
import univ.evector.beans.browse.FindUserEmotions;
import univ.evector.beans.emotion.EmotionModel;

@Repository
public interface UserEmotionModelDao {

    QueryResult<FindUserEmotions> findUserEmotions(Long userId, QueryMetaData queryMetaData) throws SparkBusinessException;

    QueryResult<FindUserEmotionModels> findUserEmotionModels(Long userId, QueryMetaData queryMetaData) throws SparkBusinessException;

    void saveUserEmotionModel(Long userId, EmotionModel model) throws SparkBusinessException;

}
