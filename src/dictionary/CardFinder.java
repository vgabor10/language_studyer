package dictionary;

import dictionary.card_comparators.CardComparatorByDefinitionForGermanLanguage;
import dictionary.card_comparators.CardComparatorByTermForGermanLanguange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardFinder {

    private CardContainer cardContainer;
    private boolean useCategoryRestrictions = false;
    private Set<Integer> cardCategoriesRestriction  = new HashSet<>();
    private String stringToSearch = "";
    private boolean searchAccordingToTerm = true;
    
    public List<Card> foundCards = new ArrayList<>();

    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }

    public void setCategoryRestrictionUsage(boolean isUsed) {
        useCategoryRestrictions = isUsed;
        
        if (!isUsed) {
            cardCategoriesRestriction.clear();
        }
    }
    
    public void setCardCategoryRestrictions(Set<Integer> ccfs) {
        cardCategoriesRestriction = ccfs;
    }

    public Set<Integer> getCardCategoryRestrictions() {
        return cardCategoriesRestriction;
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
        List<Card> cardsToList = new ArrayList<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (card.term.toLowerCase().contains(termPart.toLowerCase())) {
                cardsToList.add(card);
            }
        }

        return cardsToList;
    }
    
    private List<Card> getCardsWithGivenTermPart(String termPart,
            Set<Integer> cardCategoriesInSearch) {
        
        List<Card> cardsToList = new ArrayList<>();

        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            
            if (!Collections.disjoint(card.categoryIndexes, cardCategoriesInSearch) &&
                    card.term.toLowerCase().contains(termPart.toLowerCase())) {
                cardsToList.add(card);
            }
        }
        
        return cardsToList;
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
            if (!Collections.disjoint(card.categoryIndexes, cardCategoriesInSearch) &&
                    card.definition.toLowerCase().contains(definitionPart.toLowerCase())) {
                cardsToList.add(card);
            }
        }

        return cardsToList;
    }

    public void listAllCard() {
        foundCards.clear();

        if (searchAccordingToTerm) {
            if (!useCategoryRestrictions) {
                foundCards = getCardsWithGivenTermPart("");
                Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(""));
            }
            else {
                foundCards = getCardsWithGivenTermPart("", cardCategoriesRestriction);
                Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(""));
            }
        }
        else {
            if (!useCategoryRestrictions) {
                foundCards = getCardsWithGivenDefinitionPart("");
                Collections.sort(foundCards, new CardComparatorByDefinitionForGermanLanguage(""));
            }
            else {
                foundCards = getCardsWithGivenDefinitionPart("", cardCategoriesRestriction);
                Collections.sort(foundCards, new CardComparatorByDefinitionForGermanLanguage(""));
            }
        }
    }
    
    public void makeSearch() {
        foundCards.clear();
        
        if (stringToSearch.equals("")) {}
        else
        if (searchAccordingToTerm) {
            if (!useCategoryRestrictions) {
                foundCards = getCardsWithGivenTermPart(stringToSearch);
                Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(stringToSearch));
            }
            else {
                foundCards = getCardsWithGivenTermPart(stringToSearch, cardCategoriesRestriction);
                Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange(stringToSearch));
            }
        }
        else {
            if (!useCategoryRestrictions) {
                foundCards = getCardsWithGivenDefinitionPart(stringToSearch);
                Collections.sort(foundCards, new CardComparatorByDefinitionForGermanLanguage(stringToSearch));
            }
            else {
                foundCards = getCardsWithGivenDefinitionPart(stringToSearch, cardCategoriesRestriction);
                Collections.sort(foundCards, new CardComparatorByDefinitionForGermanLanguage(stringToSearch));
            }
        }
    }
    
}
