package language_studyer;

import java.util.HashSet;
import java.util.Set;

public class StudyStrategy {
    
    public int numberOfRandomItems = 0;
    public int numberOfItemsFromTheLeastKnown20Percent = 0;
    public int numberOfItemsFromTheLeastKnown100 = 0;    
    public int numberOfItemsAmongTheLeastSignificantAr = 0;
    public int numberOfLatestQuestionedItems = 0;
    public boolean studyingGradually = false;
    public Set<Integer> cardCategoryRestrictions = new HashSet<>();

    public void clear() {
        numberOfRandomItems = 0;
        numberOfItemsFromTheLeastKnown20Percent = 0;
        numberOfItemsFromTheLeastKnown100 = 0;    
        numberOfItemsAmongTheLeastSignificantAr = 0;
        numberOfLatestQuestionedItems = 0;
        studyingGradually = false;
        cardCategoryRestrictions.clear();
    }
    
}
