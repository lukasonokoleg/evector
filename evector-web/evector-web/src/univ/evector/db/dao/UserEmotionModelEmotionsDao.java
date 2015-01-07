package univ.evector.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import univ.evector.beans.emotion.Emotion;

@Repository
public interface UserEmotionModelEmotionsDao {

    void saveEmotions(Long emmId, List<Emotion> emotions);

}
