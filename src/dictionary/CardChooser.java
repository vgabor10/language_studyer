package dictionary;

import study_item_objects.AnswerDataByStudyItemComparatorByNumberOfAnswers;
import study_item_objects.AnswerData;
import study_item_objects.AnswerDataContainer;
import study_item_objects.AnswerDataByStudyItemsContainer;
import study_item_objects.AnswerDataByStudyItem;
import common.Logger;
import study_item_objects.AnswerDataByStudyItemComparatorByRateOfRightAnswers;

import java.util.*;

public class CardChooser {

	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;

	private final Random randomGenerator = new Random();
	private AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer;
	private final Logger logger = new Logger();

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	//WARNING: first need to be set answerDataContainer
	public void evaluateAnswerDataByStudyItemsContainer() {
		answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
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
			if (!cardIndexesLastQuestionDate.containsKey(cardContainer.getCardByOrder(i).index)) {
				cardIndexesLastQuestionDate.put(cardContainer.getCardByOrder(i).index, Long.MIN_VALUE);
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

		logger.debugActualTime();
		logger.debug("latest quastioned card indexes: " + out);

		return out;
	}

	public Set<Integer> getRandomHardestCardIndexes(double hardestWordRate, int numberOfCards, Set<Integer> omittedCardIndexes) {

		logger.debugActualTime();
		logger.debug("start evaluate random hardest card indexes");

		AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());

		Set<Integer> out = new HashSet<Integer>();
		while (out.size() != numberOfCards) {
			int r = randomGenerator.nextInt((int)Math.floor((double)answerDataByStudyItemsContainer.numberOfStudyItems() * hardestWordRate));
			int randomHardestCardIndex = datasToSort[datasToSort.length - 1 - r].getAnswer(0).index;

			if (!out.contains(randomHardestCardIndex) && !omittedCardIndexes.contains(randomHardestCardIndex)) {
				out.add(randomHardestCardIndex);
			}
		}

		logger.debugActualTime();
		logger.debug("random hardest card indexes: " + out);

		return out;
	}

	public Set<Integer> getRandomHardestCardIndex2(int numberOfHardestCards, int numberOfCardsGet, Set<Integer> omittedCardIndexes) {

		logger.debugActualTime();
		logger.debug("start evaluate random hardest card indexes");

		AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());

		Set<Integer> out = new HashSet<Integer>();
		while (out.size() != numberOfCardsGet) {
			int r = randomGenerator.nextInt(numberOfHardestCards);
			int randomHardestCardIndex = datasToSort[datasToSort.length - 1 - r].getAnswer(0).index;

			if (!out.contains(randomHardestCardIndex) && !omittedCardIndexes.contains(randomHardestCardIndex)) {
				out.add(randomHardestCardIndex);
			}
		}

		logger.debugActualTime();
		logger.debug("random hardest card indexes: " + out);

