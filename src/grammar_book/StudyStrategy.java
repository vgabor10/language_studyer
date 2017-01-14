package grammar_book;

import java.util.HashSet;
import java.util.Set;

public class StudyStrategy {
    
    public int randomGrammarItemWeight = 0;
    public int randomGrammarItemFromThe5LeastStudiedWeight = 0;
    public int randomGrammarItemFromThe5HardestWeight = 0;    
    public int latestStudiedGrammarItemWeight = 0;
    public Set<Integer> categoryRestrictions = new HashSet<>();
    
    public StudyStrategy() {
        categoryRestrictions.add(0);
    }
    
    public void clear() {
        //TODO: implement
    }
    
}
