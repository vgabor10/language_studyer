package dictionary;

import language_studyer.DataLoader;
import language_studyer.StudyStrategy;
import language_studyer.Category;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import language_studyer.AnswerData;
import language_studyer.AnswerDataContainer;
import language_studyer.DiscFilesMetaDataHandler;

public class DictionaryDataLoader extends DataLoader {
    
    private DiscFilesMetaDataHandler discFilesMetaDataHandler;
    
    public void setDictionaryDataContainer(DictionaryDataContainer dc) {
        this.dataContainer = dc;
    }
    
    public DictionaryDataContainer getDictionaryDataContainer() {
        return (DictionaryDataContainer) this.dataContainer;
    }

    public void setLanguageFilesDataHandler(DiscFilesMetaDataHandler discFilesMetaDataHandler) {
        this.discFilesMetaDataHandler = discFilesMetaDataHandler;
    }
    
    public void loadCardContainer() {
        try {        
            CardContainer cardContainer = getDictionaryDataContainer().getCardContainer();
            cardContainer.clear();
            
            String filePath = discFilesMetaDataHandler.getStudiedLanguageCardDataPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] cardVariables = strLine.split("\t");

                Card card = new Card();
                card.index = Integer.parseInt(cardVariables[0]);
                card.term = cardVariables[1];
                card.definition = cardVariables[2];

                cardContainer.addCard(card);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find card data file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }
    
    public void loadAnswerData() {
        try {
            AnswerDataContainer answerDataContainer = dataContainer.getAnswerDataContainer();
            answerDataContainer.clear();
            
            String filePath = discFilesMetaDataHandler.getStudiedLanguageAnswerDataPath();
            String strLine;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((strLine = br.readLine()) != null) {
                AnswerData answerData = new AnswerData();
                answerData.setDataFromString(strLine);
                answerDataContainer.addAnswerData(answerData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find answer data file");
        } catch (IOException e) {
            System.err.println("exception loadAnswerDataFromDisc function");
        }
    }
    
    public void loadExampleSentences() {
        try {
            String filePath = discFilesMetaDataHandler.getStudiedLanguageExampleSentencesDataPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                
                int cardIndex = Integer.parseInt(splittedRow[0]);
                String exampleSentence = splittedRow[1];
                
                getDictionaryDataContainer().getCardContainer()
                        .getCardByIndex(cardIndex).exampleSentences.add(exampleSentence);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find example_sentences file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }
    
    public void loadCardIndexesAndCategoryIndexes() {
        String filePath = discFilesMetaDataHandler
                .getStudiedLanguageCardAndCategoryIndexesPath();
        
        loadStudyItemIndexesAndCategoryIndexes(filePath);
    }
    
    public void loadCategoryIndexesAndCategoryNames() {
        String filePath = discFilesMetaDataHandler
                .getStudiedLanguageCardCategoryIndexesAndCategoryNames();
        
        loadCategoryIndexesAndCategoryNames(filePath);
    }    
    
    public void loadStudyStrategyDataFromDisc() {
        String filePath = discFilesMetaDataHandler
                .getStudiedLanguageDictionaryStudyStrategyPath();
        
        loadStudyStrategyDataFromDisc(filePath);
    }

    
    public void loadAllData() {
        dataContainer.clear();
        
        loadCardContainer();
        loadAnswerData();
        loadExampleSentences();
        loadCardIndexesAndCategoryIndexes();
        loadCategoryIndexesAndCategoryNames();
        loadStudyStrategyDataFromDisc();
        
        dataContainer.fillAuxiliaryDataContainer();
    }
}
