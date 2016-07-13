package dictionary;

import study_item_objects.StudyItemContainer;

import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

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

	public Set<Integer> getCardIndexes() {
		return getStudyItemIndexes();
	}

	public int getEmptyCardIndex() {
		int emptyCardIndex = 100000;
		Set<Integer> cardIndexes = getStudyItemIndexes();
		while (cardIndexes.contains(emptyCardIndex)) {
			emptyCardIndex++;
		}
		return emptyCardIndex;
	}

	public void removeCardWithOrderIndex(int orderIndex) {
		removeStudyItemWithOrderIndex(orderIndex);
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
		Vector<Integer> cardIndexes = new Vector<>();
		for (int i=0; i<numberOfCards(); i++) {
			if (getCardByOrder(i).term.equals(term)) {
				cardIndexes.add(getCardByOrder(i).index);
			}
		}
		return cardIndexes;
	}

	public void loadDataFromFile(String filePath) {	//TODO: take to other class
		clear();
		BufferedReader br;
		String strLine;
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

        @Override
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
        
        @Override
        public String toString() {
           String out = "";
           for (int i=0; i<numberOfCards(); i++) {
               out = out + getCardByOrder(i).toStringData() + "\n";
           }
           return out;
        }
}
