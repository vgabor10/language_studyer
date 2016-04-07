package terminal_interface;

import common.*;
import grammar_book.*;

import java.util.*;
import java.io.Console;

import java.lang.reflect.*;

public class GrammarAdditionalStatisticsShower extends AdditionalStatisticsShower {

	public GrammarAdditionalStatisticsShower() {
		super();

		Method method;

		try{
			method = GrammarAdditionalStatisticsShower.class.getDeclaredMethod("toScreenGrammarItemsWithAtLeast10ExamplesOrderedByLastSutdyTime");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("grammar items with at least 10 examples ordered by last sutdy time");

			method = GrammarAdditionalStatisticsShower.class.getDeclaredMethod("toScreenPercentageOfRightAnswersByGrammarItems");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("to screen percentage of right answers by grammar items");

		} catch(Exception ex){
			logger.debug("error at loading statistics methods");
		}
	}

	public void setGrammarAnswerDataStatisticsMaker(GrammarAnswerDataStatisticsMaker gam) {
		answerDataStatisticsMaker = gam;
	}

	private GrammarAnswerDataStatisticsMaker getGrammarAnswerDataStatisticsMaker() {
		return (GrammarAnswerDataStatisticsMaker)answerDataStatisticsMaker;
	}

	protected void toScreenGrammarItemsWithAtLeast10ExamplesOrderedByLastSutdyTime() {
		System.out.print("\033[H\033[2J");
		getGrammarAnswerDataStatisticsMaker().toScreenGrammarItemsWithAtLeast10ExamplesOrderedByLastSutdyTime();
		console.readLine();
	}

	protected void toScreenPercentageOfRightAnswersByGrammarItems() {
		System.out.print("\033[H\033[2J");
		getGrammarAnswerDataStatisticsMaker().toScreenPercentageOfRightAnswersByGrammarItems();
		console.readLine();
	}
}
