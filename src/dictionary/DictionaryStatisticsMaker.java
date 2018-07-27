package dictionary;

import language_studyer.AnswerDataStatisticsMaker;

public class DictionaryStatisticsMaker extends AnswerDataStatisticsMaker {
        
    public void setData(DictionaryDataContainer dictionaryDataContainer) {
        this.studiedAnswerDataContainer = dictionaryDataContainer.studiedDataContainer.studiedAnswerDataContainer;
        this.studiedStudyItemIndexes = dictionaryDataContainer.studiedDataContainer.studiedCardIndexes;
        this.studiedAnswerDataByStudyItemsContainer = dictionaryDataContainer.studiedDataContainer.studiedAnswerDataByStudyItemContainer;
    }
    
}
