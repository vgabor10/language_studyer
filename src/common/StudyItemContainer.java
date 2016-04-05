package common;

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

public class StudyItemContainer {

	private Vector<StudyItem> data = new Vector<StudyItem>();

	private Map<Integer,Integer> studyItemIndexToOrderIndex = new HashMap<Integer,Integer>();

	public StudyItemContainer() {
	}

	public int numberOfStudyItems() {
		return data.size();
	}

	public StudyItem getStudyItemByOrder(int orderIndex) {
		return data.get(orderIndex);
	}

	public StudyItem getStudyItemByIndex(int index) {
		return data.get(studyItemIndexToOrderIndex.get(index));
	}

	public Set<Integer> getStudyItemIndexes() {
		Set<Integer> studyItemIndexes = new HashSet<Integer>();

		for (int i=0; i<data.size(); i++) {
			studyItemIndexes.add(data.get(i).index);
		}
		return studyItemIndexes;
	}

	public void addStudyItem(StudyItem si) {
		data.add(si);
		studyItemIndexToOrderIndex.put(si.index, numberOfStudyItems()-1);
	}

	public void removeStudyItemWithIndex(int index) {	//TODO: implement
		int i=0;
		while (i<data.size()) {
			if (data.get(i).index == index) {
				data.remove(index);
			}
			else {
				i++;
			}
		}
	}

	/*public void toScreenCardsWithSameTerm() {	//TODO: move to other class
		Set<String> termData = new HashSet<String>();

		Card[] arrayToSort = new Card[numberOfCards()];

		for (int i=0; i<data.size(); i++) {
			arrayToSort[i] = data.get(i);
		}

		Arrays.sort(arrayToSort, new CardComparatorByTerm());

		String lastTerm = "";
		String actualTerm = "";
		int numberOfCases = 0;
		for (int i=0; i<numberOfCards(); i++) {
			actualTerm = arrayToSort[i].s1;
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

	public void addCardToContainerAndAppenToDiscFile(Card card, String filePath) {	//TODO: think it over
		data.add(card);

		try {
			FileWriter fw = new FileWriter(filePath,true);	//the true will append the new data
			fw.write(card.toStringData() + "\n");		//appends the string to the file
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}

	}

	public void toScreenCardsWithGivenTermPrefix(String prefix, AnswerDataContainer answerDataContainer) {	//TODO: this function should be implemented in other cass
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).s1.startsWith(prefix)) {
				int cardIndex = data.get(i).index;
				System.out.println(data.get(i).toStringData() + " | "
					+ df.format(answerDataContainer.percentageOfRightAnswers(cardIndex)) + "% ("
					+ answerDataContainer.numberOfAnswersOfCard(cardIndex) + ")");
			}
		}
	}

	public void toScreenCardsWithGivenTermPart(String prefix, AnswerDataContainer answerDataContainer) {	//TODO: this function should be implemented in other cass
		DecimalFormat df = new DecimalFormat("#.00");
		int maxListedCards = 30;
		Vector<Card> cardsToList = new Vector<Card>();

		for (int i=0; i<data.size() && cardsToList.size() < maxListedCards; i++) {
			if (data.get(i).s1.toLowerCase().contains(prefix.toLowerCase())) {
				int cardIndex = data.get(i).index;
				cardsToList.add(data.get(i));
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

	public void toScreenCardWithGivenCardIndex(int cardIndex) {	//TODO: this function should be implemented in other cass
		int i=0;
		while (i < data.size() && data.get(i).index != cardIndex) {
			i++;
		}

		if (i<data.size()) {
			System.out.println(data.get(i).toStringData());
		}
		else {
			System.out.println("there is not card with given cardIndex");
		}
	}

	public void toScreenCardsWithGivenDefinitionPart(String definitionPart) {	//TODO: this function should be implemented in other cass
		for (int i=0; i<numberOfCards(); i++) {
			if (getCard(i).s2.contains(definitionPart)) {
				System.out.println(getCard(i).toStringReverse());
			}
		}
	}

	public Vector<Integer> findCardsByTerm(String term) {	//TODO: this function should be implemented in other
		Vector<Integer> cardIndexes = new Vector<Integer>();
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).s1.equals(term)) {
				cardIndexes.add(data.get(i).index);
			}
		}
		return cardIndexes;
	}

	public void loadDataFromFile(String filePath) {	//TODO: this function should be implemented in other
		clear();
		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader( new FileReader(filePath));
			while( (strLine = br.readLine()) != null){
				//System.out.println(strLine);
				Card card = new Card();
				card.setDataFromString(strLine);
				data.addElement(card);
			}
		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}

	public void saveDataToFile(String filePath) {	//TODO: this function should be implemented in other
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
	}*/

	public void clear() {
		data.clear();
	}

	/*public void toScreen() {	//TODO: think it over
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toString());
		}
	}

	public void toScreenData() {	//TODO: think it over
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toStringData());
		}
	}*/
}
