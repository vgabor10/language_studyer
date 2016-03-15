package grammar_book;

import java.util.*;
import java.text.DecimalFormat;

public class GrammarAnswerDataStatisticsMaker {

	private GrammarAnswerDataContainer grammarAnswerDataContainer;
	private GrammarBook grammarBook;

	public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer gac) {
		grammarAnswerDataContainer = gac;
	}

	public void setGrammarBook(GrammarBook gb) {
		grammarBook = gb;
	}

	public void toScreenNumberOfGrammarItems() {
		System.out.println("number of grammar items: " + grammarBook.numberOfGrammarItems());
	}

	public void toScreenNumberOfAnswers() {
		System.out.println("number of answers: " + grammarAnswerDataContainer.numberOfAnswers());
	}

	public void toScreenNumberOfExamples() {
		int numberOfExamples = 0;
		Set<Integer> grammarItemIndexes = grammarBook.getGrammarItemIndexes();
		for (int index : grammarItemIndexes) {
			numberOfExamples = numberOfExamples + grammarBook.getGrammarItem(index).numberOfExamples();
		}

		System.out.println("number of examples: " + numberOfExamples);
	}

	public void toScreenPercentageOfRightAnswers() {
		if (grammarAnswerDataContainer.numberOfAnswers() != 0) {
			int sum = 0;
			for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
				if (grammarAnswerDataContainer.getAnswerData(i).isRight) {
					sum++;
				}
			}

			double percentage = (double)sum/(double)grammarAnswerDataContainer.numberOfAnswers() * 100.0;

			DecimalFormat df = new DecimalFormat("#.00");
			System.out.println("percentage of right answers: " + df.format(percentage) + "%");
		}
		else {
			System.out.println("percentage of right answers: there is no answer yet");
		}
	}

	public void toScreenPercentageOfRightAnswersByGrammarItems() {
		Map<Integer,Integer> numberOfRightAnswersByGrammarItems = new HashMap<Integer,Integer>();
		Map<Integer,Integer> numberOfAnswersByGrammarItems = new HashMap<Integer,Integer>();

		GrammarAnswerData grammarAnswerData;

		for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
			grammarAnswerData = grammarAnswerDataContainer.getAnswerData(i);

			//System.out.println(grammarAnswerData.grammarItemIndex);	//debug

			if (numberOfAnswersByGrammarItems.containsKey(grammarAnswerData.grammarItemIndex)) {

				if (grammarAnswerData.isRight) {
					int numberOfRightAnswers = numberOfRightAnswersByGrammarItems.get(grammarAnswerData.grammarItemIndex);
					numberOfRightAnswersByGrammarItems.remove(grammarAnswerData.grammarItemIndex);
					numberOfRightAnswers++;
					numberOfRightAnswersByGrammarItems.put(grammarAnswerData.grammarItemIndex,numberOfRightAnswers);
				}

				int numberOfAnswers = numberOfAnswersByGrammarItems.get(grammarAnswerData.grammarItemIndex);
				numberOfAnswersByGrammarItems.remove(grammarAnswerData.grammarItemIndex);
				numberOfAnswers++;
				numberOfAnswersByGrammarItems.put(grammarAnswerData.grammarItemIndex,numberOfAnswers);
			}
			else {

				if (grammarAnswerData.isRight) {
					numberOfRightAnswersByGrammarItems.put(grammarAnswerData.grammarItemIndex,1);
				}
				else {
					numberOfRightAnswersByGrammarItems.put(grammarAnswerData.grammarItemIndex,0);
				}

				numberOfAnswersByGrammarItems.put(grammarAnswerData.grammarItemIndex,1);
			}
		}

		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("grammar item index | category | percentage (number of answers)");
		for (int grammarItemIndex : numberOfRightAnswersByGrammarItems.keySet()) {
			double percentage = (double)(numberOfRightAnswersByGrammarItems.get(grammarItemIndex)) *100.0 /
				(double)(numberOfAnswersByGrammarItems.get(grammarItemIndex));
			System.out.println(grammarItemIndex + " | " + grammarBook.getGrammarItem(grammarItemIndex).title + " | "
				+ df.format(percentage) + "%" + " (" + numberOfAnswersByGrammarItems.get(grammarItemIndex) + ")");
		}
	}

	/*public void toScreenProgressByCards() {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
		Arrays.sort(datasToSort, new AnswerDataByCardComparatorByRateOfRightAnswers());
		String out = "";
		for (int i=0; i<answerDataByCardsContainer.numberOfCards(); i++) {
			out = datasToSort[i].toStringShowingPercentage(cardContainer);
			System.out.println(out);
		}
	}

	public int milisecToDay(long milisecTime) {
		return (int)(milisecTime/(1000*3600*24));
	}

	public void toScreenNumberOfAnswersGivenLastDays(int numberOfDays) {
		int [] cardsStadiedLastDays = new int[numberOfDays];
		Date date = new Date();
		int today = milisecToDay(date.getTime());
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (today - numberOfDays < milisecToDay(answerDataContainer.data.get(i).date)) {
				cardsStadiedLastDays[today - milisecToDay(answerDataContainer.data.get(i).date)]++;
			}
		}

		for (int i=0; i<numberOfDays; i++) {
			System.out.print(cardsStadiedLastDays[i] + " ");
		}
	}

	public void toScreenHardestWords(int numberOfWords) {
		AnswerDataByCardsContainer answerDataByCardsContainer = new AnswerDataByCardsContainer();
		answerDataByCardsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

		if (numberOfWords <= answerDataByCardsContainer.numberOfCards()) {
			AnswerDataByCard[] datasToSort = answerDataByCardsContainer.toArray();
			Arrays.sort(datasToSort, new AnswerDataByCardComparatorByRateOfRightAnswers());
			String out = "";

			for (int i=0; i<numberOfWords; i++) {
				out = datasToSort[answerDataByCardsContainer.numberOfCards() - 1 - i].toStringShowingPercentage(cardContainer);
				System.out.println(out);
			}
		}
	}*/

}
