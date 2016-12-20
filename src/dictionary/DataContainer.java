package dictionary;

import study_item_objects.AnswerDataContainer;

public class DataContainer {
    
    public CardContainer cardContainer = new CardContainer();
    public AnswerDataContainer answerDataContainer = new AnswerDataContainer();
    public CategoryContainer categoryContainer = new CategoryContainer();
    
    public void checkDataHelath() {
        DataFormatChecker dataFormatChecker = new DataFormatChecker();
        //TODO: implement
    }
    
    public void clear() {
        cardContainer.clear();
        answerDataContainer.clear();
        categoryContainer.clear();
    }
}
