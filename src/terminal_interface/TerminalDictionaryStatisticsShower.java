package terminal_interface;

import grammar_book.*;
import settings_handler.*;
import common.*;

import java.text.DecimalFormat;	

public class TerminalDictionaryStatisticsShower {

	AnswerDataStatisticsMaker answerDataStatisticsMaker;

	public TerminalDictionaryStatisticsShower() {
	}

	public void setAnswerDataStatisticsMaker(AnswerDataStatisticsMaker adst) {
		answerDataStatisticsMaker = adst;
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

	public void toScreenDictionaryBasicStatistics() {
		System.out.print("\033[H\033[2J");

		System.out.println("number of cards: " + answerDataStatisticsMaker.numberOfStudyItems());
		System.out.println("number of answers: " + answerDataStatisticsMaker.numberOfAnswers());
		System.out.println("number of cards questioned: " + answerDataStatisticsMaker.numberOfStudyItemsQuestioned());
		toScreenNumberOfQuestionsOfLeastStudiedCard();
		answerDataStatisticsMaker.toSreenLastQuestionedStudyItemDate();
		answerDataStatisticsMaker.toSreenNumberOfStudyingDays();
		toScreenPractisingTime();
		toScreenPercentageOfRightAnswers();
		toSreenAverageAnswerRateOfCards();
		answerDataStatisticsMaker.toScreenProgress(10);
		answerDataStatisticsMaker.toScreenNumberOfAnswersGivenLastDays(10);
		answerDataStatisticsMaker.toScreenHistogram();
	}

}
