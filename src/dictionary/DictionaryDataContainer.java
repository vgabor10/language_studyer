package dictionary;

import java.util.HashMap;
import java.util.Map;
import study_item_objects.AnswerDataContainer;

public class DictionaryDataContainer {
    
    public CardContainer cardContainer = new CardContainer();
    public AnswerDataContainer answerDataContainer = new AnswerDataContainer();
    public Map<Integer, String> categoryIndexesAndCategoryNames = new HashMap<>();
    
    public void clear() {
        cardContainer.clear();
        answerDataContainer.clear();
        categoryIndexesAndCategoryNames.clear();
    }
}
