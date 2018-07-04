package dictionary;

import language_studyer.StudyStrategy;
import language_studyer.Category;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import language_studyer.AnswerData;
import language_studyer.AnswerDataContainer;

public class DictionaryDataLoader {
    
    private DictionaryDataContainer dataContainer;
    
    private DiscFilesMetaDataHandler discFilesMetaDataHandler;
    
    public void setDataContainer(DictionaryDataContainer dc) {
        this.dataContainer = dc;
    }

    public void setLanguageFilesDataHandler(DiscFilesMetaDataHandler discFilesMetaDataHandler) {
        this.discFilesMetaDataHandler = discFilesMetaDataHandler;
    }
    
    public void loadCardContainer() {
        try {        
            CardContainer cardContainer = dataContainer.getCardContainer();
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
                
                dataContainer.getCardContainer().getCardByIndex(cardIndex).exampleSentences.add(exampleSentence);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find example_sentences file");
        } catch (IOException e) {
            System.err.println("exception in loadCardContainer function");
        }
    }
    
    public void loadCardIndexesAndCategoryIndexes() {
        try {
            String filePath = discFilesMetaDataHandler.getStudiedLanguageCardAndCategoryIndexesPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                int cardIndex = Integer.parseInt(splittedRow[0]);

                for (int i=1; i<splittedRow.length; i++) {
                    int categoryIndex = Integer.parseInt(splittedRow[i]);
                    dataContainer.getCardContainer()
                            .getCardByIndex(cardIndex).categoryIndexes.add(categoryIndex);
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
            String filePath = discFilesMetaDataHandler.getStudiedLanguageCategoryIndexesAndCategoryNames();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                int categoryIndex = Integer.parseInt(splittedRow[0]);
                String categoryName = splittedRow[1];

                Category category = new Category();
                category.index = categoryIndex;
                category.name = categoryName;

                dataContainer.getCategoryContainer().add(category);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find file");
        } catch (IOException e) {
            System.err.println("exception in load file function");
        }
    }    
    
    public void loadStudyStrategyDataFromDisc() {
        try {
            String filePath = discFilesMetaDataHandler.getStudiedLanguageDictionaryStudyStrategy();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            
            StudyStrategy studyStrategy = dataContainer.getStudyStrategy();
            
            String line;
            while ((line = br.readLine()) != null) {

                if (line.startsWith("numberOfRandomCards: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfRandomItems = Integer.parseInt(s);
                }
                
                if (line.startsWith("numberOfCardsFromTheLeastKnown20Percent: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfItemsFromTheLeastKnown20Percent = Integer.parseInt(s);
                }
                
                if (line.startsWith("numberOfCardsFromTheLeastKnown100: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfItemsFromTheLeastKnown100 = Integer.parseInt(s);
                }

                if (line.startsWith("numberOfCardsWithLeastSignificantAr: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfItemsAmongTheLeastSignificantAr = Integer.parseInt(s);
                }

                if (line.startsWith("numberOfLatestQuestionedCards: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    studyStrategy.numberOfLatestQuestionedItems = Integer.parseInt(s);
                }
                
                if (line.startsWith("studyingGradually: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);

                    if (s.equals("false")) {
                        studyStrategy.studyingGradually = false;
                    }
                    
                    if (s.equals("true")) {
                        studyStrategy.studyingGradually = true;
                    }
                }
                
                 if (line.startsWith("cardCategoryRestrictions: ")) {
                     String s = line.substring(line.lastIndexOf(":") + 2);
                     String[] stringCategoryIndexes = s.split(" ");
                     
                     for (String stringCategoryIndes : stringCategoryIndexes) {
                         int categoryIndex = Integer.parseInt(stringCategoryIndes);
                         studyStrategy.cardCategoryRestrictions.add(categoryIndex);
                     }
                }

            }
            
            //studyStrategyIndex = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }   
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
