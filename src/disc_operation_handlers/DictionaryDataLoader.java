package disc_operation_handlers;

import dictionary.Card;
import dictionary.CardContainer;
import dictionary.DictionaryDataContainer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import study_item_objects.AnswerData;
import study_item_objects.AnswerDataContainer;

public class DictionaryDataLoader {
    
    public DictionaryDataContainer dictionaryDataContainer;
    
    public LanguageFilesDataHandler languageFilesDataHandler;
    
    public void loadCardContainer() {
        try {        
            CardContainer cardContainer = dictionaryDataContainer.cardContainer;
            cardContainer.clear();
            
            String filePath = languageFilesDataHandler.getStudiedLanguageCardDataPath();
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
            AnswerDataContainer answerDataContainer = dictionaryDataContainer.answerDataContainer;
            answerDataContainer.clear();
            
            String filePath = languageFilesDataHandler.getStudiedLanguageAnswerDataPath();
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
            String filePath = languageFilesDataHandler.getStudiedLanguageExampleSentencesDataPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                
                int cardIndex = Integer.parseInt(splittedRow[0]);
                String exampleSentence = splittedRow[1];
                
                dictionaryDataContainer.cardContainer.getCardByIndex(cardIndex).exampleSentences.add(exampleSentence);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find example_sentences file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }
    
    public void loadCardIndexesAndCategoryIndexes() {
        try {
            //TODO: make it more general
            if (languageFilesDataHandler.getStudiedLanguageIndex() == 1) {
                String filePath = "../data/german_data/dictionary_data/card_indexes_to_category_indexes.txt";
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    String[] splittedRow = strLine.split("\t");
                    int cardIndex = Integer.parseInt(splittedRow[0]);
                    
                    for (int i=1; i<splittedRow.length; i++) {
                        int categoryIndex = Integer.parseInt(splittedRow[i]);
                        dictionaryDataContainer.cardContainer.getCardByIndex(cardIndex).categories.add(categoryIndex);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find card categories file");
        } catch (IOException e) {
            System.err.println("exception in loadCardCategories function");
        }
    }
    
    public void loadCategoryIndexesAndCategoryNames() {
        try {
            //TODO: make it more general
            if (languageFilesDataHandler.getStudiedLanguageIndex() == 1) {
                String filePath = "../data/german_data/dictionary_data/card_categories.txt";
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    String[] splittedRow = strLine.split("\t");
                    int categoryIndex = Integer.parseInt(splittedRow[0]);
                    String categoryName = splittedRow[1];
                    
                    dictionaryDataContainer.categoryIndexesAndCategoryNames.put(categoryIndex, categoryName);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find file");
        } catch (IOException e) {
            System.err.println("exception in load file function");
        }
    }    
    
    public void loadAllData() {
        loadCardContainer();
        loadAnswerData();
        loadExampleSentences();
        loadCardIndexesAndCategoryIndexes();
        loadCategoryIndexesAndCategoryNames();
    }
}
