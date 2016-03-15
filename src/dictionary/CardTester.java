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

	public int getRandomHardestCardIndex2(int numberOfHardestCards) {		//TODO: implement it more efficiently, too slow
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByCardComparatorByRateOfRightAnswers());
		int r = randomGenerator.nextInt(numberOfHardestCards);
		return datasToSort[datasToSort.length - 1 - r].getAnswer(0).index;
	}

	public Set<Integer> getCardIndexesWithLestSignificantAnswerRate(int numberOfCards, Set<Integer> omitedCardIndexes) {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByCardComparatorByNumberOfAnswers());

		Set<Integer> outCardIndexes = new HashSet<Integer>(); 

		int i=0;
		while (outCardIndexes.size() != numberOfCards && i<datasToSort.length) {
			if (!omitedCardIndexes.contains(datasToSort[i].getCardIndex())) {
				outCardIndexes.add(datasToSort[i].getCardIndex());
			}
			i++;
		}

		return outCardIndexes;
	}

	public Set<Integer> getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(int numberOfCards, Set<Integer> omitedCardIndexes) {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		if (answerDataByCardsContainer.numberOfCards() < 100) {
			return null;
		}
		else {
			AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
			Arrays.sort(datasToSort, new AnswerDataByCardComparatorByNumberOfAnswers());

			Set<Integer> outCardIndexes = new HashSet<Integer>(); 

			while (outCardIndexes.size() != numberOfCards) {
				int cardIndex = datasToSort[randomGenerator.nextInt(100)].getCardIndex();
				if (!omitedCardIndexes.contains(cardIndex) && !outCardIndexes.contains(cardIndex)) {
					outCardIndexes.add(cardIndex);
				}
			}

			//System.out.println("outCardIndexes: " + outCardIndexes);	//log

			return outCardIndexes;
		}
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

	public void chooseCardsToTestIndexesForTest3() {

		cardsToTestIndexes.clear();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

 		for (int index : cardsToTestIndexes) {
			testAdvance.put(index,0);
		}

		int index;
		for (int i=0; i<8; i++) {
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

	public void chooseCardsToTestIndexesForTest4() {

		cardsToTestIndexes.clear();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

 		for (int index : cardsToTestIndexes) {
			testAdvance.put(index,0);
		}

		int index;
		for (int i=0; i<10; i++) {
			do {
				index = getRandomHardestCardIndex2(100);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}

		for (int i=0; i<6; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}
	}

	public void chooseCardsToTestIndexesForTest5() {

		cardsToTestIndexes.clear();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

 		for (int index : cardsToTestIndexes) {
			testAdvance.put(index,0);
		}

		int index;
		for (int i=0; i<8; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}

		Set<Integer> cardsToAdd = getCardIndexesWithLestSignificantAnswerRate(4, cardsToTestIndexes);
		for (int i : cardsToAdd) {
			testAdvance.put(i,0);
			cardsToTestIndexes.add(i);
		}

		for (int i=0; i<4; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}
	}

	public void chooseCardsToTestIndexesForTest6() {

		cardsToTestIndexes.clear();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

 		for (int index : cardsToTestIndexes) {
			testAdvance.put(index,0);
		}

		int index;
		for (int i=0; i<8; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
			testAdvance.put(index,0);
		}

		Set<Integer> cardsToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(2, cardsToTestIndexes);
		for (int i : cardsToAdd) {
			testAdvance.put(i,0);
			cardsToTestIndexes.add(i);
		}

		for (int i=0; i<6; i++) {
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

	public void performTest3() {	//4 latest studyed cards, 8 among hardest cards, 8 random cards
		chooseCardsToTestIndexesForTest3();
		performTest();
	}

	public void performTest4() {	//10 among the hardest 100, 4 latest studied, 6 random cards
		chooseCardsToTestIndexesForTest4();
		performTest();
	}

	public void performTest5() {	//4 latest studyed cards, 8 among hardest cards, 4 cards with least significant answer rate, 4 random cards
		chooseCardsToTestIndexesForTest5();
		performTest();
	}

	public void performTest6() {	//4 latest studyed cards, 8 among hardest cards, 2 cards among cards with the 100 lest significant answer rate, 4 random cards
		chooseCardsToTestIndexesForTest6();
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

		df = new DecimalFormat("#.000");

		SettingsHandler settingsHandler = new SettingsHandler();
		AnswerDataByCardsContainer answerDatasByCardsBeforeTest = new AnswerDataByCardsContainer();
		answerDatasByCardsBeforeTest.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		answerDataContainer.appendToAnswerDataFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		AnswerDataByCardsContainer answerDatasByCardsAfterTest = new AnswerDataByCardsContainer();
		answerDatasByCardsAfterTest.loadDataFromFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		Set<Integer> cardIndexes = new HashSet<Integer>();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			cardIndexes.add(answerDataContainer.getAnswerData(i).index);	//need to give again tested card indexes
		}

		int numberOfCardsWithImprovement = 0;
		int numberOfCardsWithNoChange = 0;
		int numberOfCardsWithReducement = 0;
		int numberOfNewCardsTested = 0;
		int numberOfCategoryImprovements = 0;
		int numberOfCategoryReducements = 0;
		int[] categorySizeChanges = new int[10];

		System.out.println("CARD | % OF RIGHT ANSWERS AFTER TEST | % OF RIGHT ANSWERS BEFORE TEST | # OF ANSWERS AFTER TEST");
		System.out.println("------------------------------------------------------------------");
		for (int cardIndex : cardIndexes) {

			double percentageOfRightAnswersBeforeTest = -1;
			if (answerDatasByCardsBeforeTest.getTestedCardIndexes().contains(cardIndex)) {
				percentageOfRightAnswersBeforeTest 
					= answerDatasByCardsBeforeTest.getAnswerDataByCardByIndex(cardIndex).countRightAnswerRate() * 100.0;
			}

			double percentageOfRightAnswersAfterTest
				= answerDatasByCardsAfterTest.getAnswerDataByCardByIndex(cardIndex).countRightAnswerRate() * 100.0;
			
			System.out.print(cardContainer.getCard(cardIndex).toString() + " | " 
				+ df.format(percentageOfRightAnswersAfterTest) + "% | ");
			if (percentageOfRightAnswersBeforeTest != -1) {
				System.out.print(df.format(percentageOfRightAnswersBeforeTest) + "% | ");

				if (percentageOfRightAnswersBeforeTest < percentageOfRightAnswersAfterTest) {
					numberOfCardsWithImprovement++;
				}
				else 
				if (percentageOfRightAnswersBeforeTest > percentageOfRightAnswersAfterTest) {
					numberOfCardsWithReducement++;
				}
				else {
					numberOfCardsWithNoChange++;
				}


				///////////////// category statistics ////////////////
				double v1, v2;
				if (percentageOfRightAnswersAfterTest == 100.0) {
					v1 = percentageOfRightAnswersAfterTest-0.001;
				}
				else {
					v1 = percentageOfRightAnswersAfterTest;
				}

				if (percentageOfRightAnswersBeforeTest == 100.0) {
					v2 = percentageOfRightAnswersBeforeTest-0.001;
				}
				else {
					v2 = percentageOfRightAnswersBeforeTest;
				}

				int a = (int)Math.floor(v1/10.0) - (int)Math.floor(v2/10.0);
				if (a<0) {
					numberOfCategoryReducements = numberOfCategoryReducements - a;
				}
				if (a>0) {
					numberOfCategoryImprovements = numberOfCategoryImprovements + a;
				}

				int categoryBefore = (int)Math.floor(v2/10.0);
				int categoryAfter = (int)Math.floor(v1/10.0);
				categorySizeChanges[categoryBefore]--;
				categorySizeChanges[categoryAfter]++;
				///////////////// category statistics ////////////////
			}
			else {
				System.out.print("- % | ");
				numberOfNewCardsTested++;

				int category;
				if (percentageOfRightAnswersAfterTest == 100.0) {
					category = 9;
				}
				else {
					category = (int)Math.floor(percentageOfRightAnswersAfterTest/10.0);
				}
				categorySizeChanges[category]++;
			}

			System.out.println(answerDatasByCardsAfterTest.getAnswerDataByCardByIndex(cardIndex).numberOfAnswers());
		}

		System.out.println("------------------------------------------------------------------");;
		System.out.println("number of cards whose answer rate improves: " + numberOfCardsWithImprovement);
		System.out.println("number of cards whose answer rate does not change: " + numberOfCardsWithNoChange);
		System.out.println("number of cards whose answer rate reduces: " + numberOfCardsWithReducement);
		System.out.println("number of new cards tested: " + numberOfNewCardsTested);
		System.out.println("number of category improvements: " + numberOfCategoryImprovements);
		System.out.println("number of category reducements: " + numberOfCategoryReducements);
		System.out.print("category size changes: ");
		for (int i=0; i<9; i++) {
			System.out.print((i) + ":" + categorySizeChanges[i] + ", ");
		}
		System.out.println("9:" + categorySizeChanges[9]);
		System.out.println("number of answers: " + answerDataContainer.numberOfAnswers());
		System.out.println("percentage of right answers: " + df.format(answerDataContainer.percentageOfRightAnswers()) + "%");
		System.out.println("average answer rate of cards before test: "
			+ df.format(answerDatasByCardsBeforeTest.getAverageAnswerRateOfCards() * 100.0) + "%");
		System.out.println("average answer rate of cards after test: "
			+ df.format(answerDatasByCardsAfterTest.getAverageAnswerRateOfCards() * 100.0) + "%");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		System.out.println("used time: " + dateFormatted);

		console.readLine();
		System.out.print("\033[H\033[2J");
	}

}
