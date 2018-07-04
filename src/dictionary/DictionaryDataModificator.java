package dictionary;

import language_studyer.StudyStrategy;
import language_studyer.AnswerDataContainer;
import common.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryDataModificator {
    
    private DictionaryDataContainer dataContainer;
    
    private DiscFilesMetaDataHandler discFilesMetaDataHandler;

    private final Logger logger = new Logger();

    public void setData(DictionaryDataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }
    
    public void setDiscFilesMetaDataHandler(DiscFilesMetaDataHandler discFilesMetaDataHandler) {
        this.discFilesMetaDataHandler = discFilesMetaDataHandler;
    }

    public void removeCardByCardIndex(int cardIndex) {
        dataContainer.getCardContainer().removeByIndex(cardIndex);
        dataContainer.getAnswerDataContainer().removeAnswersWithIndex(cardIndex);

        writeAllDataToFile();
    }
    
    public void removeCardAnswerDataByCardIndex(int cardIndex) {
        dataContainer.getAnswerDataContainer().removeAnswersWithIndex(cardIndex);
        saveAnswerDataContainerDataToFile();
    }    

    public void addCard(Card card) {
        card.index = dataContainer.getCardContainer().getEmptyCardIndex();
        dataContainer.getCardContainer().addCard(card);
        
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
            for (int i = 0; i < dataContainer.getCardContainer().numberOfCards(); i++) {
                Card card = dataContainer.getCardContainer().getCardByOrder(i);
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
            for (int i = 0; i < dataContainer.getAnswerDataContainer().numberOfAnswers(); i++) {
                fw.write(dataContainer.getAnswerDataContainer().getAnswerData(i).toStringData() + "\n");	//appends the string to the file
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
            for (int i = 0; i < dataContainer.getCardContainer().numberOfCards(); i++) {
                Card card = dataContainer.getCardContainer().getCardByOrder(i);
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
            for (int i = 0; i < dataContainer.getCardContainer().numberOfCards(); i++) {
                Card card = dataContainer.getCardContainer().getCardByOrder(i);
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
            
            StudyStrategy studyStrategy = dataContainer.getStudyStrategy();
            
            //the true will append the new data
            FileWriter fw = new FileWriter(discFilesMetaDataHandler.getStudiedLanguageDictionaryStudyStrategy(), false);
            
            fw.write("numberOfRandomCards: " +
                    Integer.toString(studyStrategy.numberOfRandomItems) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown20Percent: "
                    + Integer.toString(studyStrategy.numberOfItemsFromTheLeastKnown20Percent) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown100: "
                    + Integer.toString(studyStrategy.numberOfItemsFromTheLeastKnown100) + "\n");
            
            fw.write("numberOfCardsWithLeastSignificantAr: "
                    + Integer.toString(studyStrategy.numberOfItemsAmongTheLeastSignificantAr) + "\n");

            fw.write("numberOfLatestQuestionedCards: "
                    + Integer.toString(studyStrategy.numberOfLatestQuestionedItems) + "\n");
            
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

            dataContainer.getAnswerDataContainer().appendAnswerDataContainer(answersToAppend);
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
