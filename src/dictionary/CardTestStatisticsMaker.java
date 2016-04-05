package dictionary;

import common.*;

import java.util.*;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CardTestStatisticsMaker {

	private CardContainer cardContainer;
	private AnswerDataContainer testAnswers;
	private AnswerDataByStudyItemsContainer answerDatasByStudyItemsBeforeTest;
	private AnswerDataByStudyItemsContainer answerDatasByStudyItemsAfterTest;
	private long startTime;
	private long endTime;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setTestAnswers(AnswerDataContainer ta) {
		testAnswers = ta;
	}

	public void setAnswerDatasByStudyItemsBeforeTest(AnswerDataByStudyItemsContainer a) {
		answerDatasByStudyItemsBeforeTest = a;
	}

	public void setAnswerDatasByStudyItemsAfterTest(AnswerDataByStudyItemsContainer a) {
		answerDatasByStudyItemsAfterTest = a;
	}

	public void setStartAndEndTime(long st, long et) {
		startTime = st;
		endTime = et;
	}


	public void toScreenStatistics() { 

		Set<Integer> cardIndexes = new HashSet<Integer>();

		DecimalFormat df = new DecimalFormat("#.000");

		for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
			cardIndexes.add(testAnswers.getAnswerData(i).index);
		}

		int numberOfCardsWithImprovement = 0;
		int numberOfCardsWithNoChange = 0;
		int numberOfCardsWithReducement = 0;
		int numberOfNewCardsTested = 0;
		int numberOfCategoryImprovements = 0;
		int numberOfCategoryReducements = 0;
		int[] categorySizeChanges = new int[10];

		System.out.println("CARD | % OF RIGHT ANSWERS AFTER TEST | % OF RIGHT ANSWERS BEFORE TEST | # OF ANSWERS AFTER TEST");
		System.out.println("------------------------------------------------------------------");
		for (int cardIndex : cardIndexes) {

			double percentageOfRightAnswersBeforeTest = -1;
			if (answerDatasByStudyItemsBeforeTest.getTestedStudyItemIndexes().contains(cardIndex)) {
				percentageOfRightAnswersBeforeTest 
					= answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate() * 100.0;
			}

			double percentageOfRightAnswersAfterTest
				= answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate() * 100.0;
			
			System.out.print(cardContainer.getCard(cardIndex).toString() + " | " 
				+ df.format(percentageOfRightAnswersAfterTest) + "% | ");
			if (percentageOfRightAnswersBeforeTest != -1) {
				System.out.print(df.format(percentageOfRightAnswersBeforeTest) + "% | ");

				if (percentageOfRightAnswersBeforeTest < percentageOfRightAnswersAfterTest) {
					numberOfCardsWithImprovement++;
				}
				else 
				if (percentageOfRightAnswersBeforeTest > percentageOfRightAnswersAfterTest) {
					numberOfCardsWithReducement++;
				}
				else {
					numberOfCardsWithNoChange++;
				}


				///////////////// category statistics ////////////////
				double v1, v2;
				if (percentageOfRightAnswersAfterTest == 100.0) {
					v1 = percentageOfRightAnswersAfterTest-0.001;
				}
				else {
					v1 = percentageOfRightAnswersAfterTest;
				}

				if (percentageOfRightAnswersBeforeTest == 100.0) {
					v2 = percentageOfRightAnswersBeforeTest-0.001;
				}
				else {
					v2 = percentageOfRightAnswersBeforeTest;
				}

				int a = (int)Math.floor(v1/10.0) - (int)Math.floor(v2/10.0);
				if (a<0) {
					numberOfCategoryReducements = numberOfCategoryReducements - a;
				}
				if (a>0) {
					numberOfCategoryImprovements = numberOfCategoryImprovements + a;
				}

				int categoryBefore = (int)Math.floor(v2/10.0);
				int categoryAfter = (int)Math.floor(v1/10.0);
				categorySizeChanges[categoryBefore]--;
				categorySizeChanges[categoryAfter]++;
				///////////////// category statistics ////////////////
			}
			else {
				System.out.print("- % | ");
				numberOfNewCardsTested++;

				int category;
				if (percentageOfRightAnswersAfterTest == 100.0) {
					category = 9;
				}
				else {
					category = (int)Math.floor(percentageOfRightAnswersAfterTest/10.0);
				}
				categorySizeChanges[category]++;
			}

			System.out.println(answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(cardIndex).numberOfAnswers());
		}

		System.out.println("------------------------------------------------------------------");;
		System.out.println("number of cards whose answer rate improves: " + numberOfCardsWithImprovement);
		System.out.println("number of cards whose answer rate does not change: " + numberOfCardsWithNoChange);
		System.out.println("number of cards whose answer rate reduces: " + numberOfCardsWithReducement);
		System.out.println("number of new cards tested: " + numberOfNewCardsTested);
		System.out.println("number of category improvements: " + numberOfCategoryImprovements);
		System.out.println("number of category reducements: " + numberOfCategoryReducements);
		System.out.print("category size changes: ");
		for (int i=0; i<9; i++) {
			System.out.print((i) + ":" + categorySizeChanges[i] + ", ");
		}
		System.out.println("9:" + categorySizeChanges[9]);
		System.out.println("number of answers: " + testAnswers.numberOfAnswers());
		System.out.println("percentage of right answers: " + df.format(testAnswers.percentageOfRightAnswers()) + "%");
		System.out.println("average answer rate of cards before test: "
			+ df.format(answerDatasByStudyItemsBeforeTest.getAverageAnswerRateOfStudyItems() * 100.0) + "%");
		System.out.println("average answer rate of cards after test: "
			+ df.format(answerDatasByStudyItemsAfterTest.getAverageAnswerRateOfStudyItems() * 100.0) + "%");

		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		System.out.println("used time: " + dateFormatted);
	}

}
