package settings_handler;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class SettingsHandler {

	private int studyedLanguageIndex = 0;
	public String settingsDataFilePath = "../data/settings_data/settings_data_file.txt";

	private Vector<LanguageData> languageDatas = new Vector<LanguageData>();

	public SettingsHandler() {
		LanguageData languageData1 = new LanguageData();
		languageData1.languageIndex = 0;
		languageData1.languageName = "English";
		languageData1.answerDataPath = "../data/english_language_data/dictionary_data/english_answer_data_file.txt";
		languageData1.cardDataPath = "../data/english_language_data/dictionary_data/english_words.txt";
		languageData1.grammarDataPath = "../data/english_language_data/grammar_book_data/english_grammar_book.tex";
		languageData1.grammarAnswerDataPath = "../data/english_language_data/grammar_book_data/english_grammar_answer_data_file.txt";

		LanguageData languageData2 = new LanguageData();
		languageData2.languageIndex = 1;
		languageData2.languageName = "German";
		languageData2.answerDataPath = "../data/german_data/answer_data/german_card_tester_1_data.txt";
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

	public Set<Integer> getLanguageIndexes() {
		Set <Integer> languageIndexes = new HashSet<Integer>();

		for (int i=0; i<languageDatas.size(); i++) {
			languageIndexes.add(languageDatas.get(i).languageIndex);
		}

		return languageIndexes;
	}

	public void changeLanguageTostudy(int languageIndex) {
		studyedLanguageIndex = languageIndex;
		try {
			FileWriter fw = new FileWriter(settingsDataFilePath,false);	//the true will append the new data
			fw.write(Integer.toString(languageIndex));	//appends the string to the file
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void loadSettingsData() {
		BufferedReader br = null;
		try {
			br = new BufferedReader( new FileReader("../data/settings_data/settings_data_file.txt"));
			studyedLanguageIndex = Integer.parseInt(br.readLine());
		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}
}
