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

    private DataContainer dataContainer;
    private AnswerDataContainer testAnswers;
    
    private AnswerDataContainer relevantOldAnswers = new AnswerDataContainer();
    private AnswerDataContainer relevantTestAnswers = new AnswerDataContainer();
    private CardContainer relevantTestedCards = new CardContainer();

    private long startTime;
    private long finishTime;

    private AnswerDataByStudyItemContainer relevantAnswerDatasByStudyItemsBeforeTest
            = new AnswerDataByStudyItemContainer();
    private AnswerDataByStudyItemContainer relevantAnswerDatasByStudyItemsAfterTest
            = new AnswerDataByStudyItemContainer();

    private boolean categoryRestrictionUsage = false;
    private Set<Integer> categoryRestrictions;
    
    private Logger logger = new Logger();
    
    public void setData(DataContainer dataContainer,
            AnswerDataContainer testAnswers) {
        this.dataContainer = dataContainer;
        this.testAnswers = testAnswers;
        this.categoryRestrictions = dataContainer.studyStrategy.cardCategoryRestrictions;
        
        if (!categoryRestrictions.contains(-1)) {
            categoryRestrictionUsage = true;
        }
        else {
            categoryRestrictionUsage = false;
        }
        
        CardContainer allCards = dataContainer.cardContainer;
        AnswerDataContainer allOldAnswers = dataContainer.answerDataContainer;
        
        if (categoryRestrictionUsage) {
            for (int i = 0; i < allOldAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = allOldAnswers.getAnswerData(i);
                Card card = allCards.getCardByIndex(answerData.index);
                if (card.containsAnyCategoryIndex(categoryRestrictions)) {
                    relevantOldAnswers.addAnswerData(answerData);
                }
            }
            
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = testAnswers.getAnswerData(i);
                Card card = allCards.getCardByIndex(answerData.index);
                
                if (card.containsAnyCategoryIndex(categoryRestrictions)) {
                    relevantTestAnswers.addAnswerData(answerData);
                    relevantTestedCards.addCard(card);
                }
            }
        }
        else {
            this.relevantOldAnswers = allOldAnswers;
            this.relevantTestAnswers = testAnswers;
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = testAnswers.getAnswerData(i);
                Card card = allCards.getCardByIndex(answerData.index);
                relevantTestedCards.addCard(card);
            }
        }

        for (int i=0;i<relevantOldAnswers.numberOfAnswers();i++) {
            AnswerData answerData = relevantOldAnswers.getAnswerData(i);
            relevantAnswerDatasByStudyItemsBeforeTest.addAnswerData(answerData);
            relevantAnswerDatasByStudyItemsAfterTest.addAnswerData(answerData);
        }

        for (int i=0;i<relevantTestAnswers.numberOfAnswers();i++) {
            AnswerData answerData = relevantTestAnswers.getAnswerData(i);
            relevantAnswerDatasByStudyItemsAfterTest.addAnswerData(answerData);
        }     
    }

    public boolean isCategoryConstrainsUsed() {
        return categoryRestrictionUsage;
    } 
    
    public void setStartAndFinishTime(long st, long ft) {
        startTime = st;
        finishTime = ft;
    }
    
    public double getAnswerRateOfCardBeforeTest(int cardIndex) {
        if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(cardIndex)) {
            return relevantAnswerDatasByStudyItemsBeforeTest.
                    getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate();
        }
        else {
            return -1;
        }
    }
    
    public double getAnswerRateOfCardAfterTest(int cardIndex) {
        if (relevantAnswerDatasByStudyItemsAfterTest.containsStudyItemWithIndex(cardIndex)) {
            return relevantAnswerDatasByStudyItemsAfterTest.
                    getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate();
        }
        else {
            return -1;
        }
    }
    
    public int getAfterTestNumberOfAnswers(int cardIndex) {
        if (relevantAnswerDatasByStudyItemsAfterTest.containsStudyItemWithIndex(cardIndex)) {
            return relevantAnswerDatasByStudyItemsAfterTest.
                    getAnswerDataByStudyItemByIndex(cardIndex).numberOfAnswers();
        }
        else {
            return 0;
        }
    }
    
    public CardContainer getTestedCards() {
        CardContainer out = new CardContainer();
        
        for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
            AnswerData answerData = testAnswers.getAnswerData(i);
            Card card = dataContainer.cardContainer.getCardByIndex(answerData.index);
            out.addCard(card);
        } 
        
        return out;
    }

    public String averageAnswerRateOfCardsBeforeTestAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(relevantAnswerDatasByStudyItemsBeforeTest.getAverageAnswerRateOfStudyItems());
    }

    public String averageAnswerRateOfCardsAfterTestAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(relevantAnswerDatasByStudyItemsAfterTest.getAverageAnswerRateOfStudyItems());
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
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

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
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

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
            if (!relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                numberOfNewCardsTested++;
            }
        }
        return numberOfNewCardsTested;
    }

    public int numberOfCardsWithArReducement() {
        int numberOfCardsWithArReducement = 0;
        for (int i = 0; i < relevantTestedCards.numberOfCards(); i++) {
            Card card = relevantTestedCards.getCardByOrder(i);
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

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
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

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
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

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
                    relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
            
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double rightAnswerRateBeforeTest = 
                        relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                
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
