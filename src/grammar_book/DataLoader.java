package grammar_book;

import dictionary.CardCategory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {

    private DataContainer dataContainer;

    public DiscFilesMetaDataHandler discFilesMetaDataHandler;

    public void setDataContainer(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }
    
    public void loadLanguageDataWithIndex(int languageIndex) {  //TODO: is it used anywehere?
        discFilesMetaDataHandler.setStudyedLanguageIndex(languageIndex);
        this.loadAllData();
    }

    public void loadGrammarItems() {
        try {
            GrammarItemContainer grammarBook = dataContainer.grammarItemContainer;
            
            String filePath = discFilesMetaDataHandler.getStudiedLanguageGrammarBookPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine = br.readLine();
            
            int grammarItemOrderIndex = 0;
            
            GrammarItem grammarItem = new GrammarItem();
            
            while (strLine != null) {
                
                if (strLine.startsWith("\\title{")) {
                    if (grammarItemOrderIndex != 0) {
                        grammarBook.addGrammarItem(grammarItem);
                        grammarItem = new GrammarItem();
                    }
                    grammarItemOrderIndex++;
                    
                    String titleString = strLine.substring(7, strLine.length() - 1);
                    
                    grammarItem.title.setTitleFromString(titleString);
                }
                
                if (strLine.startsWith("GrammarItemIndex")) {
                    grammarItem.index = Integer.parseInt(strLine.substring(strLine.indexOf("=") + 2));
                }
                
                if (strLine.startsWith("Categories")) {
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

                        example.index = Integer.parseInt(strings[0]);
                        example.hun = strings[2];
                        example.foreign = strings[1];

                        grammarItem.addExample(example);

                        strLine = br.readLine();
                    }
                }

                strLine = br.readLine();
            }
            
            grammarBook.addGrammarItem(grammarItem);
            
        } catch (FileNotFoundException e) {
            System.err.println("unable to find grammar book file");
        } catch (IOException e) {
            System.err.println("exception at loadGrammarBookFromDisc function");
        }
    }

    public void loadAnswerData() {
        try {
            GrammarAnswerDataContainer grammarAnswerDataContainer 
                    = dataContainer.grammarAnswerDataContainer;
            
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

    public void loadStudyItemIndexesAndCategoryIndexes() {
        /*try {
            String filePath = discFilesMetaDataHandler.getStudiedLanguageStudyItemAndCategoryIndexesPath();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] splittedRow = strLine.split("\t");
                int cardIndex = Integer.parseInt(splittedRow[0]);

                for (int i=1; i<splittedRow.length; i++) {
                    int categoryIndex = Integer.parseInt(splittedRow[i]);
                    dataContainer.grammarItemContainer.getByIndex(cardIndex).categoryIndexes.add(categoryIndex);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find study item categories file");
        } catch (IOException e) {
            System.err.println("exception in loadStudyItemIndexesAndCategoryIndexes function");
        }*/
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

                CardCategory category = new CardCategory();
                category.index = categoryIndex;
                category.name = categoryName;

                dataContainer.categoryContainer.add(category);
            }
        } catch (FileNotFoundException e) {
            System.err.println("unable to find file");
        } catch (IOException e) {
            System.err.println("exception in load file function");
        }
    }   
    
    public void loadAllData() {
        loadGrammarItems();
        loadAnswerData();
        loadCategoryIndexesAndCategoryNames();
    }

}
