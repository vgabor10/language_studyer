package dictionary;

import java.util.ArrayList;
import java.util.List;
import language_studyer.StudyItem;

public class Card extends StudyItem {

    public String term;
    public String definition;
    public List<String> exampleSentences = new ArrayList<>();

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
                + "," + exampleSentences.toString() + "]";
    }

    @Override
    public String toString() {
        return term + " - " + definition;
    }

    public String toStringReverse() {
        return definition + " - " + term;
    }
}
