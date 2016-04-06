package dictionary;

import common.*;
import settings_handler.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryDataModificator {

	private CardContainer cardContainer;
	private	AnswerDataContainer answerDataContainer;
	private SettingsHandler settingsHandler;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void setSettingsHandler(SettingsHandler sh) {
		settingsHandler = sh;
	}

	/*public void mergeCardsWithSameData(int cardIndex1, int cardIndex2) {	//for test
		if (cardIndex1 == cardIndex2) {
			System.out.println("card indexes need to be different");
		}
		else
		if (!(cardContainer.getCard(cardIndex1).term.equals(cardContainer.getCard(cardIndex2).term) 
			&& cardContainer.getCard(cardIndex1).definition.equals(cardContainer.getCard(cardIndex2).definition))) {
			System.out.println("cards have not the same data");
		}
		else {
			if (cardIndex1 > cardIndex2) {
				int tmp = cardIndex1;
				cardIndex1 = cardIndex2;
				cardIndex2 = tmp;
			}

			for (int i = cardIndex2 + 1; i < cardContainer.numberOfCards(); i++) {
				cardContainer.getCard(i).index--;
			}
			cardContainer.data.remove(cardIndex2);

			for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
				if (answerDataContainer.data.get(i).index == cardIndex2) {
					answerDataContainer.data.get(i).index = cardIndex1;
				}
				else
				if (answerDataContainer.data.get(i).index > cardIndex2) {
					answerDataContainer.data.get(i).index--;
				}
			}

		SettingsHandler settingsHandler = new SettingsHandler();

		File oldFile;
		oldFile = new File(settingsHandler.getStudiedLanguageAnswerDataPath());
		oldFile.delete();
		answerDataContainer.saveDataToFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		oldFile = new File(settingsHandler.getStudiedLanguageCardDataPath());
		oldFile.delete();
		cardContainer.saveDataToFile(settingsHandler.getStudiedLanguageCardDataPath());

		System.out.println("cards have been merged");
		}
	}*/

	private void saveCardContainerDataToFile() {	//TODO: make it more safe: save new data to file, then delete old data, then rename new data

		String filePath = settingsHandler.getStudiedLanguageCardDataPath();
		File oldFile;
		oldFile = new File(filePath);
		oldFile.delete();

		try {
			FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data
			for (int i=0; i<cardContainer.numberOfCards(); i++) {
				fw.write(cardContainer.getCardByOrder(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	private void saveAnswerDataContainerDataToFile() {	//TODO: make it more safe: save new data to file, then delete old data, then rename new data
		String filePath = settingsHandler.getStudiedLanguageAnswerDataPath();
		File oldFile;
		oldFile = new File(filePath);
		oldFile.delete();

		try {
			FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data
			for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
				fw.write(answerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void mergeCardsWithSameData() {

		int numberOfMergedCards = 0;

		int index1 = 0;
		int index2;

		while (index1 < cardContainer.numberOfCards()) {
			index2 = index1 + 1;
			while (index2 < cardContainer.numberOfCards()) {

				if (cardContainer.getCardByOrder(index1).term.equals(cardContainer.getCardByOrder(index2).term)
					&& cardContainer.getCardByOrder(index1).definition.equals(cardContainer.getCardByOrder(index2).definition)) {

					for (int i = index2 + 1; i < cardContainer.numberOfCards(); i++) {
						cardContainer.getCardByOrder(i).index--;
					}
					cardContainer.removeCardWithIndex(index2);

					for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
						if (answerDataContainer.getAnswerData(i).index == index2) {
							answerDataContainer.getAnswerData(i).index = index1;
						}
						else
							if (answerDataContainer.getAnswerData(i).index > index2) {
							answerDataContainer.getAnswerData(i).index--;
						}
					}

					numberOfMergedCards++;
				}
				else {
					index2++;
				}
			}

			index1++;
		}

		saveCardContainerDataToFile();
		saveAnswerDataContainerDataToFile();

		System.out.println(numberOfMergedCards + " cards have been merged");
	}
}
