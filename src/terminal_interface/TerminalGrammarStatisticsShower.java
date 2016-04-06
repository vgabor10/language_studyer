package terminal_interface;

import grammar_book.*;
import settings_handler.*;
import common.*;

import java.util.*;
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

	public void toSreenLastQuestionedGrammarItemDate() {
		Date date=new Date(grammarAnswerDataStatisticsMaker.getLastQuestionedStudyItemDate());
		System.out.println("date of last questioned grammar item: " + date);
	}

	public void toScreenNumberOfAnswersGivenLastDays(int numberOfDays) {	//TODO: code repetition with TerminalGrammarStatisticsShower
		Vector<Integer> numberOfAnswersGivenLastDays = grammarAnswerDataStatisticsMaker.toScreenNumberOfAnswersGivenLastDays(10);
		DecimalFormat df = new DecimalFormat("#.00");

		Date date = new Date();
		GeneralFunctions generalFunctions = new GeneralFunctions();
		int today = generalFunctions.milisecToDay(date.getTime());

		for (int i=0; i<numberOfDays; i++) {
			System.out.println(i + " - " +numberOfAnswersGivenLastDays.get(i) 
				+ " (" + df.format(grammarAnswerDataStatisticsMaker.percentageOfRightAnswersAtDay(today-i)) + "%)");
		}

		System.out.println("number of answers given last days (number of days before today - number of answers (percentage of right answers)): ");
	}

	public void toScreenHistogramOfGrammarItemsByAnswerRate() {
		System.out.println("histogram of grammar items by right answer rate (category ---> number of StudyItems in category (percentage of StudyItems)):");

		int [] numberOfStudyItemsInCategory = grammarAnswerDataStatisticsMaker.getHistogramOfStudyItemsByAnswerRate();

		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=9; 0<=i;i--) {
			System.out.println(i*10 + "% - " + (i+1) * 10 + "% ---> " + numberOfStudyItemsInCategory[i] + " (" 
				+ df.format((double)numberOfStudyItemsInCategory[i] * 100.0 / 							(double)grammarAnswerDataStatisticsMaker.numberOfStudyItems()) + "%)");
		}
	}

	public void toSreenNumberOfStudyingDays() {	//TODO: code repetition with TerminalDictionaryStatisticsShower
		System.out.println("number of studying days: " + grammarAnswerDataStatisticsMaker.getNumberOfStudyingDays());
	}

	public void toScreenGrammarBookBasicStatistics() {
		System.out.print("\033[H\033[2J");

		toScreenNumberOfGrammarItems();
		toScreenNumberOfGrammarItemsWithAtLeast10Examples();
		toScreenNumberOfExamples();
		toScreenNumberOfAnswers();
		toScreenNumberOfStudyItemsQuestioned();
		toScreenNumberOfQuestionsOfLeastStudiedGrammarItem();
		toSreenLastQuestionedGrammarItemDate();
		toSreenNumberOfStudyingDays();
		toScreenPractisingTime();
		toScreenPercentageOfRightAnswers();
		toSreenAverageAnswerRateOfGrammarItems();
		grammarAnswerDataStatisticsMaker.toScreenProgress(10);
		toScreenNumberOfAnswersGivenLastDays(10);
		toScreenHistogramOfGrammarItemsByAnswerRate();
	}
}
