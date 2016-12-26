package dictionary;

import language_studyer.StudyItemContainer;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class CardContainer extends StudyItemContainer {

    public int numberOfCards() {
        return numberOfStudyItems();
    }

    public Card getCardByIndex(int i) {
        return (Card) getStudyItemByIndex(i);
    }

    public Card getCardByOrder(int orderIndex) {
        return (Card) getStudyItemByOrder(orderIndex);
    }

    public void addCard(Card c) {
        addStudyItem(c);
    }

    public Set<Integer> getCardIndexes() {
        return getStudyItemIndexes();
    }

    public int getEmptyCardIndex() {
        int emptyCardIndex = 100000;
        Set<Integer> cardIndexes = getStudyItemIndexes();
        while (cardIndexes.contains(emptyCardIndex)) {
            emptyCardIndex++;
        }
        return emptyCardIndex;
    }

    public void removeCardWithOrderIndex(int orderIndex) {
        removeStudyItemWithOrderIndex(orderIndex);
    }

    public void addCardToContainerAndAppenToDiscFile(Card card, String filePath) {	//TODO: take to an other class
        addStudyItem(card);

        try {
            FileWriter fw = new FileWriter(filePath, true);	//the true will append the new data
            fw.write(card.toStringData() + "\n");		//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }

    //TODO: take to CardFinderClass
    public Vector<Integer> findCardsByTerm(String term) {
        Vector<Integer> cardIndexes = new Vector<>();
        for (int i = 0; i < numberOfCards(); i++) {
            if (getCardByOrder(i).term.equals(term)) {
                cardIndexes.add(getCardByOrder(i).index);
            }
        }
        return cardIndexes;
    }

    @Override
    public void toScreen() {
        for (int i = 0; i < numberOfCards(); i++) {
            System.out.println(getCardByOrder(i).toString());
        }
    }
    
    public void toScreenData() {
        for (int i = 0; i < numberOfCards(); i++) {
            System.out.println(getCardByOrder(i).toStringData());
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < numberOfCards(); i++) {
            out = out + getCardByOrder(i).toStringData() + "\n";
        }
        return out;
    }
}
