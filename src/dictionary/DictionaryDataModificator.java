package dictionary;

import language_studyer.AnswerDataContainer;
import common.Logger;
import language_studyer.DataModificator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryDataModificator extends DataModificator {

    private final Logger logger = new Logger();

    public DictionaryDataContainer getDictionaryDataContainer() {
        return (DictionaryDataContainer) dataContainer;
    }
    
    public void removeCardByCardIndex(int cardIndex) {
        getDictionaryDataContainer().getCardContainer().removeByIndex(cardIndex);
        getDictionaryDataContainer().getAnswerDataContainer().removeAnswersWithIndex(cardIndex);

        writeAllDataToFile();
    }
    
    public void removeCardAnswerDataByCardIndex(int cardIndex) {
        dataContainer.getAnswerDataContainer().removeAnswersWithIndex(cardIndex);
        saveAnswerDataContainerDataToFile();
    }    

    public void addCard(Card card) {
        card.index = getDictionaryDataContainer().getCardContainer().getEmptyCardIndex();
        getDictionaryDataContainer().getCardContainer().addStudyItem(card);
        
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
            for (int i = 0; i < getDictionaryDataContainer().getCardContainer()
                    .numberOfStudyItems(); i++) {
                
                Card card = (Card) getDictionaryDataContainer().getCardContainer()
                        .getStudyItemByOrder(i);
                
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
            for (int i = 0; i < getDictionaryDataContainer().getCardContainer()
                    .numberOfStudyItems(); i++) {
                
                Card card = (Card) getDictionaryDataContainer().getCardContainer()
                        .getStudyItemByOrder(i);
                
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
            for (int i = 0; i < getDictionaryDataContainer().getCardContainer()
                    .numberOfStudyItems(); i++) {
                
                Card card = (Card) getDictionaryDataContainer().getCardContainer()
                        .getStudyItemByOrder(i);
                
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

    public void writeDictionaryStudyStrategyDataToDisc() {
        String filePath 
                = discFilesMetaDataHandler.getStudiedLanguageDictionaryStudyStrategyPath();
        
        writeStudyStrategyDataToDisc(filePath);
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
