package dictionary;

import dictionary.card_comparators.CardComparatorByDefinition;
import dictionary.card_comparators.CardComparatorByDefinitionForGermanLanguage;
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
    
    public List<Card> foundCards = new ArrayList<>();

    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }

    public void setCardCategoryRestrictions(Set<Integer> ccfs) {
        cardCategoriesForSearch = ccfs;
    }
    
    public void setStringToSearch(String s) {
        stringToSearch = s;
    }

    public void setSearchAccordingToTerm() {
        searchAccordingToTerm = true;
    }

    public void setSearchAccordingToDefinition() {
        searchAccordingToTerm = false;
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

    public void makeSearch() {
        foundCards.clear();
        
        if (searchAccordingToTerm) {
            if (cardCategoriesForSearch.isEmpty()) {
                foundCards = getCardsWithGivenTermPart(stringToSearch);
                Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(stringToSearch));
            }
            else {
                foundCards = getCardsWithGivenTermPart(stringToSearch, cardCategoriesForSearch);
                Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(stringToSearch));
            }
        }
        else {
            if (cardCategoriesForSearch.isEmpty()) {
                foundCards = getCardsWithGivenDefinitionPart(stringToSearch);
                Collections.sort(foundCards, new CardComparatorByDefinitionForGermanLanguage(stringToSearch));
            }
            else {
                foundCards = getCardsWithGivenDefinitionPart(stringToSearch, cardCategoriesForSearch);
                Collections.sort(foundCards, new CardComparatorByDefinitionForGermanLanguage(stringToSearch));
            }
        }
    }
    
}
