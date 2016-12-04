package dictionary;

import dictionary.card_comparators.CardComparatorByDefinition;
import dictionary.card_comparators.CardComparatorByTermForGermanLanguange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardFinder {

    private CardContainer cardContainer;
    
    private Set<Integer> cardCategoriesForSearch  = new HashSet<>();
    private String stringToSearch = "";
    private boolean searchAccordingToTerm = true;

    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }

    public void setCardCategoriesForSearch(Set<Integer> ccfs) {
        cardCategoriesForSearch = ccfs;
    }
    
    public void setStringToSearch(String s) {
        stringToSearch = s;
    }

    public void setSearchAccordingToTerm() {
        searchAccordingToTerm = true;
    }

    public void setSearchAccordingToDefinition() {
        searchAccordingToTerm = true;
    }
    
    private List<Card> getCardsWithGivenTermPart(String termPart) {
        List<Card> foundCards = new ArrayList<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (card.term.toLowerCase().contains(termPart.toLowerCase())) {
                foundCards.add(card);
            }
        }

        return foundCards;
    }
    
    private List<Card> getCardsWithGivenTermPart(String termPart,
            Set<Integer> cardCategoriesInSearch) {
        
        List<Card> foundCards = new ArrayList<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            
            if (!Collections.disjoint(card.categories, cardCategoriesInSearch) &&
                    card.term.toLowerCase().contains(termPart.toLowerCase())) {
                foundCards.add(card);
            }
        }
        
        return foundCards;
    }

    private List<Card> getCardsWithGivenDefinitionPart(String definitionPart) {
        List<Card> cardsToList = new ArrayList<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (card.definition.toLowerCase().contains(definitionPart.toLowerCase())) {
                cardsToList.add(card);
            }
        }

        return cardsToList;
    }
    
    private List<Card> getCardsWithGivenDefinitionPart(String definitionPart,
            Set<Integer> cardCategoriesInSearch) {
        
        List<Card> cardsToList = new ArrayList<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (!Collections.disjoint(card.categories, cardCategoriesInSearch) &&
                    card.definition.toLowerCase().contains(definitionPart.toLowerCase())) {
                cardsToList.add(card);
            }
        }

        return cardsToList;
    }

    public List<Card> getCards() {
        List<Card> cardsToList = new ArrayList<>();
        
        if (searchAccordingToTerm) {
            if (cardCategoriesForSearch.isEmpty()) {
                cardsToList = getCardsWithGivenTermPart(stringToSearch);
                Collections.sort(cardsToList, new CardComparatorByTermForGermanLanguange(stringToSearch));
            }
            else {
                cardsToList = getCardsWithGivenTermPart(stringToSearch, cardCategoriesForSearch);
                Collections.sort(cardsToList, new CardComparatorByTermForGermanLanguange(stringToSearch));
            }
        }
        else {
            if (cardCategoriesForSearch.isEmpty()) {
                getCardsWithGivenDefinitionPart(stringToSearch);
                Collections.sort(cardsToList, new CardComparatorByDefinition());
            }
            else {
                getCardsWithGivenDefinitionPart(stringToSearch, cardCategoriesForSearch);
                Collections.sort(cardsToList, new CardComparatorByDefinition());
            }
        }
        
        return cardsToList;
    }
    
}
