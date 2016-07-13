package dictionary;

import study_item_objects.AnswerDataContainer;
import study_item_objects.AnswerDataByStudyItemsContainer;
import common.Logger;
import dictionary.*;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CardTestStatisticsMaker {

	public CardContainer testedCards;
        private CardContainer allCard;
	private AnswerDataContainer testAnswers;
        private AnswerDataContainer oldAnswers;

	public long startTime;
	public long finishTime;
        
        public AnswerDataByStudyItemsContainer answerDatasByStudyItemsBeforeTest 
                = new AnswerDataByStudyItemsContainer();
	public AnswerDataByStudyItemsContainer answerDatasByStudyItemsAfterTest
                 = new AnswerDataByStudyItemsContainer();

        private Logger logger = new Logger();
        
	public void setAllCard(CardContainer ac) {
		allCard = ac;
	}

	public void setTestAnswers(AnswerDataContainer ta) {
		testAnswers = ta;
	}
        
        public void setOldAnswers(AnswerDataContainer oa) {
		oldAnswers = oa;
	}
        
        public void setStartAndFinishTime(long st, long ft) {
		startTime = st;
                finishTime = ft;
	}

        public void evaluateTestedCards() {
            testedCards = new CardContainer();
            
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                testedCards.addCard(allCard.getCardByIndex(testAnswers.getAnswerData(i).index));
            }
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
        
        public String aggragatedReducementsAsString() {
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
            
            DecimalFormat df = new DecimalFormat("#.000");
            return df.format(aggragatedReducements);
	}  
        
        public String aggragatedImprovementsAsString() {
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
            
            DecimalFormat df = new DecimalFormat("#.000");
            return df.format(aggragatedImprovements);
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
		Date date = new Date(finishTime - startTime);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateFormatted = formatter.format(date);
		return dateFormatted;
	}

}
