package dictionary;

import study_item_objects.AnswerDataContainer;
import study_item_objects.AnswerDataByStudyItemContainer;
import common.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Set;
import study_item_objects.AnswerData;

public class CardTestStatisticsMaker {

    private DictionaryDataContainer dictionaryDataContainer;
    private AnswerDataContainer testAnswers;
    
    private AnswerDataContainer relevantTestAnswers;
    private CardContainer relevantTestedCards;

    private long startTime;
    private long finishTime;

    private AnswerDataByStudyItemContainer answerDatasByStudyItemsBeforeTest
            = new AnswerDataByStudyItemContainer();
    private AnswerDataByStudyItemContainer answerDatasByStudyItemsAfterTest
            = new AnswerDataByStudyItemContainer();

    private boolean categoryRestrictionUsage = false;
    private Set<Integer> categoryRestrictions;
    
    private Logger logger = new Logger();
    
    public void setData(DictionaryDataContainer dictionaryDataContainer,
            AnswerDataContainer testAnswers) {
        this.dictionaryDataContainer = dictionaryDataContainer;
        this.testAnswers = testAnswers;
    }
    
    public void setCategoryRestrictions(Set<Integer> cr) {
        categoryRestrictions = cr;
        categoryRestrictionUsage = true;
    }

    public void initialise() {
        CardContainer allCards = dictionaryDataContainer.cardContainer;
        AnswerDataContainer allOldAnswers = dictionaryDataContainer.answerDataContainer;
        
        if (categoryRestrictionUsage) {
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = testAnswers.getAnswerData(i);
                Card card = allCards.getCardByIndex(answerData.index);
                
                if (card.containsAnyCategoryIndex(categoryRestrictions)) {
                    relevantTestAnswers.addAnswerData(answerData);
                    relevantTestedCards.addCard(
                        dictionaryDataContainer.cardContainer.getCardByIndex(answerData.index));
                }
            }
        }
        else {
            this.relevantTestAnswers = testAnswers;
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = testAnswers.getAnswerData(i);
                Card card = allCards.getCardByIndex(answerData.index);
                relevantTestedCards.addCard(
                    dictionaryDataContainer.cardContainer.getCardByIndex(answerData.index));
            }
        }
        
        Set<Integer> relevantTestedCardIndexes = relevantTestedCards.getCardIndexes();
            
        for (int i = 0; i < allOldAnswers.numberOfAnswers(); i++) {
            AnswerData answerData = allOldAnswers.getAnswerData(i);
            if (relevantTestedCardIndexes.contains(answerData.index)) {
                answerDatasByStudyItemsBeforeTest.addAnswerData(answerData);
                answerDatasByStudyItemsAfterTest.addAnswerData(answerData);
            }
        }
        
        for (int i = 0; i < relevantTestAnswers.numberOfAnswers(); i++) {
            answerDatasByStudyItemsAfterTest.addAnswerData(relevantTestAnswers.getAnswerData(i));
        }
    }

    public void setStartAndFinishTime(long st, long ft) {
        startTime = st;
        finishTime = ft;
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
        if (relevantTestAnswers.numberOfAnswers() != 0) {
            int sum = 0;
            for (int i = 0; i < relevantTestAnswers.numberOfAnswers(); i++) {
                if (relevantTestAnswers.getAnswerData(i).isRight) {
                    sum++;
                }
            }
            double percentage = (double) sum / (double) relevantTestAnswers.numberOfAnswers() * 100.0;

            DecimalFormat df = new DecimalFormat("#.000");
            return df.format(percentage) + "%";
        } else {
            return "-";
        }
    }

    public String numberOfUserAnswersAsString() {
        return Integer.toString(relevantTestAnswers.numberOfAnswers());
    }

    public String aggragatedReducementsAsString() {
        double aggragatedReducements = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                if (d1 > d2) {
                    aggragatedReducements = aggragatedReducements + d1 - d2;
                }
            }
        }

        DecimalFormat df = new DecimalFormat("#.000");
        return df.format(aggragatedReducements);
    }

    public String aggragatedImprovementsAsString() {
        double aggragatedImprovements = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                if (d1 < d2) {
                    aggragatedImprovements = aggragatedImprovements + d2 - d1;
                }
            }
        }

        DecimalFormat df = new DecimalFormat("#.000");
        return df.format(aggragatedImprovements);
    }

    public int numberOfNewCardsTested() {
        int numberOfNewCardsTested = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (!answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                numberOfNewCardsTested++;
            }
        }
        return numberOfNewCardsTested;
    }

    public int numberOfCardsWithArReducement() {
        int numberOfCardsWithArReducement = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                if (d1 > d2) {
                    numberOfCardsWithArReducement++;
                }
            }
        }
        return numberOfCardsWithArReducement;
    }

    public int numberOfCardsWithArImprovement() {
        int numberOfCardsWithImprovement = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                if (d1 < d2) {
                    numberOfCardsWithImprovement++;
                }
            }
        }
        return numberOfCardsWithImprovement;
    }

    public int numberOfCardsWithNoArChange() {
        int numberOfCardsWithNoArChange = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                if (d1 == d2) {
                    numberOfCardsWithNoArChange++;
                }
            }
        }
        return numberOfCardsWithNoArChange;
    }
    
    public double getAggregatedUserPointsChange() {
        double aggregatedUserPoints = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            
            double rightAnswerRateAfterTest = 
                    answerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
            
            if (answerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double rightAnswerRateBeforeTest = 
                        answerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                
                aggregatedUserPoints = aggregatedUserPoints + rightAnswerRateAfterTest - rightAnswerRateBeforeTest; 
            }
            else {
                aggregatedUserPoints = aggregatedUserPoints + rightAnswerRateAfterTest;
            }
        }
        return aggregatedUserPoints;        
    }
    
    public String getAggregatedUserPointsChangeAsString() {
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format(getAggregatedUserPointsChange());
    }

    public String getUsedTimeAsString() {
        Date date = new Date(finishTime - startTime);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

}
