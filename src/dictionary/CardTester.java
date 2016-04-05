package dictionary;

import common.*;
import settings_handler.*;

import java.util.*;
import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CardTester {

	private Random randomGenerator = new Random();
	private CardContainer cardsToTest;
	private AnswerDataContainer answerDataContainer;
	private	Set<Integer> cardsToTestIndexes = new HashSet<Integer>();
	private Map<Integer,Integer> testAdvance = new HashMap<Integer,Integer>();
	private CardChooser cardChooser = new CardChooser();

	public void setCardsToTest(CardContainer ctt) {
		cardsToTest = ctt;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	//WARNING: use only after setAnswerDataContainer and setCardContainer functions
	public void setCardChooser() {
		cardChooser.setCardContainer(cardsToTest);
		cardChooser.setAnswerDataContainer(answerDataContainer);
	}

	public double countProgress(int numberOfCards) {
		int sum = 0;
		int num = 0;
		for (int key : testAdvance.keySet()) {
			sum = sum + testAdvance.get(key);
			num++;
		}
		return (sum + (numberOfCards - num)*2 )/(double)(2*numberOfCards) * 100.0;
	}

	public void initialiseTestAnvanceMap() {
		for (int index : cardsToTestIndexes) {
			testAdvance.put(index,0);
		}
	}

	//20 random cards
	public void performTest1() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	//6 latest studyed cards, 6 among hardest cards, 8 random cards
	public void performTest2() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	//4 latest studyed cards, 8 among hardest cards, 8 random cards
	public void performTest3() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	//10 among the hardest 100, 4 latest studied, 6 random cards
	public void performTest4() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	//4 latest studyed cards, 8 among hardest cards, 4 cards with least significant answer rate, 4 random cards
	public void performTest5() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	//4 latest studyed cards, 8 among hardest cards, 2 cards among cards with the 100 lest significant answer rate, 6 random cards
	public void performTest6() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	//4 latest studyed cards, 4 among hardest 20%, 4 from the hardes 100, 2 among cards with the 100 lest significant answer rate, 6 random cards
	public void performTest7() {
		cardsToTestIndexes = cardChooser.chooseCardsToTestIndexesForTest1();
		initialiseTestAnvanceMap();
		performTest();
	}

	private void performTest() { 

		int numberOfCards = cardsToTestIndexes.size();

		Console console = System.console();
		DecimalFormat df = new DecimalFormat("#.00");

		long startTime = System.currentTimeMillis();

		AnswerDataContainer testAnswers = new AnswerDataContainer();

		System.out.print("\033[H\033[2J");
		for (int a : cardsToTestIndexes) {
			System.out.println(cardsToTest.getCard(a).toStringReverse());
		}

		console.readLine();

		int numberOfCardsLearned = 0;
		while (cardsToTestIndexes.size() != 0) {

			System.out.print("\033[H\033[2J");	//clear terminal

			int r = randomGenerator.nextInt(cardsToTestIndexes.size());
			int i = 0;
			int index = 0;
			for (int a : cardsToTestIndexes) {
				if (i == r) {
					index = a;
				}
				i++;
			}

			Card card = cardsToTest.getCard(index);
			System.out.println("progress: " + df.format(countProgress(numberOfCards)) + "%");
			System.out.println("number of cards learned: " + Integer.toString(numberOfCardsLearned));
			System.out.println("----------------------------------------------");
			System.out.println(card.definition);
			String answer = console.readLine();
			if (answer.equals(card.term)) {	//right answer
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
				testAnswers.addElement(index, true, date.getTime());
			}
			else {				//wrong answer
				if (testAdvance.get(index) == 1) {
					testAdvance.remove(index);
					testAdvance.put(index,0);
				}

				Date date = new Date();
				testAnswers.addElement(index, false, date.getTime());

				do {
					System.out.print("\033[H\033[2J");
					System.out.println("progress: " + df.format(countProgress(numberOfCards)) + "%");
					System.out.println("number of cards learned: " + Integer.toString(numberOfCardsLearned));
					System.out.println("----------------------------------------------");
					System.out.println(card.definition);
					System.out.println(card.term);
					answer = console.readLine();
				} while (!answer.equals(card.term));
			}

		}

		long endTime = System.currentTimeMillis();

		System.out.print("\033[H\033[2J");

		AnswerDataByStudyItemsContainer answerDatasByStudyItemsBeforeTest = new AnswerDataByStudyItemsContainer();
		answerDatasByStudyItemsBeforeTest.loadDataFromAnswerDataContainer(answerDataContainer);

		SettingsHandler settingsHandler = new SettingsHandler();
		testAnswers.appendToAnswerDataFile(settingsHandler.getStudiedLanguageAnswerDataPath());
		answerDataContainer.appendAnswerDataContainer(testAnswers);

		AnswerDataByStudyItemsContainer answerDatasByStudyItemsAfterTest = new AnswerDataByStudyItemsContainer();
		answerDatasByStudyItemsAfterTest.loadDataFromAnswerDataContainer(answerDataContainer);

		CardTestStatisticsMaker cardTestStatisticsMaker = new CardTestStatisticsMaker();
		cardTestStatisticsMaker.setCardContainer(cardsToTest);
		cardTestStatisticsMaker.setTestAnswers(testAnswers);
		cardTestStatisticsMaker.setAnswerDatasByStudyItemsBeforeTest(answerDatasByStudyItemsBeforeTest);
		cardTestStatisticsMaker.setAnswerDatasByStudyItemsAfterTest(answerDatasByStudyItemsAfterTest);
		cardTestStatisticsMaker.setStartAndEndTime(startTime, endTime);
		cardTestStatisticsMaker.toScreenStatistics();

		console.readLine();
	}

}
