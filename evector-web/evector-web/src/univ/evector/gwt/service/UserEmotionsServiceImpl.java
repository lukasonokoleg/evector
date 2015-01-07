package univ.evector.gwt.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.gwt.client.ui.browse.query.QueryResult;
import lt.jmsys.spark.gwt.user.client.ui.core.QueryMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.beans.browse.FindUserEmotionModels;
import univ.evector.beans.browse.FindUserEmotions;
import univ.evector.beans.emotion.Emotion;
import univ.evector.beans.emotion.EmotionModel;
import univ.evector.beans.helper.EmotionModelHelper;
import univ.evector.db.dao.UserEmotionModelDao;
import univ.evector.gwt.client.service.UserEmotionsService;
import univ.evector.internal.service.UserService;

@Service
@Transactional
public class UserEmotionsServiceImpl implements UserEmotionsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEmotionModelDao userEmotionDao;

    @Override
    public QueryResult<FindUserEmotions> findUserEmotions(QueryMetaData queryMetaData) throws SparkBusinessException {
        Long currentUserId = userService.currentUserId();
        QueryResult<FindUserEmotions> retVal = userEmotionDao.findUserEmotions(currentUserId, queryMetaData);
        return retVal;
    }

    @Override
    public void saveUserEmotion(Emotion emotion) throws SparkBusinessException {
        // TODO Auto-generated method stub

    }

    @Override
    public Emotion findUserEmotion(Long emwId) throws SparkBusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryResult<FindUserEmotionModels> findUserEmotionModels(QueryMetaData queryMetaData) throws SparkBusinessException {
        Long currentUserId = userService.currentUserId();
        QueryResult<FindUserEmotionModels> retVal = userEmotionDao.findUserEmotionModels(currentUserId, queryMetaData);
        return retVal;
    }

    @Override
    public EmotionModel findUserEmotionModel(Long emmId) throws SparkBusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveUserEmotionModel(EmotionModel model) throws SparkBusinessException {
        Long currentUserId = userService.currentUserId();

        EmotionModelHelper.organizeEmotionModelWords(model);

        userEmotionDao.saveUserEmotionModel(currentUserId, model);

    }

}
