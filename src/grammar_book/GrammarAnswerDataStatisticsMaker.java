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

	public long getLastStudyTimeOfGrammarItem(int grammarItemIndex) {	//TODO: take to the ancestor class
		long lastStudyTime = 0;
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			AnswerData answerData = answerDataContainer.getAnswerData(i);
			if (answerData.index == grammarItemIndex && lastStudyTime < answerData.date) {
				lastStudyTime = answerData.date;
			}
		}

		return lastStudyTime;
	}

	public int getLastStudiedGrammarItemIndex() {	//TODO: take to the ancestor class

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

}
