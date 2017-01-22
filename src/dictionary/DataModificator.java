package dictionary;

import language_studyer.AnswerDataContainer;
import common.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataModificator {
    
    private DataContainer dataContainer;
    
    private DiscFilesMetaDataHandler discFilesMetaDataHandler;

    private final Logger logger = new Logger();

    public void setData(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }
    
    public void setDiscFilesMetaDataHandler(DiscFilesMetaDataHandler discFilesMetaDataHandler) {
        this.discFilesMetaDataHandler = discFilesMetaDataHandler;
    }

    public void removeCardByCardIndex(int cardIndex) {
        dataContainer.cardContainer.removeByIndex(cardIndex);
        dataContainer.answerDataContainer.removeAnswersWithIndex(cardIndex);

        writeAllDataToFile();
    }
    
    public void removeCardAnswerDataByCardIndex(int cardIndex) {
        dataContainer.answerDataContainer.removeAnswersWithIndex(cardIndex);
        saveAnswerDataContainerDataToFile();
    }    

    public void addCard(Card card) {
        card.index = dataContainer.cardContainer.getEmptyCardIndex();
        dataContainer.cardContainer.addCard(card);
        
        saveCardContainerDataToFile();
        saveExampleSentencesDataToFile();
        saveCardIndexesAndCategoryIndexesDataToFile();
    }

    //TODO: make it more safe: save new data to file, then delete old data, then rename new data
    public void saveCardContainerDataToFile() {
        String filePath = discFilesMetaDataHandler.getStudiedLanguageCardDataPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < dataContainer.cardContainer.numberOfCards(); i++) {
                Card card = dataContainer.cardContainer.getCardByOrder(i);
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
        String filePath = discFilesMetaDataHandler.getStudiedLanguageAnswerDataPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < dataContainer.answerDataContainer.numberOfAnswers(); i++) {
                fw.write(dataContainer.answerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    public void saveExampleSentencesDataToFile() {
        String filePath = discFilesMetaDataHandler.getStudiedLanguageExampleSentencesDataPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < dataContainer.cardContainer.numberOfCards(); i++) {
                Card card = dataContainer.cardContainer.getCardByOrder(i);
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
        String filePath = discFilesMetaDataHandler.getStudiedLanguageCardAndCategoryIndexesPath();
        File oldFile;
        oldFile = new File(filePath);
        oldFile.delete();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < dataContainer.cardContainer.numberOfCards(); i++) {
                Card card = dataContainer.cardContainer.getCardByOrder(i);
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
    
    public void writeStudyStrategyDataToDisc() {
        try {
            
            StudyStrategy studyStrategy = dataContainer.studyStrategy;
            
            //the true will append the new data
            FileWriter fw = new FileWriter(discFilesMetaDataHandler.getStudiedLanguageDictionaryStudyStrategy(), false);
            
            fw.write("numberOfRandomCards: " +
                    Integer.toString(studyStrategy.numberOfRandomCards) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown20Percent: "
                    + Integer.toString(studyStrategy.numberOfCardsFromTheLeastKnown20Percent) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown100: "
                    + Integer.toString(studyStrategy.numberOfCardsFromTheLeastKnown100) + "\n");
            
            fw.write("numberOfCardsWithLeastSignificantAr: "
                    + Integer.toString(studyStrategy.numberOfCardsAmongTheLeastSignificantAr) + "\n");

            fw.write("numberOfLatestQuestionedCards: "
                    + Integer.toString(studyStrategy.numberOfLatestQuestionedCards) + "\n");
            
            fw.write("studyingGradually: "
                    + Boolean.toString(studyStrategy.studyingGradually) + "\n");
            
            fw.write("cardCategoryRestrictions:");
            for (int categoryIndex : studyStrategy.cardCategoryRestrictions) {
                fw.write(" " + categoryIndex);
            }
            fw.write("\n");
            
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }           
    }

    public void writeAllDataToFile() {
        saveCardContainerDataToFile();
        saveAnswerDataContainerDataToFile();
        saveExampleSentencesDataToFile();
        saveCardIndexesAndCategoryIndexesDataToFile();
    }
    
    public void appendToAnswerDataFile(AnswerDataContainer answersToAppend) {
        try {
            logger.debug("following rows have been added to card data file: ");

            //the true will append the new data
            FileWriter fw = new FileWriter(discFilesMetaDataHandler.getStudiedLanguageAnswerDataPath(), true);
            for (int i = 0; i < answersToAppend.numberOfAnswers(); i++) {
                fw.write(answersToAppend.getAnswerData(i).toStringData() + "\n");	//appends the string to the file

                logger.debug(answersToAppend.getAnswerData(i).toStringData());
            }
            fw.close();

            dataContainer.answerDataContainer.appendAnswerDataContainer(answersToAppend);
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
