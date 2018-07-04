package language_studyer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StudyItem {

    public int index;
    public Set<Integer> categoryIndexes = new HashSet<>();
    public String description;

    public StudyItem() {
        index = -1;
    }
    
    
    public boolean containsAnyCategoryIndex(Set<Integer> categoryIndexes2) {
        return !Collections.disjoint(this.categoryIndexes, categoryIndexes2);
    }

}
