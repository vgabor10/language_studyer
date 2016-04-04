package terminal_interface;

import grammar_book.*;
import settings_handler.*;
import common.*;

import java.text.DecimalFormat;	

public class TerminalGrammarStatisticsShower {

	GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker;

	public TerminalGrammarStatisticsShower() {
	}

	public void toSreenAverageAnswerRateOfGrammarItems() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("average answer rate of grammar items: "  + df.format(grammarAnswerDataStatisticsMaker.averageAnswerRateOfStudyItems()) + "%");
	}

	public void setGrammarAnswerDataStatisticsMaker(GrammarAnswerDataStatisticsMaker gadst) {
		grammarAnswerDataStatisticsMaker = gadst;
	}

	public void toScreenPercentageOfRightAnswers() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("percantage of right answers: " 
			+ df.format(grammarAnswerDataStatisticsMaker.percentageOfRightAnswers()) + "%");
	}

	public void toScreenPractisingTime() {
		int[] a = grammarAnswerDataStatisticsMaker.practisingTime();
		System.out.println("practising time: " + a[0] + " hours " + a[1] + " minutes");
	}

	public void toScreenNumberOfQuestionsOfLeastStudiedCard() {
		System.out.println("number of questions of least studied grammar item: " 
			+ grammarAnswerDataStatisticsMaker.numberOfQuestionsOfLeastStudiedStudyItem());
	}

	public void toScreenNumberOfQuestionsOfLeastStudiedGrammarItem() {
		System.out.println("number of questions of least studied grammar item: " 
			+ grammarAnswerDataStatisticsMaker.numberOfQuestionsOfLeastStudiedStudyItem());
	}

	public void toScreenNumberOfGrammarItems() {
		System.out.println("number of grammar items: " + grammarAnswerDataStatisticsMaker.numberOfGrammarItems());
	}

	public void toScreenNumberOfGrammarItemsWithAtLeast10Examples() {
		System.out.println("number of grammar items with at least 10 examples: " 
			+ grammarAnswerDataStatisticsMaker.numberOfGrammarItemsWithAtLeast10Examples());
	}

	public void toScreenNumberOfExamples() {
		System.out.println("number of examples: " + grammarAnswerDataStatisticsMaker.numberOfExamples());
	}

	public void toScreenNumberOfAnswers() {
		System.out.println("number of answers: " + grammarAnswerDataStatisticsMaker.numberOfAnswers());
	}

	public void toScreenNumberOfStudyItemsQuestioned() {
		System.out.println("number of grammar items questioned: " + grammarAnswerDataStatisticsMaker.numberOfStudyItemsQuestioned());
	}

	public void toScreenGrammarBookBasicStatistics() {
		System.out.print("\033[H\033[2J");

		toScreenNumberOfGrammarItems();
		toScreenNumberOfGrammarItemsWithAtLeast10Examples();
		toScreenNumberOfExamples();
		toScreenNumberOfAnswers();
		toScreenNumberOfStudyItemsQuestioned();
		toScreenNumberOfQuestionsOfLeastStudiedGrammarItem();
		grammarAnswerDataStatisticsMaker.toSreenLastQuestionedStudyItemDate();
		grammarAnswerDataStatisticsMaker.toSreenNumberOfStudyingDays();
		toScreenPractisingTime();
		toScreenPercentageOfRightAnswers();
		toSreenAverageAnswerRateOfGrammarItems();
		grammarAnswerDataStatisticsMaker.toScreenProgress(10);
		grammarAnswerDataStatisticsMaker.toScreenNumberOfAnswersGivenLastDays(10);
		grammarAnswerDataStatisticsMaker.toScreenHistogram();
	}
}
