package dictionary;

import study_item_objects.StudyItem;

public class ExampleSentence extends StudyItem {

    public String sentence;

    public String toStringData() {
        return "ExampleSentence[" + Integer.toString(index) + "," + sentence + "]";
    }
    
}
