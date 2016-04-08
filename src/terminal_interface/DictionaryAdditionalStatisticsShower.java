package terminal_interface;

import common.*;
import dictionary.*;

import java.util.*;
import java.io.Console;

import java.lang.reflect.*;

public class DictionaryAdditionalStatisticsShower extends AdditionalStatisticsShower {

	public DictionaryAdditionalStatisticsShower() {
		super();

		Method method;

		try{
			method = DictionaryAdditionalStatisticsShower.class.getDeclaredMethod("toScreenHardestCards");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("hardest words");
		} catch(Exception ex){
			logger.debug("error at loading statistics methods");
		}
	}

	public void setDictionaryAnswerDataStatisticsMaker(DictionaryAnswerDataStatisticsMaker dam) {
		answerDataStatisticsMaker = dam;
	}

	private DictionaryAnswerDataStatisticsMaker getDictionaryAnswerDataStatisticsMaker() {
		return (DictionaryAnswerDataStatisticsMaker)answerDataStatisticsMaker;
	}

	protected void toScreenHardestCards() {
		Logger logger = new Logger();
		logger.debug("run toScreenHardestCards function");

		Vector<Integer> orderedStudyItemIndexes = getDictionaryAnswerDataStatisticsMaker().getStudyItemIndexesOrderedByAnswerRate();
		CardContainer cardContainer = getDictionaryAnswerDataStatisticsMaker().getCardContainer();

		System.out.print("\033[H\033[2J");
		for (int i=1; i<=40; i++) {
			int cardIndex = orderedStudyItemIndexes.get(orderedStudyItemIndexes.size() - i);
			Card card = cardContainer.getCardByIndex(cardIndex);
			System.out.println(card);
		}
		console.readLine();
	}
}
