package grammar_book;

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
    
    public void loadLanguageDataWithIndex(int languageIndex) {
        discFilesMetaDataHandler.setStudyedLanguageIndex(languageIndex);
        this.loadAllData();
    }

    public void loadGrammarBook() {
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

    public void loadGrammarAnswerData() {
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

    public void loadAllData() {
        loadGrammarBook();
        loadGrammarAnswerData();
    }

}