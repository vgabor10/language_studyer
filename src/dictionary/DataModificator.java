package dictionary;

import study_item_objects.AnswerDataContainer;
import common.Logger;
import dictionary.Card;
import dictionary.CardContainer;
import dictionary.DataContainer;
import disc_operation_handlers.LanguageFilesDataHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataModificator {
    
    private CardContainer cardContainer;
    private AnswerDataContainer answerDataContainer;
    private final LanguageFilesDataHandler settingsHandler = new LanguageFilesDataHandler();    //TODO: give it from manin frame

    private final Logger logger = new Logger();

    public void setData(DataContainer dictionaryDataContainer) {
        cardContainer = dictionaryDataContainer.cardContainer;
        answerDataContainer = dictionaryDataContainer.answerDataContainer;
    }

    public void removeCardWithAnswersByCardIndex(int cardIndex) {
        cardContainer.removeByIndex(cardIndex);
        answerDataContainer.removeAnswersWithIndex(cardIndex);

        saveCardContainerDataToFile();
        saveAnswerDataContainerDataToFile();
        saveExampleSentencesDataToFile();
    }
    
    public void removeCardAnswerDataByCardIndex(int cardIndex) {
        answerDataContainer.removeAnswersWithIndex(cardIndex);
        saveAnswerDataContainerDataToFile();
    }    

    public void addCard(Card card) {
        card.index = cardContainer.getEmptyCardIndex();
        cardContainer.addCard(card);
        saveCardContainerDataToFile();
        saveExampleSentencesDataToFile();
    }

    //TODO: make it more safe: save new data to file, then delete old data, then rename new data
    public void saveCardContainerDataToFile() {
        String filePath = settingsHandler.getStudiedLanguageCardDataPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < cardContainer.numberOfCards(); i++) {
                Card card = cardContainer.getCardByOrder(i);
                fw.write(Integer.toString(card.index) + "\t" +
                    card.term + "\t" + card.definition + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    } 

    //TODO: make it more safe: save new data to file, then delete old data, then rename new data
    private void saveAnswerDataContainerDataToFile() {
        String filePath = settingsHandler.getStudiedLanguageAnswerDataPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
                fw.write(answerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    public void saveExampleSentencesDataToFile() {
        String filePath = settingsHandler.getStudiedLanguageExampleSentencesDataPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < cardContainer.numberOfCards(); i++) {
                Card card = cardContainer.getCardByOrder(i);
                for (String exampleSentence : card.exampleSentences) {
                    fw.write(card.index + "\t" + exampleSentence  + "\n");	//appends the string to the file
                }
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    public void saveCardIndexesAndCategoryIndexesDataToFile() {
        String filePath = settingsHandler.getStudiedLanguageCardAndCategoryIndexesPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < cardContainer.numberOfCards(); i++) {
                Card card = cardContainer.getCardByOrder(i);
                if (!card.categoryIndexes.isEmpty()) {
                    String outString = Integer.toString(card.index);
                    for (int categoryIndex : card.categoryIndexes) {
                        outString = outString + "\t" + Integer.toString(categoryIndex);
                    }
                    fw.write(outString  + "\n");
                }
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void saveAllData() {
        saveCardContainerDataToFile();
        saveAnswerDataContainerDataToFile();
        saveExampleSentencesDataToFile();
        saveCardIndexesAndCategoryIndexesDataToFile();
    }
    
    public void appendToStudiedLanguageCardData(AnswerDataContainer answersToAppend) {
        try {
            logger.debug("following rows have been added to card data file: ");

            //the true will append the new data
            FileWriter fw = new FileWriter(settingsHandler.getStudiedLanguageAnswerDataPath(), true);
            for (int i = 0; i < answersToAppend.numberOfAnswers(); i++) {
                fw.write(answersToAppend.getAnswerData(i).toStringData() + "\n");	//appends the string to the file

                logger.debug(answersToAppend.getAnswerData(i).toStringData());
            }
            fw.close();

            answerDataContainer.appendAnswerDataContainer(answersToAppend);
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
