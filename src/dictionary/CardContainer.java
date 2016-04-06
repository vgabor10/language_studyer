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

	public void addCardToContainerAndAppenToDiscFile(Card card, String filePath) {	//TODO: take to an other class
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

	public Vector<Integer> findCardsByTerm(String term) {	//TODO: take to CardFinderClass
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
