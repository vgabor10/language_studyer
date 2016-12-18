package dictionary;

import java.util.Collections;
import java.util.Set;
import study_item_objects.AnswerData;
import study_item_objects.AnswerDataContainer;
import study_item_objects.AnswerDataStatisticsMaker;

public class DictionaryStatisticsMaker extends AnswerDataStatisticsMaker {
        
        private boolean isCategoryRestrictionsUsed = false;
    
        public void setData(DictionaryDataContainer dictionaryDataContainer) {
            this.answerDataContainer = dictionaryDataContainer.answerDataContainer;
            this.studyItemContainer = dictionaryDataContainer.cardContainer;
        }
        
        public void setData(DictionaryDataContainer dictionaryDataContainer,
                Set<Integer> cardCategoryRestrictions) {
            
            this.studyItemContainer = new CardContainer();
            for (int i=0; i<dictionaryDataContainer.cardContainer.numberOfCards(); i++) {
                Card card = dictionaryDataContainer.cardContainer.getCardByOrder(i);
                if (!Collections.disjoint(card.categoryIndexes, cardCategoryRestrictions)) {
                    this.studyItemContainer.addStudyItem(card);
                }
            }
            
            Set<Integer> cardIndexes = this.studyItemContainer.getStudyItemIndexes();
            this.answerDataContainer = new AnswerDataContainer();
            for (int i=0; i<dictionaryDataContainer.answerDataContainer.numberOfAnswers(); i++) {
                AnswerData answerData = dictionaryDataContainer.answerDataContainer.getAnswerData(i);
                if (cardIndexes.contains(answerData.index)) {
                    this.answerDataContainer.addAnswerData(answerData);
                }
            }
            
            isCategoryRestrictionsUsed = true;
        }
        
        public boolean isCategoryRestrictionsUsed() {
            return isCategoryRestrictionsUsed;
        }
        
        public CardContainer getCardContontainer() {
            return (CardContainer) this.studyItemContainer;
        }
        
        public AnswerDataContainer getAnswerDataContainer() {
            return this.answerDataContainer;
        }
}
