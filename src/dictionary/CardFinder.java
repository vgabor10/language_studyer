package dictionary;

import common.*;

import java.util.*;
//import java.io.File;
//import java.io.IOException;
//import java.lang.Math;
import java.text.DecimalFormat;

public class CardFinder {

	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void toScreenCardsWithSameTerm() {	//TODO: should be implemented a FoundCardsSchover class
		Set<String> termData = new HashSet<String>();

		Card[] arrayToSort = new Card[cardContainer.numberOfCards()];

		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			arrayToSort[i] = cardContainer.getCardByOrder(i);
		}

		Arrays.sort(arrayToSort, new CardComparatorByTerm());

		String lastTerm = "";
		String actualTerm = "";
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

	public void toScreenCardsWithGivenTermPart(String prefix) {
		DecimalFormat df = new DecimalFormat("#.00");
		int maxListedCards = 30;
		Vector<Card> cardsToList = new Vector<Card>();

		for (int i=0; i < cardContainer.numberOfCards() && cardsToList.size() < maxListedCards; i++) {
			if (cardContainer.getCardByOrder(i).term.toLowerCase().contains(prefix.toLowerCase())) {
				int cardIndex = cardContainer.getCardByOrder(i).index;
				cardsToList.add(cardContainer.getCardByOrder(i));
			}
		}

		Collections.sort(cardsToList, new CardComparatorByTermForGermanLanguange());
		for (int i=0; i < cardsToList.size(); i++) {
			int cardIndex = cardsToList.get(i).index;
			System.out.println(cardsToList.get(i).toStringData() + " | "
					+ df.format(answerDataContainer.getAnswerRateOfCard(cardIndex) * 100) + "% ("
					+ answerDataContainer.numberOfAnswersOfCard(cardIndex) + ")");
		}
		if (cardsToList.size() == maxListedCards) {
			System.out.println("THERE CAN BE MORE CARDS FOUND");
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

	public void toScreenCardsWithGivenDefinitionPart(String definitionPart) {
		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			if (cardContainer.getCardByOrder(i).definition.contains(definitionPart)) {
				System.out.println(cardContainer.getCardByOrder(i).toStringReverse());
			}
		}
	}
}
