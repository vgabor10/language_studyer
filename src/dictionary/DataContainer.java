package dictionary;

import study_item_objects.AnswerDataContainer;

public class DictionaryDataContainer {
    
    public CardContainer cardContainer = new CardContainer();
    public AnswerDataContainer answerDataContainer = new AnswerDataContainer();
    public CategoryContainer categoryContainer = new CategoryContainer();
    
    public void clear() {
        cardContainer.clear();
        answerDataContainer.clear();
        categoryContainer.clear();
    }
}
