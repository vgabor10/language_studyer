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

	public Card getCard(int i) {	//TODO: rename: getCardByIndex
		return (Card)getStudyItemByOrder(i);
	}

	public void addCard(Card c) {
		addStudyItem(c);
	}

	public void removeCardWithIndex(int index) {	//TODO: take to other class
		removeStudyItemWithIndex(index);
	}

	public void toScreenCardsWithSameTerm() {
		Set<String> termData = new HashSet<String>();

		Card[] arrayToSort = new Card[numberOfCards()];

		for (int i=0; i<numberOfCards(); i++) {
			arrayToSort[i] = (Card)getCard(i);
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
			if (getCard(i).term.startsWith(prefix)) {
				int cardIndex = getCard(i).index;
				System.out.println(getCard(i).toStringData() + " | "
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
			if (getCard(i).term.toLowerCase().contains(prefix.toLowerCase())) {
				int cardIndex = getCard(i).index;
				cardsToList.add(getCard(i));
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
		while (i < numberOfCards() && getCard(i).index != cardIndex) {
			i++;
		}

		if (i<numberOfCards()) {
			System.out.println(getCard(i).toStringData());
		}
		else {
			System.out.println("there is not card with given cardIndex");
		}
	}

	public void toScreenCardsWithGivenDefinitionPart(String definitionPart) {	// this function should be implemented in other cass
		for (int i=0; i<numberOfCards(); i++) {
			if (getCard(i).definition.contains(definitionPart)) {
				System.out.println(getCard(i).toStringReverse());
			}
		}
	}

	public Vector<Integer> findCardsByTerm(String term) {
		Vector<Integer> cardIndexes = new Vector<Integer>();
		for (int i=0; i<numberOfCards(); i++) {
			if (getCard(i).term.equals(term)) {
				cardIndexes.add(getCard(i).index);
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
				fw.write(getCard(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void toScreen() {
		for(int i=0; i<numberOfCards(); i++){
			System.out.println(getCard(i).toString());
		}
	}

	public void toScreenData() {
		for(int i=0; i<numberOfCards(); i++){
			System.out.println(getCard(i).toStringData());
		}
	}
}
