package dictionary;

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

public class CardContainer {

	public Vector<Card> data = new Vector<Card>();

	public int numberOfCards() {
		return data.size();
	}

	public Card getCard(int i) {
		return data.get(i);
	}

	public void toScreenCardsWithSameTerm() {
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

	public void addCardToContainerAndAppenToDiscFile(Card card, String filePath) {
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

	public void toScreenCardsWithGivenTermPrefix(String prefix) {
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).s1.startsWith(prefix)) {
				System.out.println(data.get(i).toStringData());
			}
		}
	}

	public Vector<Integer> findCardsByTerm(String term) {
		Vector<Integer> cardIndexes = new Vector<Integer>();
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).s1.equals(term)) {
				cardIndexes.add(data.get(i).index);
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

	public void clear() {
		data.clear();
	}

	public void toScreen() {
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toString());
		}
	}

	public void toScreenData() {
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toStringData());
		}
	}
}
