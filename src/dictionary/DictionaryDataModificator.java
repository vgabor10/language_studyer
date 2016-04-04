package dictionary;

import common.*;
import settings_handler.*;

import java.io.File;

public class DictionaryDataModificator {

	private CardContainer cardContainer;
	private	AnswerDataContainer answerDataContainer;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	/*public void mergeCardsWithSameData(int cardIndex1, int cardIndex2) {	//for test
		if (cardIndex1 == cardIndex2) {
			System.out.println("card indexes need to be different");
		}
		else
		if (!(cardContainer.getCard(cardIndex1).s1.equals(cardContainer.getCard(cardIndex2).s1) 
			&& cardContainer.getCard(cardIndex1).s2.equals(cardContainer.getCard(cardIndex2).s2))) {
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

	public void mergeCardsWithSameData() {

		int numberOfMergedCards = 0;

		int index1 = 0;
		int index2;

		while (index1 < cardContainer.numberOfCards()) {
			index2 = index1 + 1;
			while (index2 < cardContainer.numberOfCards()) {

				if (cardContainer.getCard(index1).s1.equals(cardContainer.getCard(index2).s1)
					&& cardContainer.getCard(index1).s2.equals(cardContainer.getCard(index2).s2)) {

					for (int i = index2 + 1; i < cardContainer.numberOfCards(); i++) {
						cardContainer.getCard(i).index--;
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

		SettingsHandler settingsHandler = new SettingsHandler();

		File oldFile;
		oldFile = new File(settingsHandler.getStudiedLanguageAnswerDataPath());
		oldFile.delete();
		answerDataContainer.saveDataToFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		oldFile = new File(settingsHandler.getStudiedLanguageCardDataPath());
		oldFile.delete();
		cardContainer.saveDataToFile(settingsHandler.getStudiedLanguageCardDataPath());

		System.out.println(numberOfMergedCards + " cards have been merged");
	}
}
