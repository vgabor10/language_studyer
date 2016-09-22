package dictionary;

import java.util.ArrayList;
import java.util.List;
import study_item_objects.StudyItem;

public class Card extends StudyItem {

    public String term;
    public String definition;
    public List<Integer> exampleSentenceIndexes = new ArrayList<>();
    public List<String> categories = new ArrayList<>();

    public Card() {
        index = -1;
        term = "";
        definition = "";
    }

    public Card(int i, String t, String d) {
        index = i;
        term = t;
        definition = d;
    }

    public String toStringData() {
        return "Card[" + Integer.toString(index) + "," + term + "," + definition 
                + "," + exampleSentenceIndexes.toString() + "]";
    }

    @Override
    public String toString() {
        return term + " - " + definition;
    }

    public String toStringReverse() {
        return definition + " - " + term;
    }
}