		return out;
	}

	public Set<Integer> getCardIndexesWithLestSignificantAnswerRate(int numberOfCards, Set<Integer> omitedCardIndexes) {

		AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

		Set<Integer> outCardIndexes = new HashSet<Integer>(); 

		int i=0;
		while (outCardIndexes.size() != numberOfCards && i<datasToSort.length) {
			if (!omitedCardIndexes.contains(datasToSort[i].getStudyItemIndex())) {
				outCardIndexes.add(datasToSort[i].getStudyItemIndex());
			}
			i++;
		}

		return outCardIndexes;
	}

	public Set<Integer> getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(int numberOfCards, Set<Integer> omitedCardIndexes) {

		if (answerDataByStudyItemsContainer.numberOfStudyItems() < 100) {
			return null;
		}
		else {
			AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
			Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

			Set<Integer> outCardIndexes = new HashSet<Integer>(); 

			while (outCardIndexes.size() != numberOfCards) {
				int cardIndex = datasToSort[randomGenerator.nextInt(100)].getStudyItemIndex();
				if (!omitedCardIndexes.contains(cardIndex) && !outCardIndexes.contains(cardIndex)) {
					outCardIndexes.add(cardIndex);
				}
			}

			//System.out.println("outCardIndexes: " + outCardIndexes);	//log

			return outCardIndexes;
		}
	}

	public Set<Integer> getRandomCardIndexes(int numberOfCards, Set<Integer> omittedCardIndexes) {
		Set<Integer> outCardIndexes = new HashSet<Integer>(); 

		while (outCardIndexes.size() != numberOfCards) {
			int orderIndex = randomGenerator.nextInt(cardContainer.numberOfCards());
			int cardIndex = cardContainer.getCardByOrder(orderIndex).index;
			if (!omittedCardIndexes.contains(cardIndex) && !outCardIndexes.contains(cardIndex)) {
				outCardIndexes.add(cardIndex);
			}
		}

		return outCardIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest1() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		Set<Integer> indexesToAdd;

		indexesToAdd = getRandomCardIndexes(20, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest2() {

		evaluateAnswerDataByStudyItemsContainer();

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(6);

		Set<Integer> indexesToAdd;

		indexesToAdd = getRandomHardestCardIndexes(0.2, 6, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getRandomCardIndexes(8, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest3() {

		evaluateAnswerDataByStudyItemsContainer();

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		Set<Integer> indexesToAdd;

		indexesToAdd = getRandomHardestCardIndexes(0.2, 6, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getRandomCardIndexes(8, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest4() {

		evaluateAnswerDataByStudyItemsContainer();

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		Set<Integer> indexesToAdd;

		indexesToAdd = getRandomHardestCardIndex2(100, 10, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getRandomCardIndexes(6, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest5() {

		evaluateAnswerDataByStudyItemsContainer();

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		Set<Integer> indexesToAdd;

		indexesToAdd = getRandomHardestCardIndexes(0.2, 8, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getCardIndexesWithLestSignificantAnswerRate(4, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getRandomCardIndexes(4, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest6() {

		evaluateAnswerDataByStudyItemsContainer();

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		Set<Integer> indexesToAdd = getRandomHardestCardIndexes(0.2, 8, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(2, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		Set<Integer> cardsToAdd = getRandomCardIndexes(6, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest7() {

		logger.debug("start evaluate card indexes for test7");

		evaluateAnswerDataByStudyItemsContainer();

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		Set<Integer> indexesToAdd;

		indexesToAdd = getRandomHardestCardIndexes(0.2, 4, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getRandomHardestCardIndex2(100, 4, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(2, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		indexesToAdd = getRandomCardIndexes(6, cardsToTestIndexes);
		cardsToTestIndexes.addAll(indexesToAdd);

		logger.debug("end evaluate card indexes for test7");

		return cardsToTestIndexes;
	}

	public Set<Integer> getCardIndexesWithMinAnswerRateAndPlusSome(double minAnswerRate, int plusNumberOfCards) {
		Set<Integer> cardIndexes = new HashSet<Integer>();

		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		for (int index : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {

			AnswerDataByStudyItem answerDataByStudyItem 
				= answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index);

			if (0.5 <= answerDataByStudyItem.countRightAnswerRate()) {
				cardIndexes.add(answerDataByStudyItem.getStudyItemIndex());
			}
		}

		int plusNumberOfCardsAdded = 0;
		int i=0;
		while(i<cardContainer.numberOfCards() && plusNumberOfCardsAdded<100) {
			Card card = cardContainer.getCardByOrder(i);
			if (!cardIndexes.contains(card.index)) {
				cardIndexes.add(card.index);
				plusNumberOfCardsAdded++;
			}
			i++;
		}

		return cardIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest8() {

		Set<Integer> cardIndexes = getCardIndexesWithMinAnswerRateAndPlusSome(0.5, 100);

		logger.debug("performTest8: number of card indexes for choice: " + cardIndexes.size());

		AnswerDataContainer answerDataContainer2 = new AnswerDataContainer();
		CardContainer cardContainer2 = new CardContainer();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			AnswerData answerData = answerDataContainer.getAnswerData(i);
			if (cardIndexes.contains(answerData.index)) {
				answerDataContainer2.addAnswerData(answerData);
			}
		}

		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			Card card = cardContainer.getCardByOrder(i);
			if (cardIndexes.contains(card.index)) {
				cardContainer2.addCard(card);
			}
		}

		CardChooser cardChooser2 = new CardChooser();
		cardChooser2.setCardContainer(cardContainer2);
		cardChooser2.setAnswerDataContainer(answerDataContainer2);

		return cardChooser2.chooseCardsToTestIndexesForTest7();
	}
}
