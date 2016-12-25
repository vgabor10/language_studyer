package dictionary;

import study_item_objects.AnswerData;
import study_item_objects.AnswerDataContainer;

public class DataContainer {
    
    public CardContainer cardContainer = new CardContainer();
    public AnswerDataContainer answerDataContainer = new AnswerDataContainer();
    public CategoryContainer categoryContainer = new CategoryContainer();
    public StudyStrategy studyStrategy = new StudyStrategy();

    public AuxiliaryDataContainer auxiliaryDataContainer = new AuxiliaryDataContainer();
    
    public void fillAuxiliaryDataContainer() {
        auxiliaryDataContainer.clear();
        
        for (int i=0; i<cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (card.containsAnyCategoryIndex(studyStrategy.cardCategoryRestrictions)) {
                auxiliaryDataContainer.studiedCardIndexes.add(card.index);
            }
        }
        
        for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);
            if (auxiliaryDataContainer.studiedCardIndexes.contains(answerData.index)) {
                auxiliaryDataContainer.studiedAnswerDataContainer.addAnswerData(answerData);
                auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer.addAnswerData(answerData);
            }
        }        
    }
    
    public void clear() {
        cardContainer.clear();
        answerDataContainer.clear();
        categoryContainer.clear();
    }
}
