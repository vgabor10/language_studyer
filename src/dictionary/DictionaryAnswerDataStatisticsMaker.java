package dictionary;

import java.util.Set;
import study_item_objects.AnswerDataStatisticsMaker;
import study_item_objects.AnswerDataContainer;

public class DictionaryAnswerDataStatisticsMaker extends AnswerDataStatisticsMaker {

        @Override
	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void setCardContainer(CardContainer cc) {
		this.studyItemContainer = cc;
	}
        
        public void setData(DictionaryDataContainer dictionaryDataContainer,
                Set<Integer> cardCategoryRestrictions) {
            
        }
}
