package dictionary;

import dictionary.card_comparators.CardComparatorByDefinition;
import dictionary.card_comparators.CardComparatorByTerm;
import dictionary.card_comparators.CardComparatorByTermForGermanLanguange;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class CardFinder {

    private CardContainer cardContainer;

    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }

    //TODO: should integrate to gui
    public void toScreenCardsWithSameTerm() {
        Set<String> termData = new HashSet<>();

        Card[] arrayToSort = new Card[cardContainer.numberOfCards()];

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            arrayToSort[i] = cardContainer.getCardByOrder(i);
        }

        Arrays.sort(arrayToSort, new CardComparatorByTerm());

        String lastTerm = "";
        String actualTerm;
        int numberOfCases = 0;
        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            actualTerm = arrayToSort[i].term;
            if (actualTerm.equals(lastTerm)) {
                numberOfCases++;
                if (numberOfCases == 1) {
                    System.out.println(arrayToSort[i - 1].toStringData());
                    System.out.println(arrayToSort[i].toStringData());
                } else {
                    System.out.println(arrayToSort[i].toStringData());
                }
            } else {
                numberOfCases = 0;
            }
            lastTerm = actualTerm;
        }
    }

    public Vector<Card> getCardsWithGivenTermPart(String termPart) {
        Vector<Card> foundCards = new Vector<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (card.term.toLowerCase().contains(termPart.toLowerCase())) {
                foundCards.add(card);
            }
        }

        Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(termPart));
        return foundCards;
    }

    public Vector<Card> getCardsWithGivenDefinitionPart(String definitionPart) {
        Vector<Card> cardsToList = new Vector<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (card.definition.toLowerCase().contains(definitionPart.toLowerCase())) {
                cardsToList.add(card);
            }
        }

        Collections.sort(cardsToList, new CardComparatorByDefinition());

        return cardsToList;
    }

}
