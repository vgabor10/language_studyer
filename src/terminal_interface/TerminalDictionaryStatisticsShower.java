package terminal_interface;

import grammar_book.*;
import settings_handler.*;
import common.*;

import java.util.*;
import java.text.DecimalFormat;	

public class TerminalDictionaryStatisticsShower {

	AnswerDataStatisticsMaker answerDataStatisticsMaker;

	public TerminalDictionaryStatisticsShower() {
	}

	public void setAnswerDataStatisticsMaker(AnswerDataStatisticsMaker adst) {
		answerDataStatisticsMaker = adst;
	}

	public void toScreenNumberOfCards() {
		System.out.println("number of cards: " + answerDataStatisticsMaker.numberOfStudyItems());
	}

	public void toScreenNumberOfAnswers() {
		System.out.println("number of answers: " + answerDataStatisticsMaker.numberOfAnswers());
	}

	public void toSreenAverageAnswerRateOfCards() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("average answer rate of cards: "  + df.format(answerDataStatisticsMaker.averageAnswerRateOfStudyItems()) + "%");
	}

	public void toScreenPercentageOfRightAnswers() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("percantage of right answers: " + df.format(answerDataStatisticsMaker.percentageOfRightAnswers()) + "%");
	}

	public void toScreenPractisingTime() {
		int[] a = answerDataStatisticsMaker.practisingTime();
		System.out.println("practising time: " + a[0] + " hours " + a[1] + " minutes");
	}

	public void toScreenNumberOfQuestionsOfLeastStudiedCard() {
		System.out.println("number of questions of least studied card: " 
			+ answerDataStatisticsMaker.numberOfQuestionsOfLeastStudiedStudyItem());
	}

	public void toScreennumberOfCardsQuestioned() {
		System.out.println("number of cards questioned: " + answerDataStatisticsMaker.numberOfStudyItemsQuestioned());
	}	

	public void toSreenLastQuestionedCardDate() {
		Date date=new Date(answerDataStatisticsMaker.getLastQuestionedStudyItemDate());
		System.out.println("date of last questioned card: " + date);
	}

	public void toScreenNumberOfAnswersGivenLastDays(int numberOfDays) {	//TODO: code repetition with TerminalGrammarStatisticsShower
		Vector<Integer> numberOfAnswersGivenLastDays = answerDataStatisticsMaker.toScreenNumberOfAnswersGivenLastDays(10);
		DecimalFormat df = new DecimalFormat("#.00");

		Date date = new Date();
		GeneralFunctions generalFunctions = new GeneralFunctions();
		int today = generalFunctions.milisecToDay(date.getTime());

		System.out.println("number of answers given last days (number of days before today - number of answers (percentage of right answers)): ");
		for (int i=0; i<numberOfDays; i++) {
			System.out.println(i + " - " +numberOfAnswersGivenLastDays.get(i) 
				+ " (" + df.format(answerDataStatisticsMaker.percentageOfRightAnswersAtDay(today-i)) + "%)");
		}
	}

	public void toScreenHistogramOfCardsByAnswerRate() {
		System.out.println("histogram of cards by right answer rate (category ---> number of StudyItems in category (percentage of StudyItems)):");

		int [] numberOfStudyItemsInCategory = answerDataStatisticsMaker.getHistogramOfStudyItemsByAnswerRate();

		DecimalFormat df = new DecimalFormat("#.00");
		for (int i=9; 0<=i;i--) {
			System.out.println(i*10 + "% - " + (i+1) * 10 + "% ---> " + numberOfStudyItemsInCategory[i] + " (" 
				+ df.format((double)numberOfStudyItemsInCategory[i] * 100.0 / 							(double)answerDataStatisticsMaker.numberOfStudyItems()) + "%)");
		}
	}

	public void toSreenNumberOfStudyingDays() {	//TODO: code repetition with TerminalGrammarStatisticsShower
		System.out.println("number of studying days: " + answerDataStatisticsMaker.getNumberOfStudyingDays());
	}

	public void toScreenDictionaryBasicStatistics() {
		System.out.print("\033[H\033[2J");

		toScreenNumberOfCards();
		toScreenNumberOfAnswers();
		toScreennumberOfCardsQuestioned();
		toScreenNumberOfQuestionsOfLeastStudiedCard();
		toSreenLastQuestionedCardDate();
		toSreenNumberOfStudyingDays();
		toScreenPractisingTime();
		toScreenPercentageOfRightAnswers();
		toSreenAverageAnswerRateOfCards();
		answerDataStatisticsMaker.toScreenProgress(10);
		toScreenNumberOfAnswersGivenLastDays(10);
		toScreenHistogramOfCardsByAnswerRate();
	}

}
