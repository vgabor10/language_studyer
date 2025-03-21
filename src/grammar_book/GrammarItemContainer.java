package grammar_book;

import language_studyer.StudyItemContainer;

import java.util.*;

public class GrammarItemContainer extends StudyItemContainer {

    public int numberOfGrammarItems() {
        return numberOfStudyItems();
    }

    public GrammarItem getByIndex(int grammarItemIndex) {
        return (GrammarItem) getStudyItemByIndex(grammarItemIndex);
    }

    public GrammarItem getGrammarItemByOrder(int orderIndex) {
        return (GrammarItem) getStudyItemByOrder(orderIndex);
    }

    public Set<Integer> getGrammarItemIndexes() {
        return getStudyItemIndexes();
    }

    public void addGrammarItem(GrammarItem gi) {
        addStudyItem(gi);
    }

    public Example getExample(int grammarItemIndex, int exampleIndex) {
        return getByIndex(grammarItemIndex).getExampleByIndex(exampleIndex);
    }

    public void toScreenTableOfContents() {
        System.out.println("TABLE OF CONTENT");
        System.out.println();
        for (int i = 0; i < numberOfStudyItems(); i++) {
            System.out.println(i + " - "
                    + getGrammarItemByOrder(i).title + " (" + getGrammarItemByOrder(i).numberOfExamples() + ")");
        }

        System.out.println();
        System.out.println("note: index - title (number of examples)");
    }

    @Override
    public void toScreen() {
        for (int i = 0; i < numberOfStudyItems(); i++) {
            System.out.println(getGrammarItemByOrder(i).toString());
        }
    }
}
