package language_studyer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DiscFilesMetaDataHandler {

    //TODO: make different classes for grammar book and dictionary
    
    public String settingsDataFilePath = "../data/settings_data/settings_data_file.txt";

    
    protected int studyedLanguageIndex = 1;
    
    protected ArrayList<LanguageFilesData> languageDatas = new ArrayList<>();
    
    
    public DiscFilesMetaDataHandler() {
        
        /////////////////// ENGLISH ///////////////////
        
        LanguageFilesData languageData1 = new LanguageFilesData();
        languageData1.languageIndex = 0;
        languageData1.languageName = "English";
        languageData1.dataFilesPath = "../data/english_data/";

        /////////////////// GERMAN ///////////////////
        
        LanguageFilesData languageData2 = new LanguageFilesData();
        languageData2.languageIndex = 1;
        languageData2.languageName = "German";
        languageData2.dataFilesPath = "../data/german_data/";

        languageDatas.add(languageData1);
        languageDatas.add(languageData2);
        
        loadSettingsData();
    }
    
    public String getStudiedLanguageName() {
        return languageDatas.get(studyedLanguageIndex).languageName;
    }

    public String getStudiedLanguageDataPath() {
        return languageDatas.get(studyedLanguageIndex).dataFilesPath;
    } 
    
   public String getStudiedLanguageCardDataPath() {
        return getStudiedLanguageDataPath() + "dictionary_data/words.txt";
    }
    
    public String getStudiedLanguageAnswerDataPath() {
        return getStudiedLanguageDataPath() + "dictionary_data/card_tester_data.txt";
    }

    public String getStudiedLanguageExampleSentencesDataPath() {
        return getStudiedLanguageDataPath() + "dictionary_data/example_sentences.txt";
    }
    
    public String getStudiedLanguageCardAndCategoryIndexesPath() {
        return getStudiedLanguageDataPath() + "dictionary_data/card_indexes_to_category_indexes.txt";
    }
    
    public String getStudiedLanguageDictionaryStudyStrategyPath() {
        return getStudiedLanguageDataPath() + "dictionary_data/study_strategy_data_file.txt";
    }     
    
    public String getStudiedLanguageCardCategoryIndexesAndCategoryNames() {
        return getStudiedLanguageDataPath() + "dictionary_data/card_categories.txt";
    }
    
    ///////////// grammar book /////////////
    
    public String getStudiedLanguageGrammarBookPath() {
        return getStudiedLanguageDataPath() 
                + "grammar_data/grammar_book.txt";
    }
    
    public String getStudiedLanguageGrammarAnswerDataPath() {
        return getStudiedLanguageDataPath() 
                + "grammar_data/grammar_answer_data.txt";
    }
   
    public String getStudiedLanguageGrammarItemAndCategoryIndexesPath() {
        return getStudiedLanguageDataPath() 
                + "grammar_data/grammar_item_indexes_to_category_indexes.txt";
    }
    
    public String getStudiedLanguageGrammarItemCategoryIndexesAndCategoryNames() {
        return getStudiedLanguageDataPath() 
                + "grammar_data/grammar_item_categories.txt";
    }
    
    public String getStudiedLanguageGrammarStudyStrategyPath() {
        return getStudiedLanguageDataPath() 
                + "grammar_data/study_strategy_data_file.txt";
    }  
            
    public int getStudiedLanguageIndex() {
        return studyedLanguageIndex;
    }
    
    public Set<Integer> getLanguageIndexes() {
        Set<Integer> languageIndexes = new HashSet<>();

        for (int i = 0; i < languageDatas.size(); i++) {
            languageIndexes.add(languageDatas.get(i).languageIndex);
        }

        return languageIndexes;
    }

    public void setStudyedLanguageIndex(int languageIndex) {
        studyedLanguageIndex = languageIndex;
        try {
            FileWriter fw = new FileWriter(settingsDataFilePath, false);	//true will append the new data
            fw.write(Integer.toString(languageIndex));
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    
    public void loadSettingsData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("../data/settings_data/settings_data_file.txt"));
            studyedLanguageIndex = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }
    }
}
