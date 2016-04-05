package dictionary;

import common.*;
//import settings_handler.*;

import java.util.*;
/*import java.io.Console;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;*/

public class CardChooser {

	private Random randomGenerator = new Random();
	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
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
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());
		int r = randomGenerator.nextInt((int)Math.floor((double)answerDataByStudyItemsContainer.numberOfStudyItems() * hardestWordRate));
		return datasToSort[datasToSort.length - 1 - r].getAnswer(0).index;
	}

	public int getRandomHardestCardIndex2(int numberOfHardestCards) {		//TODO: implement it more efficiently, too slow
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());
		int r = randomGenerator.nextInt(numberOfHardestCards);
		return datasToSort[datasToSort.length - 1 - r].getAnswer(0).index;
	}

	public Set<Integer> getCardIndexesWithLestSignificantAnswerRate(int numberOfCards, Set<Integer> omitedCardIndexes) {
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

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
		AnswerDataByStudyItemsContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemsContainer();
		answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

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

	public int getRandomCardIndex() {
		return randomGenerator.nextInt(cardContainer.numberOfCards());
	}

	public Set<Integer> chooseCardsToTestIndexesForTest1() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		int index;
		for (int i=0; i<20; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest2() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(6);

		int index;
		for (int i=0; i<6; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		for (int i=0; i<8; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest3() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		int index;
		for (int i=0; i<8; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		for (int i=0; i<8; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest4() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		int index;
		for (int i=0; i<10; i++) {
			do {
				index = getRandomHardestCardIndex2(100);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		for (int i=0; i<6; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest5() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		int index;
		for (int i=0; i<8; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		Set<Integer> cardsToAdd = getCardIndexesWithLestSignificantAnswerRate(4, cardsToTestIndexes);
		for (int i : cardsToAdd) {
			cardsToTestIndexes.add(i);
		}

		for (int i=0; i<4; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest6() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		int index;
		for (int i=0; i<8; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		Set<Integer> cardsToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(2, cardsToTestIndexes);
		for (int i : cardsToAdd) {
			cardsToTestIndexes.add(i);
		}

		for (int i=0; i<6; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}

	public Set<Integer> chooseCardsToTestIndexesForTest7() {

		Set<Integer> cardsToTestIndexes = new HashSet<Integer>();

		cardsToTestIndexes = getLatestQuestionedCardIndexes(4);

		int index;
		for (int i=0; i<4; i++) {
			do {
				index = getRandomHardestCardIndex(0.2);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		for (int i=0; i<4; i++) {
			do {
				index = getRandomHardestCardIndex2(100);
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		Set<Integer> cardsToAdd = getCardIndexesAmongCardsWithThe100LestSignificantAnswerRate(2, cardsToTestIndexes);
		for (int i : cardsToAdd) {
			cardsToTestIndexes.add(i);
		}

		for (int i=0; i<6; i++) {
			do {
				index = getRandomCardIndex();
			} while (cardsToTestIndexes.contains(index));
			cardsToTestIndexes.add(index);
		}

		return cardsToTestIndexes;
	}
}
