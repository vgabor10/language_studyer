package dictionary;

import study_item_objects.StudyItemContainer;

public class ExampleSentenceContainer extends StudyItemContainer {

    public ExampleSentence getByIndex(int i) {
        return (ExampleSentence) getStudyItemByIndex(i);
    }
    
    public ExampleSentence getByOrder(int i) {
        return (ExampleSentence) getStudyItemByOrder(i);
    }
    
    public void toScreenData() {
        for (int i = 0; i < numberOfStudyItems(); i++) {
            System.out.println(getByOrder(i).toStringData());
        }
    }
    
}
