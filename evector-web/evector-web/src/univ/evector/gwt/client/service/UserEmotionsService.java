package univ.evector.gwt.client.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.browse.FindUserEmotionModels;
import univ.evector.beans.browse.FindUserEmotions;
import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionModel;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc")
public interface UserEmotionsService extends RemoteService {

    public QueryResult<FindUserEmotionModels> findUserEmotionModels(QueryMetaData queryMetaData) throws SparkBusinessException;

    public QueryResult<FindUserEmotions> findUserEmotions(QueryMetaData queryMetaData) throws SparkBusinessException;

    public void saveUserEmotion(Emotion emotion) throws SparkBusinessException;

    public Emotion findUserEmotion(Long emwId) throws SparkBusinessException;

    public EmotionModel findUserEmotionModel(Long emmId) throws SparkBusinessException;

    public void saveUserEmotionModel(EmotionModel model) throws SparkBusinessException;

}