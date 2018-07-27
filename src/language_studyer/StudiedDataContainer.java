package language_studyer;

import java.util.HashSet;
import java.util.Set;

public class StudiedDataContainer {
    
    
    
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
