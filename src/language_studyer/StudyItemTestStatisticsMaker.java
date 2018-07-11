package language_studyer;

import common.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Set;

public class StudyItemTestStatisticsMaker {

    private StudyItemContainer allStudyItems;
    private AnswerDataContainer testAnswers;
    
    private AnswerDataContainer relevantOldAnswers = new AnswerDataContainer();
    private AnswerDataContainer relevantTestAnswers = new AnswerDataContainer();
    private StudyItemContainer relevantTestedStudiItems = new StudyItemContainer();

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
        
        this.testAnswers = testAnswers;
        this.categoryRestrictions = dataContainer.getStudyStrategy().cardCategoryRestrictions;
        this.allStudyItems = dataContainer.getStudyItemContainer();
        
        if (!categoryRestrictions.contains(-1)) {
            categoryRestrictionUsage = true;
        }
        else {
            categoryRestrictionUsage = false;
        }

        AnswerDataContainer allOldAnswers = dataContainer.getAnswerDataContainer();
        
        if (categoryRestrictionUsage) {
            for (int i = 0; i < allOldAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = allOldAnswers.getAnswerData(i);
                StudyItem studyItem = allStudyItems.getStudyItemByIndex(answerData.index);
                if (studyItem.containsAnyCategoryIndex(categoryRestrictions)) {
                    relevantOldAnswers.addAnswerData(answerData);
                }
            }
            
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = testAnswers.getAnswerData(i);
                StudyItem studyItem = allStudyItems.getStudyItemByIndex(answerData.index);
                
                if (studyItem.containsAnyCategoryIndex(categoryRestrictions)) {
                    relevantTestAnswers.addAnswerData(answerData);
                    relevantTestedStudiItems.addStudyItem(studyItem);
                }
            }
        }
        else {
            this.relevantOldAnswers = allOldAnswers;
            this.relevantTestAnswers = testAnswers;
            for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                AnswerData answerData = testAnswers.getAnswerData(i);
                StudyItem studyItem = allStudyItems.getStudyItemByIndex(answerData.index);
                relevantTestedStudiItems.addStudyItem(studyItem);
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
    
    public double getAnswerRateOfStudyItemBeforeTest(int cardIndex) {
        if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(cardIndex)) {
            return relevantAnswerDatasByStudyItemsBeforeTest.
                    getAnswerDataByStudyItemByIndex(cardIndex).countRightAnswerRate();
        }
        else {
            return -1;
        }
    }
    
    public double getAnswerRateOfStudyItemAfterTest(int cardIndex) {
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
    
    public StudyItemContainer getTestedStudyItems() {
        StudyItemContainer out = new StudyItemContainer();
        
        for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
            AnswerData answerData = testAnswers.getAnswerData(i);
            StudyItem studyItem = allStudyItems.getStudyItemByIndex(answerData.index);
            out.addStudyItem(studyItem);
        } 
        
        return out;
    }

    public String averageAnswerRateOfStudyItemsBeforeTestAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(relevantAnswerDatasByStudyItemsBeforeTest.getAverageAnswerRateOfStudyItems());
    }

    public String averageAnswerRateOfStudyItemsAfterTestAsString() {
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
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem card = relevantTestedStudiItems.getStudyItemByOrder(i);
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
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem card = relevantTestedStudiItems.getStudyItemByOrder(i);
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
        int numberOfNewStudyItemsTested = 0;
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem studyItem = relevantTestedStudiItems.getStudyItemByOrder(i);
            if (!relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(studyItem.index)) {
                numberOfNewStudyItemsTested++;
            }
        }
        return numberOfNewStudyItemsTested;
    }

    public int numberOfStudyItemsWithArReducement() {
        int numberOfStudyItemsWithArReducement = 0;
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem card = relevantTestedStudiItems.getStudyItemByOrder(i);
            if (relevantAnswerDatasByStudyItemsBeforeTest.containsStudyItemWithIndex(card.index)) {
                double d1 = relevantAnswerDatasByStudyItemsBeforeTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();
                double d2 = relevantAnswerDatasByStudyItemsAfterTest.getAnswerDataByStudyItemByIndex(card.index).countRightAnswerRate();

                if (d1 > d2) {
                    numberOfStudyItemsWithArReducement++;
                }
            }
        }
        return numberOfStudyItemsWithArReducement;
    }

    public int numberOfStudyItemsWithArImprovement() {
        int numberOfCardsWithImprovement = 0;
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem card = relevantTestedStudiItems.getStudyItemByOrder(i);
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

    public int numberOfStudyItemsWithNoArChange() {
        int numberOfCardsWithNoArChange = 0;
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem card = relevantTestedStudiItems.getStudyItemByOrder(i);
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
        for (int i = 0; i < relevantTestedStudiItems.numberOfStudyItems(); i++) {
            StudyItem card = relevantTestedStudiItems.getStudyItemByOrder(i);
            
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
