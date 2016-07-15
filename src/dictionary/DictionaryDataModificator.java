package dictionary;

import disc_operation_handlers.LanguageFilesDataHendler;
import study_item_objects.AnswerDataContainer;
import common.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryDataModificator {

	private CardContainer cardContainer;
	private	AnswerDataContainer answerDataContainer;
	private final LanguageFilesDataHendler settingsHandler = new LanguageFilesDataHendler();    //TODO: give it from manin frame

	private final Logger logger = new Logger();

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void removeCardWithAnswersByCardIndex(int cardIndex) {
		cardContainer.removeByIndex(cardIndex);
		answerDataContainer.removeAnswersWithIndex(cardIndex);

		saveCardContainerDataToFile();
		saveAnswerDataContainerDataToFile();
	}
        
        public void addCard(Card card) {
            cardContainer.addCard(card);
            saveCardContainerDataToFile();
        }

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

	public void appendToStudiedLanguageCardData(AnswerDataContainer answerDataContainer) {
		try {
                        logger.debug("following rows have been added to card data file: "); 
                    
			//the true will append the new data
			FileWriter fw = new FileWriter(settingsHandler.getStudiedLanguageAnswerDataPath(),true);
			for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
				fw.write(answerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
                                
                                logger.debug(answerDataContainer.getAnswerData(i).toStringData());
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void mergeCardsWithSameData() {

		/*if (logger.isDebug) {
			logger.debug("cardContainer before merge");
			for (int i=0; i<cardContainer.numberOfCards(); i++) {
				logger.debug(cardContainer.getCardByOrder(i).toStringData());
			}

			logger.debug("answerDataContainer before merge");
			for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
				logger.debug(answerDataContainer.getAnswerData(i).toStringData());
			}
		}*/

		logger.debug("start merging cards with same term, definition, group");

		int numberOfMergedCards = 0;

		int orderIndex1 = 0;
		int orderIndex2;

		while (orderIndex1 < cardContainer.numberOfCards()) {
			orderIndex2 = orderIndex1 + 1;
			while (orderIndex2 < cardContainer.numberOfCards()) {

				Card card1 = cardContainer.getCardByOrder(orderIndex1);
				Card card2 = cardContainer.getCardByOrder(orderIndex2);

				boolean equalTerm = card1.term.equals(card2.term);
				boolean equalDefinition = card1.definition.equals(card2.definition);
				boolean equalGroup = card1.group.equals(card2.group);

				if (equalTerm && equalDefinition && equalGroup) {

					logger.debug("following cards has same term, definition, group:");
					logger.debug(card1.toStringData());
					logger.debug(card2.toStringData());

					cardContainer.removeCardWithOrderIndex(orderIndex2);

					logger.debug("card with order index " + orderIndex2 + " is removed");

					for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
						if (answerDataContainer.getAnswerData(i).index == card2.index) {
							answerDataContainer.getAnswerData(i).index = card1.index;
						}
					}

					numberOfMergedCards++;
				}
				else {
					orderIndex2++;
				}
			}

			orderIndex1++;
		}

		if (numberOfMergedCards!=0) {
			saveCardContainerDataToFile();
			logger.debug("card container is saved to disc");
			saveAnswerDataContainerDataToFile();
			logger.debug("answerDataContainer is saved to disc");
		}

		System.out.println(numberOfMergedCards + " cards have been merged");

		logger.debug("end merging cards with same term, definition, group");

		/*if (logger.isDebug) {
			logger.debug("cardContainer after merge");
			for (int i=0; i<cardContainer.numberOfCards(); i++) {
				logger.debug(cardContainer.getCardByOrder(i).toStringData());
			}

			logger.debug("answerDataContainer after merge");
			for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
				logger.debug(answerDataContainer.getAnswerData(i).toStringData());
			}
		}*/

	}
}
