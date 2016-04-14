package terminal_interface;

import common.*;
import grammar_book.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;

import java.lang.reflect.*;

public class GrammarAdditionalStatisticsShower extends AdditionalStatisticsShower {

	public GrammarAdditionalStatisticsShower() {
		super();

		Method method;

		try{
			method = GrammarAdditionalStatisticsShower.class.getDeclaredMethod("toScreenGrammarItemsWithAtLeast10ExamplesOrderedByLastSutdyTime");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("grammar items with at least 10 examples ordered by last sutdy time");

			method = GrammarAdditionalStatisticsShower.class.getDeclaredMethod("toScreenAnswerRatesOfGrammarItems");
			indexAndStatisticsMethods.add(method);
			indexAndStatisticsCaption.add("answer rates of grammar items");

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

	protected void toScreenAnswerRatesOfGrammarItems() {
		System.out.print("\033[H\033[2J");
		Map<Integer, Double> answerRatesByGrammarItems = getGrammarAnswerDataStatisticsMaker().getAnswerRatesByGrammarItems();

		GrammarBook grammarBook = getGrammarAnswerDataStatisticsMaker().getGrammarBook();

		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("CATEGORY - PERCENTAGE");

		for (int i=0; i<grammarBook.numberOfGrammarItems(); i++) {
			GrammarItem grammarItem = grammarBook.getGrammarItemByOrder(i);
			if (answerRatesByGrammarItems.keySet().contains(grammarItem.index)) {
				double answerRate = answerRatesByGrammarItems.get(grammarItem.index);
				System.out.println(grammarItem.title + " - "
					+ df.format(answerRate*100) + "%");
			}
		}

		console.readLine();
	}
}
