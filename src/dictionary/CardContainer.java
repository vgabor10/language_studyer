package dictionary;

import common.*;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.text.DecimalFormat;

public class CardContainer extends StudyItemContainer {

	public int numberOfCards() {
		return numberOfStudyItems();
	}

	public Card getCardByIndex(int i) {
		return (Card)getStudyItemByIndex(i);
	}

	public Card getCardByOrder(int orderIndex) {
		return (Card)getStudyItemByOrder(orderIndex);
	}

	public void addCard(Card c) {
		addStudyItem(c);
	}

	public Set<Integer> getCardIndexes() {	//TODO implement it in the anchestor class
		Set<Integer> cardIndexes = new HashSet<Integer>();
		for (int i=0; i<numberOfCards(); i++) {
			cardIndexes.add(getCardByOrder(i).index);
		}

		return cardIndexes;
	}

	public void removeCardWithIndex(int index) {	//TODO: take to other class
		removeStudyItemWithIndex(index);
	}

	public void toScreenCardsWithSameTerm() {
		Set<String> termData = new HashSet<String>();

		Card[] arrayToSort = new Card[numberOfCards()];

		for (int i=0; i<numberOfCards(); i++) {
			arrayToSort[i] = getCardByOrder(i);
		}

		Arrays.sort(arrayToSort, new CardComparatorByTerm());

		String lastTerm = "";
		String actualTerm = "";
		int numberOfCases = 0;
		for (int i=0; i<numberOfCards(); i++) {
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

	public void addCardToContainerAndAppenToDiscFile(Card card, String filePath) {
		addStudyItem(card);

		try {
			FileWriter fw = new FileWriter(filePath,true);	//the true will append the new data
			fw.write(card.toStringData() + "\n");		//appends the string to the file
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}

	}

	public void toScreenCardsWithGivenTermPrefix(String prefix, AnswerDataContainer answerDataContainer) {	// this function should be implemented in other cass
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=0; i < numberOfCards(); i++) {
			if (getCardByOrder(i).term.startsWith(prefix)) {
				int cardIndex = getCardByOrder(i).index;
				System.out.println(getCardByOrder(i).toStringData() + " | "
					+ df.format(answerDataContainer.percentageOfRightAnswers(cardIndex)) + "% ("
					+ answerDataContainer.numberOfAnswersOfCard(cardIndex) + ")");
			}
		}
	}

	public void toScreenCardsWithGivenTermPart(String prefix, AnswerDataContainer answerDataContainer) {	// this function should be implemented in other cass
		DecimalFormat df = new DecimalFormat("#.00");
		int maxListedCards = 30;
		Vector<Card> cardsToList = new Vector<Card>();

		for (int i=0; i < numberOfCards() && cardsToList.size() < maxListedCards; i++) {
			if (getCardByOrder(i).term.toLowerCase().contains(prefix.toLowerCase())) {
				int cardIndex = getCardByOrder(i).index;
				cardsToList.add(getCardByOrder(i));
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

	public void toScreenCardWithGivenCardIndex(int cardIndex) {	// this function should be implemented in other cass
		int i=0;
		while (i < numberOfCards() && getCardByOrder(i).index != cardIndex) {
			i++;
		}

		if (i<numberOfCards()) {
			System.out.println(getCardByOrder(i).toStringData());
		}
		else {
			System.out.println("there is not card with given cardIndex");
		}
	}

	public void toScreenCardsWithGivenDefinitionPart(String definitionPart) {	// this function should be implemented in other cass
		for (int i=0; i<numberOfCards(); i++) {
			if (getCardByOrder(i).definition.contains(definitionPart)) {
				System.out.println(getCardByOrder(i).toStringReverse());
			}
		}
	}

	public Vector<Integer> findCardsByTerm(String term) {
		Vector<Integer> cardIndexes = new Vector<Integer>();
		for (int i=0; i<numberOfCards(); i++) {
			if (getCardByOrder(i).term.equals(term)) {
				cardIndexes.add(getCardByOrder(i).index);
			}
		}
		return cardIndexes;
	}

	public void loadDataFromFile(String filePath) {
		clear();
		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader( new FileReader(filePath));
			while( (strLine = br.readLine()) != null){
				//System.out.println(strLine);	//log

				String[] cardVariables = strLine.split("\t");

				Card card = new Card();
				card.index = Integer.parseInt(cardVariables[0]);
				card.term = cardVariables[1];
				card.definition = cardVariables[2];
				if (3 < cardVariables.length) {
					card.group = cardVariables[3];
				}

				addCard(card);
			}
		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}

	public void saveDataToFile(String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data
			for (int i=0; i<numberOfCards(); i++) {
				fw.write(getCardByOrder(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void toScreen() {
		for(int i=0; i<numberOfCards(); i++){
			System.out.println(getCardByOrder(i).toString());
		}
	}

	public void toScreenData() {
		for(int i=0; i<numberOfCards(); i++){
			System.out.println(getCardByOrder(i).toStringData());
		}
	}
}
