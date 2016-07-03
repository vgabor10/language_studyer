package experimental_classes;

import study_item_objects.AnswerDataContainer;
import study_item_objects.AnswerDataByStudyItemsContainer;
import common.Logger;
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
        
        public double aggragatedReducements() {
            double aggragatedReducements = 0;
            for (int i=0; i<testedCards.numberOfCards(); i++) {
                Card card = testedCards.getCardByOrder(i);
                if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                    double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                    double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                    if (d1>d2) {
                        aggragatedReducements = aggragatedReducements + d1 - d2;
                    }
                }
            }
            return aggragatedReducements;
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
            return aggragatedImprovements;
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
