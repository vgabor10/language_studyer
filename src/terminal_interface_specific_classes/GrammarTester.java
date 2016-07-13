package terminal_interface_specific_classes;

import grammar_book.Example;
import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;
import grammar_book.GrammarItem;
import study_item_objects.AnswerDataByStudyItem;
import settings_handler.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GrammarTester {

	private GrammarAnswerDataContainer grammarAnswerDataContainer;
	private final Random randomGenerator = new Random();
	private GrammarBook grammarBook;

	public void setGrammarBook(GrammarBook gb) {
		grammarBook = gb;
	}

	public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer a) {
		grammarAnswerDataContainer = a;
	}

	private Vector<Integer> getRandomExampleIndexes(int grammarItemIndex, int numberOfExamples) {

		Vector<Integer> idexesToAdd = new Vector<>(grammarBook.getGrammarItemByIndex(grammarItemIndex).getExampleIndexes());
		Vector<Integer> outVector = new Vector<>();

		while (outVector.size()<numberOfExamples) {
			java.util.Collections.shuffle(idexesToAdd);
			outVector.addAll(idexesToAdd);
		}

		while (!(outVector.size() == numberOfExamples)) {
			outVector.removeElementAt(outVector.size()-1);
		}

		return outVector;
	}

	public void performTestByOrderIndex(int orderIndex, int numberOfExamples) {
		int grammarItemIndex = grammarBook.getGrammarItemByOrder(orderIndex).index;
		performTestByGrammarItemIndex(grammarItemIndex, numberOfExamples);
	}

	public void performTestByGrammarItemIndex(int grammarItemIndex, int numberOfExamples) {

		GrammarItem grammarItem = grammarBook.getGrammarItemByIndex(grammarItemIndex);

		System.out.println("log: " + grammarItem.index);	//log

		Console console = System.console();
		GrammarAnswerDataContainer testAnswers = new GrammarAnswerDataContainer();

		Vector<Integer> exampleIndexes = getRandomExampleIndexes(grammarItem.index, numberOfExamples);

		long startTime = System.currentTimeMillis();

		int counter = 0;
		for (int exampleIndex : exampleIndexes) {
			System.out.print("\033[H\033[2J");

			counter++;
			System.out.println(counter + "/" + numberOfExamples);
			System.out.println("-------------------------------------------------");

			Example example = grammarItem.getExampleByIndex(exampleIndex);

			System.out.println(grammarItem.title);
			System.out.println(example.hun);
			String answer = console.readLine();

			Date date = new Date();
			if (answer.equals(example.foreign)) {
				System.out.print("RIGHT");
				testAnswers.addElement(grammarItem.index, exampleIndex, true, date.getTime());
				console.readLine();
			}
			else {
				System.out.println(example.foreign);

				System.out.println();
				System.out.println("DESCRIPTION:");
				System.out.println(grammarItem.description);
				System.out.println();

				System.out.println("seems to be wrong, what is your opinion?");
				System.out.println("0 or enter - wrong answer");
				System.out.println("1 - accept answer");
				System.out.println("2 - do not register the answer");

				String choice = console.readLine();

				if (choice.equals("0") || choice.equals("")) {
					testAnswers.addElement(grammarItem.index, exampleIndex, false, date.getTime());
				}

				if (choice.equals("1")) {
					testAnswers.addElement(grammarItem.index, exampleIndex, true, date.getTime());
				}
			}
		}

		long endTime = System.currentTimeMillis();

		AnswerDataByStudyItem answerDataByStudyItem = new AnswerDataByStudyItem();
		answerDataByStudyItem.loadDataFromAnswerDataContainer(grammarItemIndex, grammarAnswerDataContainer);
		double rightAnswerRateBeforeTest = answerDataByStudyItem.countRightAnswerRate();

		SettingsHandler settingsHandler = new SettingsHandler();
		testAnswers.appendToAnswerDataFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());
		grammarAnswerDataContainer.appendAnswerDataContainer(testAnswers);

		answerDataByStudyItem.clear();
		answerDataByStudyItem.loadDataFromAnswerDataContainer(grammarItemIndex, grammarAnswerDataContainer);
		double rightAnswerRateAfterTest = answerDataByStudyItem.countRightAnswerRate();

		System.out.print("\033[H\033[2J");
		System.out.println("number of registered answers: " + testAnswers.numberOfAnswers());
		System.out.println("number of answers of grammar item after test: " + answerDataByStudyItem.numberOfAnswers());

		int numberOfRightAnswers = 0;
		for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
			if (testAnswers.getAnswerData(i).isRight) numberOfRightAnswers++;
		}
		double percentage = (double)numberOfRightAnswers * 100.0 / (double)testAnswers.numberOfAnswers();
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("percentage of right answers: " + df.format(percentage) + "%");

		if (rightAnswerRateBeforeTest != -1) {
			System.out.println("grammar item right answer rate before test: " +  df.format(rightAnswerRateBeforeTest * 100) + "%");
		}
		else {
			System.out.println("grammar item right answer rate before test: -");
		}
		System.out.println("grammar item right answer rate after test: " +  df.format(rightAnswerRateAfterTest * 100) + "%");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		System.out.println("used time: " + dateFormatted);

		console.readLine();
        }
}
