package dictionary;

import java.util.HashSet;
import java.util.Set;

public class StudyStrategy {
    
    public int numberOfRandomCards = 0;
    public int numberOfCardsFromTheLeastKnown20Percent = 0;
    public int numberOfCardsFromTheLeastKnown100 = 0;    
    public int numberOfCardsAmongTheLeastSignificantAr = 0;
    public int numberOfLatestQuestionedCards = 0;
    public boolean studyingGradually = false;
    public Set<Integer> cardCategoryRestrictions = new HashSet<>();

}
