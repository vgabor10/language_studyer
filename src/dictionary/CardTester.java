package dictionary;

import common.*;
import experimental_classes.CardChooser2;
import settings_handler.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;

public class CardTester {

	private Random randomGenerator = new Random();
	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;
	private	List<Integer> cardsToTestIndexes = new Vector<Integer>();
	private CardChooser2 cardChooser = new CardChooser2();

	private Logger logger = new Logger();

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	//20 random cards
	public void performTest1() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest1());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

	//6 latest studyed cards, 6 among hardest cards, 8 random cards
	public void performTest2() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest2());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

	//4 latest studyed cards, 8 among hardest cards, 8 random cards
	public void performTest3() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest3());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

	//10 among the hardest 100, 4 latest studied, 6 random cards
	public void performTest4() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest4());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

	//4 latest studyed cards, 8 among hardest cards, 4 cards with least significant answer rate, 4 random cards
	public void performTest5() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest5());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

	//4 latest studyed cards, 8 among hardest cards, 2 cards among cards with the 100 lest significant answer rate, 6 random cards
	public void performTest6() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest6());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

	//4 latest studyed cards, 4 among hardest 20%, 4 from the hardes 100, 2 among cards with the 100 lest significant answer rate, 6 random cards
	public void performTest7() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest7());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}

        //4 latest studyed cards, 4 among hardest 20%, 4 from the hardes 100, 2 among cards with the 100 lest significant answer rate, 6 random cards
	public void performTest8() {
		cardChooser.setCardContainer(cardContainer);
		cardChooser.setAnswerDataContainer(answerDataContainer);

		cardsToTestIndexes.addAll(cardChooser.chooseCardsToTestIndexesForTest8());
		java.util.Collections.shuffle(cardsToTestIndexes);
		performTest();
	}
        
	private Map<String, Integer> getAcceptabelAnswersAndCardIndexes(String definition) {
		Map<String, Integer> acceptableAnswersAndCardIndexes = new HashMap<String, Integer>();

		Set<String> definitionParts = new HashSet<String>(Arrays.asList(definition.split(", ")));

		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			Card card = cardContainer.getCardByOrder(i);

			Set<String> definitionParts2 = new HashSet<String>(Arrays.asList(card.definition.split(", ")));

			definitionParts2.retainAll(definitionParts);

			if (definitionParts2.size() != 0) {
				acceptableAnswersAndCardIndexes.put(card.term, card.index);
			}
		}

		return acceptableAnswersAndCardIndexes;
	}

	private void performTest() { 

		logger.debug("cardsToTestIndexes: " + cardsToTestIndexes.toString());

		Console console = System.console();
		DecimalFormat df = new DecimalFormat("#.00");

		long startTime = System.currentTimeMillis();

		AnswerDataContainer testAnswers = new AnswerDataContainer();

		for (int i=0; i<cardsToTestIndexes.size(); i++) {

			Card card = cardContainer.getCardByIndex(cardsToTestIndexes.get(i));
			Map<String, Integer> acceptabelAnswersAndCardIndexes = getAcceptabelAnswersAndCardIndexes(card.definition);

			logger.debug("questioned card: " + card.toString());
			logger.debug("acceptabel answers and card indexes: " + acceptabelAnswersAndCardIndexes.toString());

			System.out.print("\033[H\033[2J");
			System.out.println((i+1) + "\\" + cardsToTestIndexes.size());
			System.out.println("-------------------------------");
			System.out.println(card.definition);
			String answer = console.readLine();

			logger.debug("answer: " + answer);

			if (answer.equals(card.term)) {	//right answer
				Date date = new Date();
				testAnswers.addElement(card.index, true, date.getTime());
				logger.debug("added answer data: " + card.index + ", true");
			}
			else {				//wrong answer

				if (acceptabelAnswersAndCardIndexes.keySet().contains(answer)) {
					Date date = new Date();
					testAnswers.addElement(acceptabelAnswersAndCardIndexes.get(answer), true, date.getTime());
					logger.debug("added answer data: " + acceptabelAnswersAndCardIndexes.get(answer) + ", true");
					do {
						System.out.print("\033[H\033[2J");
						System.out.println((i+1) + "\\" + cardsToTestIndexes.size());
						System.out.println("-------------------------------");
						System.out.println(card.definition);
						System.out.println("RIGHT, but the following word was tought:");
						System.out.println(card.term);
						answer = console.readLine();
					} while (!answer.equals(card.term));
				}
				else {
					Date date = new Date();
					testAnswers.addElement(card.index, false, date.getTime());
					logger.debug("added answer data: " + card.index + ", false");
					do {
						System.out.print("\033[H\033[2J");
						System.out.println((i+1) + "\\" + cardsToTestIndexes.size());
						System.out.println("-------------------------------");
						System.out.println(card.definition);
						System.out.println(card.term);
						answer = console.readLine();
					} while (!answer.equals(card.term));
				}
			}
		}

		long endTime = System.currentTimeMillis();

		System.out.print("\033[H\033[2J");

		AnswerDataByStudyItemsContainer answerDatasByStudyItemsBeforeTest = new AnswerDataByStudyItemsContainer();
		answerDatasByStudyItemsBeforeTest.loadDataFromAnswerDataContainer(answerDataContainer);

		SettingsHandler settingsHandler = new SettingsHandler();
		testAnswers.appendToAnswerDataFile(settingsHandler.getStudiedLanguageAnswerDataPath());

		answerDataContainer.appendAnswerDataContainer(testAnswers);

		logger.debug("test asnswers:\n" + testAnswers.toString());

		AnswerDataByStudyItemsContainer answerDatasByStudyItemsAfterTest = new AnswerDataByStudyItemsContainer();
		answerDatasByStudyItemsAfterTest.loadDataFromAnswerDataContainer(answerDataContainer);

		CardTestStatisticsMaker cardTestStatisticsMaker = new CardTestStatisticsMaker();
		cardTestStatisticsMaker.setCardContainer(cardContainer);
		cardTestStatisticsMaker.setTestAnswers(testAnswers);
		cardTestStatisticsMaker.setAnswerDatasByStudyItemsBeforeTest(answerDatasByStudyItemsBeforeTest);
		cardTestStatisticsMaker.setAnswerDatasByStudyItemsAfterTest(answerDatasByStudyItemsAfterTest);
		cardTestStatisticsMaker.setStartAndEndTime(startTime, endTime);
		cardTestStatisticsMaker.toScreenStatistics();

		console.readLine();
	}

}
