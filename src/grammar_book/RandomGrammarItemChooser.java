package grammar_book;

import common.*;
import java.util.*;

public class RandomGrammarItemChooser {

	private Random randomGenerator = new Random();
	private GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker;
	private GrammarBook grammarBook;
	private Logger logger = new Logger();

	public void setGrammarAnswerDataStatisticsMaker(GrammarAnswerDataStatisticsMaker g) {
		grammarAnswerDataStatisticsMaker = g;
		grammarBook = grammarAnswerDataStatisticsMaker.getGrammarBook();
	}

	public int getRandomGrammarItemIndex() {
		logger.debug("run getRandomGrammarItemIndex function");

		int orderIndex = randomGenerator.nextInt(grammarBook.numberOfGrammarItems());
		int grammarItemIndex = grammarBook.getGrammarItemByOrder(orderIndex).index;

		logger.debug("choosen grammar item index: " + grammarItemIndex);

		return grammarItemIndex;
	}

	public int getRandomIndexFromThe5LeastStudiedGrammarItem() {
		logger.debug("run getRandomIndexFromThe5LeastStudiedGrammarItem function");

		Vector<Integer> grammarItemIndexesOrderedByNumberOfAnswers 
			= grammarAnswerDataStatisticsMaker.getStudyItemIndexesOrderedByNumberOfAnswers();

		int r = /*grammarItemIndexesOrderedByNumberOfAnswers.size() - 1 -*/ randomGenerator.nextInt(5);
		int grammarItemIndex = grammarItemIndexesOrderedByNumberOfAnswers.get(r);

		logger.debug("choosen grammar item index: " + grammarItemIndex);

		return grammarItemIndex;
	}

	public int getIndexFromThe5HardestGrammarItem() {
		logger.debug("run getIndexFromThe5HardestGrammarItem function");

		Vector<Integer> grammarItemIndexesOrderedByAnswerRate 
			= grammarAnswerDataStatisticsMaker.getStudyItemIndexesOrderedByAnswerRate();
		int r = grammarItemIndexesOrderedByAnswerRate.size() - 1 - randomGenerator.nextInt(5);
		int grammarItemIndex = grammarItemIndexesOrderedByAnswerRate.get(r);

		logger.debug("choosen grammar item index: " + grammarItemIndex);

		return grammarItemIndex;
	}

	public int getIndexOfLatestStudiedGrammarItem() {
		logger.debug("run getIndexOfLatestStudiedGrammarItem function");

		int grammarItemIndex = grammarAnswerDataStatisticsMaker.getLastStudiedGrammarItemIndex();

		logger.debug("choosen grammar item index: " + grammarItemIndex);

		return grammarItemIndex;
	}

	//random grammar item from a random, 5 least studied, 5 hardest, latest studyed item
	public int getGrammarItemIndexForTest3() {
		logger.debug("run getGrammarItemIndexForTest3 function");

		Vector<Integer> grammarItemIndexses = new Vector<Integer>();

		grammarItemIndexses.add(getRandomGrammarItemIndex());
		grammarItemIndexses.add(getRandomIndexFromThe5LeastStudiedGrammarItem());
		grammarItemIndexses.add(getIndexFromThe5HardestGrammarItem());
		grammarItemIndexses.add(getIndexOfLatestStudiedGrammarItem());

		int grammarItemIndex = grammarItemIndexses.get(randomGenerator.nextInt(4));

		logger.debug("choosen grammar item index: " + grammarItemIndex);

		return grammarItemIndex;
	}

}
