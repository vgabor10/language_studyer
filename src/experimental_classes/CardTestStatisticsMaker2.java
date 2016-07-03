package experimental_classes;

import common.*;
import dictionary.*;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CardTestStatisticsMaker2 {

	private CardContainer testedCards;
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
        public AnswerDataByStudyItemsContainer answerDatasByStudyItemsBeforeTest 
                = new AnswerDataByStudyItemsContainer();
	public AnswerDataByStudyItemsContainer answerDatasByStudyItemsAfterTest
                 = new AnswerDataByStudyItemsContainer();

	/////////////// STATISTICS ///////////

        private Logger logger = new Logger();
        
	public void setTestedCards(CardContainer tc) {
		testedCards = tc;
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

        public CardContainer getTestedCards() {
            return testedCards;
        }
        
        public void fillAnserDataByStudyItemContainers() {
            answerDatasByStudyItemsBeforeTest.loadDataFromAnswerDataContainer(oldAnswers);
                
            for (int i=0; i<oldAnswers.numberOfAnswers(); i++) {
                answerDatasByStudyItemsAfterTest.addAnswerData(oldAnswers.getAnswerData(i));
            }
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                answerDatasByStudyItemsAfterTest.addAnswerData(testAnswers.getAnswerData(i));
            }
        }
        
	/*public void evaluateStatistics() {

                Set<Integer> cardIndexes = new HashSet<Integer>();
		for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
			cardIndexes.add(testAnswers.getAnswerData(i).index);
		}
               
                
 
                
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
        }*/

        public Double getAfterTestArOfCardWithIndex(int index) {
            return answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();
        }
        
        public String averageAnswerRateOfCardsBeforeTestAsString() {
                DecimalFormat df = new DecimalFormat("#.0000");
		return df.format(answerDatasByStudyItemsBeforeTest.getAverageAnswerRateOfStudyItems());
	}   
        
        public String averageAnswerRateOfCardsAfterTestAsString() {
                DecimalFormat df = new DecimalFormat("#.0000");
		return df.format(answerDatasByStudyItemsAfterTest.getAverageAnswerRateOfStudyItems());
	}   
        
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
        
        public double aggragatedReducements() {
            double aggragatedImprovements = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
                if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                    double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                    if (d1>d2) {
                        aggragatedImprovements = aggragatedImprovements + d1 - d2;
                    }
                }
            }
            return numberOfCategoryImprovements;
	}  
        
        public double aggragatedImprovements() {
            double aggragatedImprovements = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
                if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                    double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                    if (d1<d2) {
                        aggragatedImprovements = aggragatedImprovements + d2 - d1;
                    }
                }
            }
            return numberOfCategoryImprovements;
	}        
        
        public int numberOfNewCardsTested() {
            int numberOfNewCardsTested = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
		if (!answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    numberOfNewCardsTested++;
                }
            }
            return numberOfNewCardsTested;
	}
        
        public int numberOfCardsWithArReducement() {
            int numberOfCardsWithArReducement = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
                if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                    double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                    if (d1>d2) {
                        numberOfCardsWithArReducement++;
                    }
                }
            }
            return numberOfCardsWithArReducement;
	}
        
        public int numberOfCardsWithArImprovement() {
            int numberOfCardsWithImprovement = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
                if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                    double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                    if (d1<d2) {
                        numberOfCardsWithImprovement++;
                    }
                }
            }
            return numberOfCardsWithImprovement;
	}
        
        public int numberOfCardsWithNoArChange() {
            int numberOfCardsWithNoArChange = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
                if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                    double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                    if (d1==d2) {
                        numberOfCardsWithNoArChange++;
                    }
                }
            }
            return numberOfCardsWithNoArChange;
	}
        
        public String getUsedTimeAsString() {
		Date date = new Date(endTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		return dateFormatted;
	}

}
