package dictionary;

import language_studyer.AnswerDataStatisticsMaker;

public class DictionaryStatisticsMaker extends AnswerDataStatisticsMaker {
        
    public void setData(DictionaryDataContainer dictionaryDataContainer) {
        this.studiedAnswerDataContainer = dictionaryDataContainer.auxiliaryDataContainer.studiedAnswerDataContainer;
        this.studiedStudyItemIndexes = dictionaryDataContainer.auxiliaryDataContainer.studiedCardIndexes;
        this.studiedAnswerDataByStudyItemsContainer = dictionaryDataContainer.auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer;
    }
    
}
