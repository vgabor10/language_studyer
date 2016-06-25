package experimental_classes;

import common.*;
import dictionary.*;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CardTestStatisticsMaker2 {

	private CardContainer cardContainer;    //TODO: rename: testedCards
	private AnswerDataContainer testAnswers;
        private AnswerDataContainer oldAnswers;

	/////////////// STATISTICS ///////////

	public int numberOfCardsWithImprovement = 0;
	public int numberOfCardsWithNoChange = 0;
	public int numberOfCardsWithReducement = 0;
	public int numberOfNewCardsTested = 0;
	public int numberOfCategoryImprovements = 0;
	public int numberOfCategoryReducements = 0;
	public int[] categorySizeChanges = new int[10];
	public long startTime;
	public long endTime;

	/////////////// STATISTICS ///////////

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setTestAnswers(AnswerDataContainer ta) {
		testAnswers = ta;
	}
        
        public void setOldAnswers(AnswerDataContainer oa) {
		oldAnswers = oa;
	}

	public void startMeasureTime() {
		Date date = new Date();
		startTime = date.getTime();
	}

	public void endMeasureTime() {
		Date date = new Date();
		endTime = date.getTime();
	}

	public void evaluateStatistics() {

                Set<Integer> cardIndexes = new HashSet<Integer>();
		for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
			cardIndexes.add(testAnswers.getAnswerData(i).index);
		}

                AnswerDataByStudyItemsContainer answerDatasByStudyItemsBeforeTest
                        = new AnswerDataByStudyItemsContainer();
                AnswerDataByStudyItemsContainer answerDatasByStudyItemsAfterTest
                        = new AnswerDataByStudyItemsContainer();
                 
                answerDatasByStudyItemsBeforeTest.loadDataFromAnswerDataContainer(oldAnswers);
                
                AnswerDataContainer actualAnswers = new AnswerDataContainer();
                actualAnswers.appendAnswerDataContainer(oldAnswers);
                actualAnswers.appendAnswerDataContainer(testAnswers);
                answerDatasByStudyItemsAfterTest.loadDataFromAnswerDataContainer(actualAnswers);
                
		for (int cardIndex : cardIndexes) {

			double percentageOfRightAnswersBeforeTest = -1;
			if (answerDatasByStudyItemsBeforeTest.getTestedStudyItemIndexes().contains(cardIndex)) {
				percentageOfRightAnswersBeforeTest 
					= answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate() * 100.0;
			}

			double percentageOfRightAnswersAfterTest
				= answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate() * 100.0;		

			if (percentageOfRightAnswersBeforeTest != -1) {

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

				double v1;
                                double v2;
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

		}

		/*System.out.println("percentage of right answers: " + df.format(testAnswers.percentageOfRightAnswers()) + "%");
		System.out.println("average answer rate of cards before test: "
			+ df.format(answerDatasByStudyItemsBeforeTest.getAverageAnswerRateOfStudyItems() * 100.0) + "%");
		System.out.println("average answer rate of cards after test: "
			+ df.format(answerDatasByStudyItemsAfterTest.getAverageAnswerRateOfStudyItems() * 100.0) + "%");
       */ }

        public String percentageOfRightAnswersAsString() {
                DecimalFormat df = new DecimalFormat("#.000");
		return df.format(testAnswers.percentageOfRightAnswers()) + "%";
	}   
        
        public String numberOfUserAnswersAsString() {
		return Integer.toString(testAnswers.numberOfAnswers());
	}   
        
        public String categorySizeChangesAsString() {
            String s = "";
            for (int i=0; i<9; i++) {
			s = s + ":" + categorySizeChanges[i] + ", ";
		}
		return s + "9:" + categorySizeChanges[9];
	}           
        
        public String numberOfCategoryReducementsAsString() {
		return Integer.toString(numberOfCategoryReducements);
	}   
        
        public String numberOfCategoryImprovementsAsString() {
		return Integer.toString(numberOfCategoryImprovements);
	}        
        
        public String numberOfNewCardsTestedAsString() {
		return Integer.toString(numberOfNewCardsTested);
	}
        
        public String numberOfCardsWithReducementAsString() {
		return Integer.toString(numberOfCardsWithReducement);
	}
        
        public String numberOfCardsWithImprovementAsString() {
		return Integer.toString(numberOfCardsWithImprovement);
	}
        
        public String numberOfCardsWithNoChangeAsString() {
		return Integer.toString(numberOfCardsWithNoChange);
	}
        
        public String getUsedTimeAsString() {
		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		return dateFormatted;
	}

}
