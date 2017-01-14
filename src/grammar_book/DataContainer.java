package grammar_book;

import dictionary.AuxiliaryDataContainer;
import dictionary.CategoryContainer;
import language_studyer.AnswerData;

public class DataContainer {
    
    public GrammarItemContainer grammarItemContainer = new GrammarItemContainer();
    public GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
    public StudyStrategy studyStrategy = new StudyStrategy();
    public CategoryContainer categoryContainer = new CategoryContainer();
    
    public AuxiliaryDataContainer auxiliaryDataContainer = new AuxiliaryDataContainer();
    
    public void fillAuxiliaryDataContainer() {
        auxiliaryDataContainer.clear();
        
        for (int i=0; i<grammarItemContainer.numberOfGrammarItems(); i++) {
            GrammarItem grammarItem = grammarItemContainer.getGrammarItemByOrder(i);
            if (grammarItem.containsAnyCategoryIndex(studyStrategy.categoryRestrictions)) {
                auxiliaryDataContainer.studiedCardIndexes.add(grammarItem.index);
            }
        }
        
        for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = grammarAnswerDataContainer.getAnswerData(i);
            if (auxiliaryDataContainer.studiedCardIndexes.contains(answerData.index)) {
                auxiliaryDataContainer.studiedAnswerDataContainer.addAnswerData(answerData);
                auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer.addAnswerData(answerData);
            }
        }       
    }
    
    public void clear() {
        grammarItemContainer.clear();
        grammarAnswerDataContainer.clear();
        studyStrategy.clear();
        categoryContainer.clear();
    }
    
}
