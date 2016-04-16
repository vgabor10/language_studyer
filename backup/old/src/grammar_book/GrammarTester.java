package grammar_book;

import settings_handler.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GrammarTester {

	private Random randomGenerator = new Random();
	private GrammarBook grammarBook;

	public void setGrammarBook(GrammarBook gb) {
		grammarBook = gb;
	}

	public Vector<int[]> getRandomExampleIndexes(int numberOfExamples) {	// (grammarItemIndex,exampleIndex)

		Vector<int[]> exampleIndexes = new Vector<int[]>();

		for(int i=0; i < numberOfExamples; i++) {
			int[] pair = new int[2];
			boolean again;

			do {
				pair[0] = randomGenerator.nextInt(grammarBook.numberOfGrammarItems());
				pair[1] = randomGenerator.nextInt(grammarBook.grammarItems.get(pair[0]).numberOfExamples());

				int j=0;
				while (j<exampleIndexes.size() &&
					!(exampleIndexes.get(j)[0] == pair[0] && exampleIndexes.get(j)[1] == pair[1])) j++;

				if (j == exampleIndexes.size()) {
					again = false;
				}
				else {
					again = true;
				}

			} while (again);

			exampleIndexes.add(pair);
			//System.out.println(Arrays.toString(pair));	//debug
		}

		return exampleIndexes;
	}

	/*public double countProgress(int numberOfCards) {
		int sum = 0;
		int num = 0;
		for (int key : testAdvance.keySet()) {
			sum = sum + testAdvance.get(key);
			num++;
		}
		return (sum + (numberOfCards - num)*2 )/(double)(2*numberOfCards) * 100.0;
	}*/

	public void performTest(int numberOfExamples) {

		Console console = System.console();
		GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();

		Vector<int[]> exampleIndexes = getRandomExampleIndexes(numberOfExamples);

		long startTime = System.currentTimeMillis();

		for (int i=0; i<exampleIndexes.size(); i++) {
			System.out.print("\033[H\033[2J");

			//System.out.println(Arrays.toString(exampleIndexes.get(i)));	//debug

			Example example = grammarBook.getExample(exampleIndexes.get(i)[0], exampleIndexes.get(i)[1]);

			System.out.println(grammarBook.grammarItems.get(exampleIndexes.get(i)[0]).title);
			System.out.println(example.hun);
			String answer = console.readLine();

			Date date = new Date();
			if (answer.equals(example.foreign)) {
				System.out.print("RIGHT");
				grammarAnswerDataContainer.addElement(exampleIndexes.get(i)[0], exampleIndexes.get(i)[1], true, date.getTime());
				console.readLine();
			}
			else {
				System.out.println(example.foreign);

				System.out.println();
				System.out.println("Grammar item description:");
				System.out.println(grammarBook.grammarItems.get(exampleIndexes.get(i)[0]).description);
				System.out.println();

				System.out.println("seems to be wrong, what is your opinion?");
				System.out.println("0 or enter - wrong answer");
				System.out.println("1 - accept answer");
				System.out.println("2 - do not register the answer");

				String choice = console.readLine();

				if (choice.equals("0") || choice.equals("")) {
					grammarAnswerDataContainer.addElement(exampleIndexes.get(i)[0], exampleIndexes.get(i)[1], false, date.getTime());
				}

				if (choice.equals("1")) {
					grammarAnswerDataContainer.addElement(exampleIndexes.get(i)[0], exampleIndexes.get(i)[1], true, date.getTime());
				}
			}
		}

		long endTime = System.currentTimeMillis();

		SettingsHandler settingsHandler = new SettingsHandler();
		grammarAnswerDataContainer.appendToAnswerDataFile(settingsHandler.getStudiedLanguageGrammarAnswerDataPath());

		System.out.print("\033[H\033[2J");
		System.out.println("number of registeread answers: " + grammarAnswerDataContainer.numberOfAnswers());

		int numberOfRightAnswers = 0;
		for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
			if (grammarAnswerDataContainer.getAnswerData(i).isRight) numberOfRightAnswers++;
		}
		double percentage = (double)numberOfRightAnswers * 100.0 / (double)grammarAnswerDataContainer.numberOfAnswers();
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("percentage of right answers: " + df.format(percentage) + "%");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		System.out.println("used time: " + dateFormatted);

		console.readLine();

		/*int numberOfCardsLearned = 0;
		while (cardsToTestIndexes.size() != 0) {

			int r = randomGenerator.nextInt(cardsToTestIndexes.size());
			int i = 0;
			int index = 0;
			for (int a : cardsToTestIndexes) {
				if (i == r) {
					index = a;
				}
				i++;
			}

			if (answer.equals(card.s1)) {	//right answer
				//System.out.println("RIGHT");

				if (testAdvance.get(index) == 0) {
					testAdvance.remove(index);
					testAdvance.put(index,1);
				} else
				if (testAdvance.get(index) == 1) {
					testAdvance.remove(index);
					cardsToTestIndexes.remove(index);
					numberOfCardsLearned++;
				}

				Date date = new Date();
				answerDataContainer.addElement(index, true, date.getTime());
			}
			else {				//wrong answer
				if (testAdvance.get(index) == 1) {
					testAdvance.remove(index);
					testAdvance.put(index,0);
				}

				Date date = new Date();
				answerDataContainer.addElement(index, false, date.getTime());

				do {
					System.out.print("\033[H\033[2J");
					System.out.println("progress: " + Double.toString(countProgress(numberOfCards)) + "%");
					System.out.println("number of cards learned: " + Integer.toString(numberOfCardsLearned));
					System.out.println("----------------------------------------------");
					System.out.println(card.s2);
					System.out.println(card.s1);
					answer = console.readLine();
				} while (!answer.equals(card.s1));
			}

		}

		long endTime = System.currentTimeMillis();

		answerDataContainer.appendToAnswerDataFile("../data/answer_data_file.txt");
		System.out.print("\033[H\033[2J");
		answerDataContainer.toScreenStatistics(cardContainer);
		System.out.println("---------------------------------------------");
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("percentage of right answers: " + df.format(answerDataContainer.percentageOfRightAnswers()) + "%");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		System.out.println("used time: " + dateFormatted);

		console.readLine();
		System.out.print("\033[H\033[2J");*/
	}

}
