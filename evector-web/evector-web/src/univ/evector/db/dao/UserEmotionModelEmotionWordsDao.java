package univ.evector.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import univ.evector.beans.emotion.EmotionWord;

@Repository
public interface UserEmotionModelEmotionWordsDao {

    void saveEmotionWords(Long emoId, List<EmotionWord> words);

}
