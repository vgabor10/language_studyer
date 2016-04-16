package dictionary;

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
	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;
	private	Set<Integer> cardsToTestIndexes = new HashSet<Integer>();
	private Map<Integer,Integer> testAdvance = new HashMap<Integer,Integer>();

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
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

	public Set<Integer> getLatestQuestionedCardIndexes(int numberOfCards) {

		Map<Integer,Long> cardIndexesLastQuestionDate = new HashMap<Integer,Long>();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			AnswerData answerData = answerDataContainer.getAnswerData(i);
			if (cardIndexesLastQuestionDate.containsKey(answerData.index)) {
				if (cardIndexesLastQuestionDate.get(answerData.index) < answerData.date) {
					cardIndexesLastQuestionDate.remove(answerData.index);
					cardIndexesLastQuestionDate.put(answerData.index, answerData.date);
				}
			}
			else {
				cardIndexesLastQuestionDate.put(answerData.index, answerData.date);
			}
		}

		for (int i=0; i < cardContainer.numberOfCards(); i++) {
			if (!cardIndexesLastQuestionDate.containsKey(cardContainer.getCard(i).index)) {
				cardIndexesLastQuestionDate.put(cardContainer.getCard(i).index, Long.MIN_VALUE);
			}
		}

		Set<Integer> out = new HashSet<Integer>();

		for (int j=0; j<numberOfCards; j++) {
			long minDate = Long.MAX_VALUE;
			int minIndex = -1;
			for (int index : cardIndexesLastQuestionDate.keySet()) {
				if (cardIndexesLastQuestionDate.get(index) < minDate) {
					minDate = cardIndexesLastQuestionDate.get(index);
					minIndex = index;
				}
			}
			cardIndexesLastQuestionDate.remove(minIndex);
			out.add(minIndex);
		}

		return out;
	}

	public int getRandomHardestCardIndex(double hardestWordRate) {		//TODO: implement it more efficiently, too slow
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByCardComparatorByRateOfRightAnswers());
		int r = randomGenerator.nextInt((int)Math.floor((double)answerDataByCardsContainer.numberOfCards() * hardestWordRate));
		return datasToSort[datasToSort.length - 1 - r].getAnswer(0).index;
	}

	public int getRandomCardIndex() {
		return randomGenerator.nextInt(cardContainer.numberOfCards());
	}

	public void chooseCardsToTestIndexesForTest1() {

		cardsToTestIndexes.clear();

		int index;
		for (int i=0; i<20; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}
	}

	public void chooseCardsToTestIndexesForTest2() {

		cardsToTestIndexes.clear();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(6);

 		for (int index : cardsToTestIndexes) {
			testAdvance.put(index,0);
		}

		int index;
		for (int i=0; i<6; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}

		for (int i=0; i<8; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}
	}

	public void performTest1() {	//20 random cards
		chooseCardsToTestIndexesForTest1();
		performTest();
	}


	public void performTest2() {	//6 latest studyed cards, 6 among hardest cards, 8 random cards
		chooseCardsToTestIndexesForTest2();
		performTest();
	}

	public void performTest() {	//first cards need to choose

		int numberOfCards = cardsToTestIndexes.size();

		Console console = System.console();
		DecimalFormat df = new DecimalFormat("#.00");

		long startTime = System.currentTimeMillis();

		AnswerDataContainer answerDataContainer = new AnswerDataContainer();

		System.out.print("\033[H\033[2J");
		for (int a : cardsToTestIndexes) {
			System.out.println(cardContainer.data.elementAt(a).toStringReverse());
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

			Card card = cardContainer.data.elementAt(index);
			System.out.println("progress: " + df.format(countProgress(numberOfCards)) + "%");
			System.out.println("number of cards learned: " + Integer.toString(numberOfCardsLearned));
			System.out.println("----------------------------------------------");
			System.out.println(card.s2);
			String answer = console.readLine();
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
					System.out.println("progress: " + df.format(countProgress(numberOfCards)) + "%");
					System.out.println("number of cards learned: " + Integer.toString(numberOfCardsLearned));
					System.out.println("----------------------------------------------");
					System.out.println(card.s2);
					System.out.println(card.s1);
					answer = console.readLine();
				} while (!answer.equals(card.s1));
			}

		}

		long endTime = System.currentTimeMillis();

		System.out.print("\033[H\033[2J");

		SettingsHandler settingsHandler = new SettingsHandler();
		AnswerDataContainer storedAnswerDatas = new AnswerDataContainer();
		storedAnswerDatas.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		Set<Integer> cardIndexes = new HashSet<Integer>();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			cardIndexes.add(answerDataContainer.getAnswerData(i).index);
		}

		int numberOfCardsWithImprovement = 0;
		int numberOfCardsWithNoChange = 0;
		int numberOfCardsWithReducement = 0;
		int numberOfNewCardsTested = 0;

		System.out.println("CARD | % OF RIGHT ANSWERS IN TEST | % OF RIGHT ANSWERS BEFORE TEST");
		System.out.println("------------------------------------------------------------------");
		for (int cardIndex : cardIndexes) {
			double percentageOfRightAnswersInTest = answerDataContainer.percentageOfRightAnswers(cardIndex);
			double percentageOfRightAnswersBeforeTest = storedAnswerDatas.percentageOfRightAnswers(cardIndex);
			System.out.print(cardContainer.getCard(cardIndex).toString() + " | " 
				+ df.format(percentageOfRightAnswersInTest) + "% | ");
			if (percentageOfRightAnswersBeforeTest != -1) {
				System.out.println(df.format(percentageOfRightAnswersBeforeTest) + "%");

				if (percentageOfRightAnswersBeforeTest < percentageOfRightAnswersInTest) {
					numberOfCardsWithImprovement++;
				}
				else 
				if (percentageOfRightAnswersBeforeTest > percentageOfRightAnswersInTest) {
					numberOfCardsWithReducement++;
				}
				else {
					numberOfCardsWithNoChange++;
				}
			}
			else {
				System.out.println("- %");
				numberOfNewCardsTested++;
			}
		}

		System.out.println("------------------------------------------------------------------");;
		System.out.println("number of cards whose answer rate improves: " + numberOfCardsWithImprovement);
		System.out.println("number of cards whose answer rate does not change: " + numberOfCardsWithNoChange);
		System.out.println("number of cards whose answer rate reduces: " + numberOfCardsWithReducement);
		System.out.println("number of new cards tested: " + numberOfNewCardsTested);
		System.out.println("number of answers: " + answerDataContainer.numberOfAnswers());
		System.out.println("percentage of right answers: " + df.format(answerDataContainer.percentageOfRightAnswers()) + "%");
		System.out.println("average answer rate of cards before test :"
			+ df.format(answerDataContainer.getAverageAnswerRateOfCards() * 100.0) + "%");
		System.out.println("average answer rate of cards after test :"
			+ df.format(answerDataContainer.getAverageAnswerRateOfCards() * 100.0) + "%");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		System.out.println("used time: " + dateFormatted);

		answerDataContainer.appendToAnswerDataFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		console.readLine();
		System.out.print("\033[H\033[2J");
	}

}
