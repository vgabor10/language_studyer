package dictionary;

import dictionary.card_comparators.CardComparatorByDefinition;
import dictionary.card_comparators.CardComparatorByTerm;
import dictionary.card_comparators.CardComparatorByTermForGermanLanguange;
import study_item_objects.AnswerDataContainer;

import java.util.*;
import java.text.DecimalFormat;

public class CardFinder {

	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;    //TODO: is necessery?

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void toScreenCardsWithSameTerm() {	//TODO: should be implemented a FoundCardsSchover class
		Set<String> termData = new HashSet<>();

		Card[] arrayToSort = new Card[cardContainer.numberOfCards()];

		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			arrayToSort[i] = cardContainer.getCardByOrder(i);
		}

		Arrays.sort(arrayToSort, new CardComparatorByTerm());

		String lastTerm = "";
		String actualTerm;
		int numberOfCases = 0;
		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			actualTerm = arrayToSort[i].term;
			if (actualTerm.equals(lastTerm)) {
				numberOfCases++;
				if (numberOfCases == 1) {
					System.out.println(arrayToSort[i-1].toStringData());
					System.out.println(arrayToSort[i].toStringData());
				}
				else {
					System.out.println(arrayToSort[i].toStringData());
				}
			}
			else {
				numberOfCases = 0;
			}
			lastTerm = actualTerm;
		}
	}

	public void toScreenAllCards() {
		for(int i=0; i<cardContainer.numberOfCards(); i++){
			System.out.println(cardContainer.getCardByOrder(i).toStringData());
		}
	}

	public void toScreenCardsWithGivenTermPrefix(String prefix) {
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=0; i < cardContainer.numberOfCards(); i++) {
			if (cardContainer.getCardByOrder(i).term.startsWith(prefix)) {
				int cardIndex = cardContainer.getCardByOrder(i).index;
				System.out.println(cardContainer.getCardByOrder(i).toStringData() + " | "
					+ df.format(answerDataContainer.percentageOfRightAnswers(cardIndex)) + "% ("
					+ answerDataContainer.numberOfAnswersOfCard(cardIndex) + ")");
			}
		}
	}

	public void toScreenCardsWithGivenTermPart(String prefix) { //TODO: remove
		int maxListedCards = 31;
		Vector<Card> cardsToList = new Vector<Card>();

		for (int i=0; i < cardContainer.numberOfCards(); i++) {
			if (cardContainer.getCardByOrder(i).term.toLowerCase().contains(prefix.toLowerCase())) {
				Card card = cardContainer.getCardByOrder(i);
				cardsToList.add(card);
			}
		}

		Collections.sort(cardsToList, new CardComparatorByTermForGermanLanguange());

		System.out.println();
		System.out.println("TERM - DEFINITION");
		for (int i=0; i < cardsToList.size() && i < maxListedCards; i++) {
			System.out.println(cardsToList.get(i).toString());
		}

		if (cardsToList.size() > maxListedCards) {
			System.out.println("MORE CARDS HAS BEEN FOUND");
		}
	}
        
        public Vector<Card> getCardsWithGivenTermPart(String termPart) {
		Vector<Card> foundCards = new Vector<>();

		for (int i=0; i < cardContainer.numberOfCards(); i++) {
                        Card card = cardContainer.getCardByOrder(i);
			if (card.term.toLowerCase().contains(termPart.toLowerCase())) {
				foundCards.add(card);
			}
		}
                
		Collections.sort(foundCards, new CardComparatorByTermForGermanLanguange());
                return foundCards;
        }

	public void toScreenCardsWithGivenDefinitionPart(String definitionPart) {

		int maxListedCards = 31;
		Vector<Card> cardsToList = new Vector<>();

		for (int i=0; i < cardContainer.numberOfCards(); i++) {
			if (cardContainer.getCardByOrder(i).definition.toLowerCase().contains(definitionPart.toLowerCase())) {
				Card card = cardContainer.getCardByOrder(i);
				cardsToList.add(card);
			}
		}

		Collections.sort(cardsToList, new CardComparatorByDefinition());

		System.out.println();
		System.out.println("DEFINITION - TERM");
		for (int i=0; i < cardsToList.size() && i < maxListedCards; i++) {
			System.out.println(cardsToList.get(i).toStringReverse());
		}

		if (cardsToList.size() > maxListedCards) {
			System.out.println("MORE CARDS HAS BEEN FOUND");
		}
	}

	public void toScreenCardWithGivenCardIndex(int cardIndex) {
		int i=0;
		while (i < cardContainer.numberOfCards() && cardContainer.getCardByOrder(i).index != cardIndex) {
			i++;
		}

		if (i<cardContainer.numberOfCards()) {
			System.out.println(cardContainer.getCardByOrder(i).toStringData());
		}
		else {
			System.out.println("there is not card with given cardIndex");
		}
	}

}
