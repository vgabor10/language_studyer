package grammar_book;

import common.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import language_studyer.AnswerDataContainer;
import language_studyer.DataContainer;
import language_studyer.DataLoader;
import language_studyer.DiscFilesMetaDataHandler;

public class GrammarDataLoader extends DataLoader {
    
    private final Logger logger = new Logger();

    private DiscFilesMetaDataHandler discFilesMetaDataHandler;

    public void setGrammarDataContainer(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }
    
    public GrammarDataContainer getGrammarDataContainer() {
        return (GrammarDataContainer) this.dataContainer;
    }   
    
    public void setDiscFilesMetaDataHandler
        (DiscFilesMetaDataHandler discFilesMetaDataHandler) {
        this.discFilesMetaDataHandler = discFilesMetaDataHandler;
    }
    
    public void loadLanguageDataWithIndex(int languageIndex) {  //TODO: is it used anywehere?
        discFilesMetaDataHandler.setStudyedLanguageIndex(languageIndex);
        this.loadAllData();
    }

    public void loadGrammarItems() {
        try {
            logger.debug("start loading grammar items");
            
            GrammarItemContainer grammarItemContainer 
                    = getGrammarDataContainer().getGrammarItemContainer();
            
            String filePath = discFilesMetaDataHandler.getStudiedLanguageGrammarBookPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine = br.readLine();
            
            int grammarItemOrderIndex = 0;
            
            GrammarItem grammarItem = new GrammarItem();
            
            while (strLine != null) {
                
                if (strLine.startsWith("\\title{")) {
                    if (grammarItemOrderIndex != 0) {
                        grammarItemContainer.addGrammarItem(grammarItem);
                        
                        logger.debug("grammar item " + grammarItem.title.toString() + " loaded");
                        
                        grammarItem = new GrammarItem();
                    }
                    grammarItemOrderIndex++;
                    
                    String titleString = strLine.substring(7, strLine.length() - 1);
                    
                    grammarItem.title.setTitleFromString(titleString);
                }
                
                if (strLine.startsWith("GrammarItemIndex")) {
                    grammarItem.index = Integer.parseInt(strLine.substring(strLine.indexOf("=") + 2));
                }
                
                if (strLine.startsWith("Categories")) {     //TODO: should delete!!!!!!
                    grammarItem.categoryIndexes.add(Integer.parseInt(strLine.substring(strLine.indexOf("=") + 2)));
                }

                if (strLine.startsWith("%")) {
                    grammarItem.comments = strLine;
                }

                if (strLine.equals("\\" + "begin{desc}")) {
                    strLine = br.readLine();

                    while (!strLine.equals("\\" + "end{desc}")) {
                        grammarItem.description = grammarItem.description + strLine + "\n";
                        strLine = br.readLine();
                    }

                    if (grammarItem.description.endsWith("\n")) {
                        grammarItem.description
                                = grammarItem.description.substring(0, grammarItem.description.length() - 1);
                    }
                }

                if (strLine.equals("\\" + "begin{exmp}")) {
                    strLine = br.readLine();

                    while (!strLine.equals("\\" + "end{exmp}")) {
                        String[] strings = strLine.split(" \\| ");
                        Example example = new Example();

                        example.grammarItemIndex = grammarItem.index;
                        example.index = Integer.parseInt(strings[0]);
                        example.hun = strings[2];
                        example.foreign = strings[1];

                        grammarItem.addExample(example);

                        strLine = br.readLine();
                    }
                }

                strLine = br.readLine();
            }
            
            grammarItemContainer.addGrammarItem(grammarItem);
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find grammar book file");
        } catch (IOException e) {
            System.err.println("exception at loadGrammarBookFromDisc function");
        }
    }

    public void loadAnswerData() {
        try {
            AnswerDataContainer grammarAnswerDataContainer 
                    =  dataContainer.getAnswerDataContainer();
            
            String filePath = discFilesMetaDataHandler.getStudiedLanguageGrammarAnswerDataPath();
            String strLine;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((strLine = br.readLine()) != null) {
                GrammarAnswerData grammarAnswerData = new GrammarAnswerData();
                grammarAnswerData.setDataFromString(strLine);
                grammarAnswerDataContainer.addAnswerData(grammarAnswerData);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find grammar answer data file");
        } catch (IOException e) {
            System.err.println("exception at loadGrammarAnswerDataFromDisc function");
        }
    }

    public void loadGrammarItemIndexesAndCategoryIndexes() {
        String filePath = discFilesMetaDataHandler
                .getStudiedLanguageGrammarItemAndCategoryIndexesPath();
        
        loadStudyItemIndexesAndCategoryIndexes(filePath);
    }
    
    public void loadCategoryIndexesAndCategoryNames() {
        logger.debug("start to load grammar item categories");
        
        String filePath = discFilesMetaDataHandler
                .getStudiedLanguageGrammarItemCategoryIndexesAndCategoryNames();
        
        loadCategoryIndexesAndCategoryNames(filePath);
    }
    
    public void loadStudyStrategyDataFromDisc() {
        String filePath = discFilesMetaDataHandler
                .getStudiedLanguageGrammarStudyStrategyPath();
        
        loadStudyStrategyDataFromDisc(filePath);
    }
    
    public void loadAllData() {
        dataContainer.clear();
        
        loadGrammarItems();
        loadAnswerData();
        loadCategoryIndexesAndCategoryNames();
        loadStudyStrategyDataFromDisc();
    }

}
