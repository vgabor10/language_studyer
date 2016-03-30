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
			numberOfExamples = numberOfExamples + grammarBook.getGrammarItemByIndex(index).numberOfExamples();
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
		System.out.println("CATEGORY | PERCENTAGE (NUMBER OF ANSWERS)");
		/*for (int grammarItemIndex : numberOfRightAnswersByGrammarItems.keySet()) {
			double percentage = (double)(numberOfRightAnswersByGrammarItems.get(grammarItemIndex)) *100.0 /
				(double)(numberOfAnswersByGrammarItems.get(grammarItemIndex));
			System.out.println(grammarItemIndex + " | " + grammarBook.getGrammarItemByIndex(grammarItemIndex).title + " | "
				+ df.format(percentage) + "%" + " (" + numberOfAnswersByGrammarItems.get(grammarItemIndex) + ")");
		}*/
		for (int i=0; i<grammarBook.numberOfGrammarItems(); i++) {
			GrammarItem grammarItem = grammarBook.getGrammarItemByOrder(i);
			if ( numberOfRightAnswersByGrammarItems.keySet().contains(grammarItem.index)) {
				double percentage 
					= (double)(numberOfRightAnswersByGrammarItems.get(grammarItem.index)) *100.0 /
					(double)(numberOfAnswersByGrammarItems.get(grammarItem.index));

				System.out.println(grammarBook.getGrammarItemByIndex(grammarItem.index).title + " | "
					+ df.format(percentage) + "%" + " (" + numberOfAnswersByGrammarItems.get(grammarItem.index) + ")");
			}
		}
	}

	public int milisecToDay(long milisecTime) {	//TODO: take to an other class
		int timeZone = 1;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public void toScreenNumberOfAnswersByDays() {
		Map<Integer,Integer> numberOfAnswersByDays = new HashMap<Integer,Integer>();

		for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
			long date = grammarAnswerDataContainer.getAnswerData(i).date;
			int day = milisecToDay(date);
			if (numberOfAnswersByDays.containsKey(day)) {
				int numberOfAnswersAtDay = numberOfAnswersByDays.get(day);
				numberOfAnswersAtDay++;
				numberOfAnswersByDays.remove(day);
				numberOfAnswersByDays.put(day, numberOfAnswersAtDay);
			}
			else {
				numberOfAnswersByDays.put(day, 1);
			}
		}

		Set<Integer> days = numberOfAnswersByDays.keySet();
		SortedSet<Integer> sortedDays = new TreeSet<Integer>(days);
		System.out.println("DAY - NUMBER OF ANSWERS AT DAY");
		for (int day : sortedDays) {
			System.out.println(day + " - " + numberOfAnswersByDays.get(day));
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
