package disc_operation_handlers;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LanguageFilesDataHandler {

    private int studyedLanguageIndex = 1;
    public String settingsDataFilePath = "../data/settings_data/settings_data_file.txt";

    private ArrayList<LanguageFilesData> languageDatas = new ArrayList<>();

    public LanguageFilesDataHandler() {
        LanguageFilesData languageData1 = new LanguageFilesData();
        languageData1.languageIndex = 0;
        languageData1.languageName = "English";
        languageData1.answerDataPath = "../data/english_data/answer_data/english_card_tester_data.txt";
        languageData1.cardDataPath = "../data/english_data/language_data/english_words.txt";
        languageData1.grammarDataPath = "../data/english_data/language_data/english_grammar_book.tex";
        languageData1.grammarAnswerDataPath = "../data/english_data/answer_data/english_grammar_answer_data.txt";

        LanguageFilesData languageData2 = new LanguageFilesData();
        languageData2.languageIndex = 1;
        languageData2.languageName = "German";
        languageData2.answerDataPath = "../data/german_data/answer_data/german_card_tester_data.txt";
        languageData2.cardDataPath = "../data/german_data/language_data/german_words.txt";
        languageData2.grammarDataPath = "../data/german_data/language_data/german_grammar_book.tex";
        languageData2.grammarAnswerDataPath = "../data/german_data/answer_data/german_grammar_answer_data.txt";

        languageDatas.add(languageData1);
        languageDatas.add(languageData2);

        loadSettingsData();
    }

    public String getStudiedLanguageName() {
        return languageDatas.get(studyedLanguageIndex).languageName;
    }

    public String getStudiedLanguageAnswerDataPath() {
        return languageDatas.get(studyedLanguageIndex).answerDataPath;
    }

    public String getStudiedLanguageCardDataPath() {
        return languageDatas.get(studyedLanguageIndex).cardDataPath;
    }

    public String getStudiedLanguageGrammarBookPath() {
        return languageDatas.get(studyedLanguageIndex).grammarDataPath;
    }

    public String getStudiedLanguageGrammarAnswerDataPath() {
        return languageDatas.get(studyedLanguageIndex).grammarAnswerDataPath;
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
