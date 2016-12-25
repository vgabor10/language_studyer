package grammar_book;

import disc_operation_handlers.LanguageFilesData;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DiscFilesMetaDataHandler {

    private int studyedLanguageIndex = 1;
    public String settingsDataFilePath = "../data/settings_data/settings_data_file.txt";

    private ArrayList<LanguageFilesData> languageDatas = new ArrayList<>();

    public DiscFilesMetaDataHandler() {
        
        /////////////////// ENGLISH ///////////////////
        
        LanguageFilesData languageData1 = new LanguageFilesData();
        languageData1.languageIndex = 0;
        languageData1.languageName = "English";
        languageData1.dataFilesPath = "../data/english_data/grammar_data/";

        /////////////////// GERMAN ///////////////////
        
        LanguageFilesData languageData2 = new LanguageFilesData();
        languageData2.languageIndex = 1;
        languageData2.languageName = "German";
        languageData2.dataFilesPath = "../data/german_data/grammar_data/";

        languageDatas.add(languageData1);
        languageDatas.add(languageData2);
    }

    public String getStudiedLanguageName() {
        return languageDatas.get(studyedLanguageIndex).languageName;
    }

    public String getStudiedLanguageDataPath() {
        return languageDatas.get(studyedLanguageIndex).dataFilesPath;
    }
    
    public String getStudiedLanguageGrammarBookPath() {
        return getStudiedLanguageDataPath() + "grammar_book.txt";
    }
    
    public String getStudiedLanguageGrammarAnswerDataPath() {
        return getStudiedLanguageDataPath() + "grammar_answer_data.txt";
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
            FileWriter fw = new FileWriter(settingsDataFilePath, false);	//the true will append the new data
            fw.write(Integer.toString(languageIndex));	//appends the string to the file
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
