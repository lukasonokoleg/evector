package univ.evector.gwt.client.service;

import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;
import univ.evector.beans.browse.FindUserEmotionModels;
import univ.evector.beans.browse.FindUserEmotions;
import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserEmotionsServiceAsync {

    public void findUserEmotionModels(QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindUserEmotionModels>> callback);

    public void findUserEmotions(QueryMetaData queryMetaData, AsyncCallback<QueryResult<FindUserEmotions>> callback);

    public void saveUserEmotion(Emotion emotion, AsyncCallback<Void> callback);

    public void findUserEmotion(Long emwId, AsyncCallback<Emotion> callback);

    public void findUserEmotionModel(Long emmId, AsyncCallback<EmotionModel> callback);

    public void saveUserEmotionModel(EmotionModel model, AsyncCallback<Void> callback);

}