package dictionary;

import java.util.HashSet;
import java.util.Set;
import study_item_objects.AnswerDataByStudyItemContainer;
import study_item_objects.AnswerDataContainer;

public class AuxiliaryDataContainer {
    
    public Set<Integer> studiedCardIndexes = new HashSet<>();
    public AnswerDataContainer studiedAnswerDataContainer = new AnswerDataContainer();
    public AnswerDataByStudyItemContainer studiedAnswerDataByStudyItemContainer = 
            new AnswerDataByStudyItemContainer();

    public void clear() {
        studiedCardIndexes.clear();
        studiedAnswerDataContainer.clear();
        studiedAnswerDataByStudyItemContainer.clear();
    }
}
