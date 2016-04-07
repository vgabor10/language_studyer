package grammar_book;

import common.*;

import java.util.*;
import java.text.DecimalFormat;

public class GrammarAnswerDataStatisticsMaker extends AnswerDataStatisticsMaker {

	public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer gac) {
		setAnswerDataContainer(gac);
	}

	public void setGrammarBook(GrammarBook grammarBook) {
		studyItemContainer = grammarBook;
	}

	public GrammarBook getGrammarBook() {
		return (GrammarBook)studyItemContainer;
	}

	public int numberOfGrammarItems() {
		return getGrammarBook().numberOfGrammarItems();
	}

	public int numberOfExamples() {
		int numberOfExamples = 0;
		Set<Integer> grammarItemIndexes = getGrammarBook().getGrammarItemIndexes();
		for (int index : grammarItemIndexes) {
			numberOfExamples = numberOfExamples + getGrammarBook().getGrammarItemByIndex(index).numberOfExamples();
		}

		return numberOfExamples;
	}

	public long getLastStudyTimeOfGrammarItem(int grammarItemIndex) {
		long lastStudyTime = 0;
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			AnswerData answerData = answerDataContainer.getAnswerData(i);
			if (answerData.index == grammarItemIndex && lastStudyTime < answerData.date) {
				lastStudyTime = answerData.date;
			}
		}

		return lastStudyTime;
	}

	public int getLastStudiedGrammarItemIndex() {

		Map<Integer,Long> GrammarItemIndexAndLastStudyTime = new HashMap<Integer,Long>();

		for (int i=0; i<getGrammarBook().numberOfGrammarItems(); i++) {
			GrammarItem grammarItem	=  getGrammarBook().getGrammarItemByOrder(i);

			if (10 <= grammarItem.numberOfExamples()) {
				long lastStudyTime = getLastStudyTimeOfGrammarItem(grammarItem.index);
				GrammarItemIndexAndLastStudyTime.put(grammarItem.index, lastStudyTime);
			}
		}

		int lastStudiedGrammarItemIndex = -1;
		long lastStudiedGrammarItemDate = Long.MAX_VALUE;
		for (int index : GrammarItemIndexAndLastStudyTime.keySet()) {
			if (GrammarItemIndexAndLastStudyTime.get(index) < lastStudiedGrammarItemDate) {
				lastStudiedGrammarItemDate = GrammarItemIndexAndLastStudyTime.get(index);
				lastStudiedGrammarItemIndex = index;
			}	
		}

		return lastStudiedGrammarItemIndex;
	}

	public void toScreenGrammarItemsWithAtLeast10ExamplesOrderedByLastSutdyTime() {
		Map<Long,Integer> LastStudyTimeAndGrammarItemIndex = new HashMap<Long,Integer>();
		int numberOfUntestedGrammarItems = 0;
		for (int grammarItemIndex : getGrammarBook().getGrammarItemIndexes()) {
			if (10 <= getGrammarBook().getGrammarItemByIndex(grammarItemIndex).numberOfExamples()) {
				long lastStudyDate = getLastStudyTimeOfGrammarItem(grammarItemIndex);
				if (lastStudyDate == 0) {
					numberOfUntestedGrammarItems++;
					lastStudyDate = -1 * numberOfUntestedGrammarItems;
				}
				LastStudyTimeAndGrammarItemIndex.put(lastStudyDate,grammarItemIndex);
			}
		}

		Set<Long> SortedLastStudyTime = new TreeSet<Long>(LastStudyTimeAndGrammarItemIndex.keySet());

		System.out.println("GRAMMAR ITEM TITLE - LAST TIME STUDYED");
		for (long date : SortedLastStudyTime) {
			int grammarItemIndex = LastStudyTimeAndGrammarItemIndex.get(date);
			if (date < 0) {
				System.out.println(getGrammarBook().getGrammarItemByIndex(grammarItemIndex).title.toString()
					+ " | grammar item never been tested");
			}
			else {
				Date date2 = new Date(date);
				System.out.println(getGrammarBook().getGrammarItemByIndex(grammarItemIndex).title.toString()
					+ " | " + date2);
			}
		}
	}

	public int numberOfGrammarItemsWithAtLeast10Examples() {
		int numberOfGrammarItems = 0;
		for (int grammarItemIndex : getGrammarBook().getGrammarItemIndexes()) {
			if (10 <= getGrammarBook().getGrammarItemByIndex(grammarItemIndex).numberOfExamples()) {
				numberOfGrammarItems++;
			}
		}
		return numberOfGrammarItems;
	}

	public void toScreenPercentageOfRightAnswersByGrammarItems() {		//TODO: make it to toScreenPercentageOfRightAnswersByStudyItems
		Map<Integer,Integer> numberOfRightAnswersByGrammarItems = new HashMap<Integer,Integer>();
		Map<Integer,Integer> numberOfAnswersByGrammarItems = new HashMap<Integer,Integer>();

		GrammarAnswerData grammarAnswerData;

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			grammarAnswerData = (GrammarAnswerData)answerDataContainer.getAnswerData(i);

			//System.out.println(grammarAnswerData.index);	//debug

			if (numberOfAnswersByGrammarItems.containsKey(grammarAnswerData.index)) {

				if (grammarAnswerData.isRight) {
					int numberOfRightAnswers = numberOfRightAnswersByGrammarItems.get(grammarAnswerData.index);
					numberOfRightAnswersByGrammarItems.remove(grammarAnswerData.index);
					numberOfRightAnswers++;
					numberOfRightAnswersByGrammarItems.put(grammarAnswerData.index,numberOfRightAnswers);
				}

				int numberOfAnswers = numberOfAnswersByGrammarItems.get(grammarAnswerData.index);
				numberOfAnswersByGrammarItems.remove(grammarAnswerData.index);
				numberOfAnswers++;
				numberOfAnswersByGrammarItems.put(grammarAnswerData.index,numberOfAnswers);
			}
			else {

				if (grammarAnswerData.isRight) {
					numberOfRightAnswersByGrammarItems.put(grammarAnswerData.index,1);
				}
				else {
					numberOfRightAnswersByGrammarItems.put(grammarAnswerData.index,0);
				}

				numberOfAnswersByGrammarItems.put(grammarAnswerData.index,1);
			}
		}

		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("CATEGORY | PERCENTAGE (NUMBER OF ANSWERS)");
		/*for (int grammarItemIndex : numberOfRightAnswersByGrammarItems.keySet()) {
			double percentage = (double)(numberOfRightAnswersByGrammarItems.get(grammarItemIndex)) *100.0 /
				(double)(numberOfAnswersByGrammarItems.get(grammarItemIndex));
			System.out.println(grammarItemIndex + " | " + getGrammarBook().getGrammarItemByIndex(grammarItemIndex).title + " | "
				+ df.format(percentage) + "%" + " (" + numberOfAnswersByGrammarItems.get(grammarItemIndex) + ")");
		}*/
		for (int i=0; i<getGrammarBook().numberOfGrammarItems(); i++) {
			GrammarItem grammarItem = getGrammarBook().getGrammarItemByOrder(i);
			if ( numberOfRightAnswersByGrammarItems.keySet().contains(grammarItem.index)) {
				double percentage 
					= (double)(numberOfRightAnswersByGrammarItems.get(grammarItem.index)) *100.0 /
					(double)(numberOfAnswersByGrammarItems.get(grammarItem.index));

				System.out.println(getGrammarBook().getGrammarItemByIndex(grammarItem.index).title + " | "
					+ df.format(percentage) + "%" + " (" + numberOfAnswersByGrammarItems.get(grammarItem.index) + ")");
			}
		}
	}

	public void toScreenNumberOfAnswersByDays() {
		Map<Integer,Integer> numberOfAnswersByDays = new HashMap<Integer,Integer>();
		GeneralFunctions generalFunctions = new GeneralFunctions();

		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			long date = answerDataContainer.getAnswerData(i).date;
			int day = generalFunctions.milisecToDay(date);
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

}
