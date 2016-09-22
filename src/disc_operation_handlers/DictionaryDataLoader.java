package disc_operation_handlers;

import dictionary.Card;
import dictionary.CardContainer;
import dictionary.DictionaryDataContainer;
import dictionary.ExampleSentence;
import dictionary.ExampleSentenceContainer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import study_item_objects.AnswerData;
import study_item_objects.AnswerDataContainer;

public class DictionaryDataLoader {
    
    public DictionaryDataContainer dictionaryDataContainer;
    
    private LanguageFilesDataHandler languageFilesDataHandler = new LanguageFilesDataHandler();
    
    public void loadCardContainer() {
        try {        
            CardContainer cardContainer = dictionaryDataContainer.cardContainer;
            
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
            ExampleSentenceContainer exampeSentenceContainer
                    = dictionaryDataContainer.exampleSentenceContainer;
            
            String filePath = "../data_to_integrate/example_sentences.txt";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                
                ExampleSentence exampleSentence = new ExampleSentence();
                exampleSentence.index = Integer.parseInt(splittedRow[0]);
                exampleSentence.sentence = splittedRow[1];
                
                exampeSentenceContainer.addStudyItem(exampleSentence);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find example_sentences file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }
    
    public void loadCardsToExampeSentencesAssignations() {
        try {        
            CardContainer cardContainer = dictionaryDataContainer.cardContainer;
            
            String filePath = "../data_to_integrate/exampe_sentences_and_cards_assignations.txt";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                
                int exmpleSentenceIndex = Integer.parseInt(splittedRow[0]);
                
                for (int i=1; i<splittedRow.length; i++) {
                    int cardIndex = Integer.parseInt(splittedRow[i]);

                    cardContainer.getCardByIndex(cardIndex).exampleSentenceIndexes.add(exmpleSentenceIndex);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find example_sentences file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }
    
    public void loadAllData() {
        loadCardContainer();
        loadAnswerData();
        loadExampleSentences();
        loadCardsToExampeSentencesAssignations();
    }
}
