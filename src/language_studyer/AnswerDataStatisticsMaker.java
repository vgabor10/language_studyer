package language_studyer;

import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByLastStudyDate;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByNumberOfAnswers;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByRateOfRightAnswers;
import common.Logger;
import common.GeneralFunctions;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class AnswerDataStatisticsMaker {

    protected AnswerDataContainer answerDataContainer;
    protected StudyItemContainer studyItemContainer;

    public void setAnswerDataContainer(AnswerDataContainer ac) {
        answerDataContainer = ac;
    }

    public void setStudyItemContainer(StudyItemContainer sc) {
        studyItemContainer = sc;
    }

    public int numberOfAnswersAtDay(int day) {
        int numberOfAnswers = 0;
        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            if (generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date) == day) {
                numberOfAnswers++;
            }
        }
        return numberOfAnswers;
    }

    public int numberOfRightAnswersAtDay(int day) {
        int numberOfAnswers = 0;
        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            if (answerDataContainer.getAnswerData(i).isRight
                    && generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date) == day) {
                numberOfAnswers++;
            }
        }
        return numberOfAnswers;
    }

    public double percentageOfRightAnswersAtDay(int day) {
        if (numberOfAnswersAtDay(day) != 0) {
            return (double) numberOfRightAnswersAtDay(day) * 100.0 / (double) numberOfAnswersAtDay(day);
        } else {
            return 0.0;
        }
    }

    public double percentageOfRightAnswers() {
        if (answerDataContainer.numberOfAnswers() != 0) {
            int sum = 0;
            for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
                if (answerDataContainer.getAnswerData(i).isRight) {
                    sum++;
                }
            }
            return (double) sum / (double) answerDataContainer.numberOfAnswers() * 100.0;
        } else {
            return -1;
        }
    }

    public String getPercentageOfRightAnswersAsString() {
        double p = percentageOfRightAnswers();
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(p) + "%";
    }

    public int numberOfStudyItemsQuestioned() {
        Set<Integer> StudyItemInexes = new HashSet<>();
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            StudyItemInexes.add(answerDataContainer.getAnswerData(i).index);
        }

        return StudyItemInexes.size();
    }

    //supposed, that dates are sorted
    public long evaluatePractisingTime(List<Long> answerDates) {
        if (answerDates.isEmpty()) {
            return 0;
        }
            
        long practisingTime = 0;
        long intervalStartTime = answerDates.get(0);

        for (int i = 1; i < answerDates.size(); i++) {
            if (answerDates.get(i) - answerDates.get(i - 1) > 30000) {
                practisingTime = practisingTime + (answerDates.get(i - 1) - intervalStartTime) + 5000;
                intervalStartTime = answerDates.get(i);
            }
        }

        practisingTime = practisingTime + (answerDates.get(answerDates.size()-1) - intervalStartTime) + 5000;

        return practisingTime;
    }

    public int[] practisingTime() {
        List<Long> answerDates = new ArrayList<>();
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            answerDates.add(answerDataContainer.getAnswerData(i).date);
        }

        long practisingTime = evaluatePractisingTime(answerDates);
        int hours = (int) practisingTime / 3600000;
        int minutes = (int) ((practisingTime - 3600000 * hours) / 60000);

        int[] out = {hours, minutes};
        return out;
    }

    public String getPractisingTimeAsString() {
        int[] a = practisingTime();
        return Integer.toString(a[0]) + " hours " + Integer.toString(a[1]) + " minutes";
    }

    public Map<Integer, String> getPractisingTimeByDaysAsString() {
        if (answerDataContainer.numberOfAnswers() != 0) {
            Map<Integer, String> out = new HashMap<>();

            List<Long> answerDates = new ArrayList<>();

            GeneralFunctions generalFunctions = new GeneralFunctions();
            int actualDay = generalFunctions.milisecToDay(answerDataContainer.getAnswerData(0).date);

            for (int i = 1; i < answerDataContainer.numberOfAnswers(); i++) {
                int newDay = generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date);
                if (actualDay == newDay) {
                    answerDates.add(answerDataContainer.getAnswerData(i).date);
                } else {
                    long practisingTime = evaluatePractisingTime(answerDates);
                    int hours = (int) practisingTime / 3600000;
                    int minutes = (int) ((practisingTime - 3600000 * hours) / 60000);

                    out.put(actualDay, Integer.toString(hours) + " hours " + Integer.toString(minutes) + " minutes");

                    actualDay = newDay;
                    answerDates.clear();
                    answerDates.add(answerDataContainer.getAnswerData(i).date);
                }
            }

            long practisingTime = evaluatePractisingTime(answerDates);
            int hours = (int) practisingTime / 3600000;
            int minutes = (int) ((practisingTime - 3600000 * hours) / 60000);
            out.put(actualDay, Integer.toString(hours) + " hours " + Integer.toString(minutes) + " minutes");

            return out;
        } else {
            return null;
        }
    }

    public int numberOfQuestionsOfLeastStudiedStudyItem() {
        int numberOfQuestionsOfLeastStudiedStudyItem;

        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

        if (answerDataByStudyItemsContainer.numberOfStudiedStudyItems() 
                == studyItemContainer.numberOfStudyItems()) {

            Set<Integer> testedStudyItemIndexes = answerDataByStudyItemsContainer.getTestedStudyItemIndexes();
            numberOfQuestionsOfLeastStudiedStudyItem = Integer.MAX_VALUE;
            for (int index : testedStudyItemIndexes) {
                int numberOfAnswers = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).numberOfAnswers();
                if (numberOfAnswers < numberOfQuestionsOfLeastStudiedStudyItem) {
                    numberOfQuestionsOfLeastStudiedStudyItem = numberOfAnswers;
                }
            }

            return numberOfQuestionsOfLeastStudiedStudyItem;
        }
        else {
            return 0;
        }
    }

    public HashMap<Integer, Integer> getHistogramOfStudyItemsByNumberOfAnswers() {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

        HashMap<Integer, Integer> histogramOfStudyItemsByNumberOfAnswers = new HashMap<>();

        Set<Integer> testedStudyItemIndexes = answerDataByStudyItemsContainer.getTestedStudyItemIndexes();
        for (int index : testedStudyItemIndexes) {
            int numberOfAnswers = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).numberOfAnswers();
            if (histogramOfStudyItemsByNumberOfAnswers.containsKey(numberOfAnswers)) {
                int numberOfStudyItems = histogramOfStudyItemsByNumberOfAnswers.get(numberOfAnswers) + 1;
                histogramOfStudyItemsByNumberOfAnswers.remove(numberOfAnswers);
                histogramOfStudyItemsByNumberOfAnswers.put(numberOfAnswers, numberOfStudyItems);
            } else {
                histogramOfStudyItemsByNumberOfAnswers.put(numberOfAnswers, 1);
            }
        }

        return histogramOfStudyItemsByNumberOfAnswers;
    }

    public double getProbabilityOfRightAnswerAtXthAnswer(int x) {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

        int numberOfAnswers = 0;
        int numberOfRightAnswers = 0;

        Set<Integer> testedStudyItemIndexes = answerDataByStudyItemsContainer.getTestedStudyItemIndexes();

        for (int i : testedStudyItemIndexes) {
            if (x < answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(i).numberOfAnswers()) {
                if (answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(i).getAnswer(x).isRight) {
                    numberOfRightAnswers++;
                }
                numberOfAnswers++;
            }
        }

        if (numberOfAnswers == 0) {
            return -1;
        } else {
            return (double) numberOfRightAnswers / (double) numberOfAnswers;
        }
    }

    public int numberOfStudyItems() {
        return studyItemContainer.numberOfStudyItems();
    }

    public int numberOfAnswers() {
        return answerDataContainer.numberOfAnswers();
    }

    public int getNumberOfQuestionedStudyItems() {
        Set<Integer> studyItemIndexes = new HashSet<>();
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            studyItemIndexes.add(answerDataContainer.getAnswerData(i).index);
        }
        return studyItemIndexes.size();
    }

    public long getLastQuestionedStudyItemDate() {
        if (answerDataContainer.numberOfAnswers() == 0) {
             return 0;
        }
        
        Set<Integer> StudyItemIndexes = new HashSet<>();
        int i = answerDataContainer.numberOfAnswers() - 1;
        int numberOfQuestionedStudyItems = getNumberOfQuestionedStudyItems();
        while ((int) StudyItemIndexes.size() < numberOfQuestionedStudyItems) {
            StudyItemIndexes.add(answerDataContainer.getAnswerData(i).index);
            i--;
        }

        return answerDataContainer.getAnswerData(i + 1).date;
    }

    public int getNumberOfStudyingDays() {
        Set<Integer> studyingDays = new HashSet<>();
        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            studyingDays.add(generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date));
        }

        return studyingDays.size();
    }

    public Map<Integer, Integer> getNumberOfAnswersByDays() {
        Map<Integer, Integer> out = new HashMap<>();

        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            int answerDataDay
                    = generalFunctions.milisecToDay(answerDataContainer.getAnswerData(i).date);

            if (out.keySet().contains(answerDataDay)) {
                int numberOfAnswersAtDay = out.get(answerDataDay);
                out.remove(answerDataDay);
                numberOfAnswersAtDay++;
                out.put(answerDataDay, numberOfAnswersAtDay);
            } else {
                out.put(answerDataDay, 1);
            }
        }

        return out;
    }

    public Vector<Integer> getNumberOfAnswersGivenLastDays(int numberOfDays) {

        Vector<Integer> out = new Vector<>();
        Map<Integer, Integer> numberOfAnswersByDays = getNumberOfAnswersByDays();

        Date date = new Date();
        GeneralFunctions generalFunctions = new GeneralFunctions();
        int today = generalFunctions.milisecToDay(date.getTime());

        for (int i = 0; i < numberOfDays; i++) {
            if (numberOfAnswersByDays.keySet().contains(today - i)) {
                out.add(numberOfAnswersByDays.get(today - i));
            } else {
                out.add(0);
            }
        }

        return out;
    }

    public Map<Integer, Integer> numberOfIndividualStudyItemsQuestionedByDays() {
        Map<Integer, Set<Integer>> data = new HashMap<>();

        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);

            int answerDataDay
                    = generalFunctions.milisecToDay(answerData.date);

            if (data.keySet().contains(answerDataDay)) {
                data.get(answerDataDay).add(answerData.index);
            } else {
                Set<Integer> set = new HashSet<>();
                set.add(answerData.index);
                data.put(answerDataDay, set);
            }
        }

        Map<Integer, Integer> out = new HashMap<>();
        for (int day : data.keySet()) {
            out.put(day, data.get(day).size());
        }

        return out;
    }

    public int[] numberOfGivenAnswersByHours() {
        int[] numberOfGivenAnswersByHours = new int[24];
        GeneralFunctions generalFunctions = new GeneralFunctions();

        //Date date = new Date();
        //System.out.println(generalFunctionsmilisecToHour(date.getTime() + 3600*1000));	//for test: related to time zone
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            int hour = generalFunctions.milisecToHour(answerDataContainer.getAnswerData(i).date);
            numberOfGivenAnswersByHours[hour]++;
        }

        return numberOfGivenAnswersByHours;
    }

    public int[] getHistogramOfStudyItemsByAnswerRate() {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

        int[] numberOfStudyItemsInCategory = new int[10];

        for (int index : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            double rateOfRightAnswers = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();

            if (rateOfRightAnswers == 1.0) {
                numberOfStudyItemsInCategory[9]++;
            } else {
                numberOfStudyItemsInCategory[(int) Math.floor(10.0 * rateOfRightAnswers)]++;
            }
        }

        return numberOfStudyItemsInCategory;
    }

    public Map<Integer, Histogram> getHistogramOfStudyItemAnswerRatesByNumberOfAnswers(
            int numberOfAnswers) {
        
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer
                = new AnswerDataByStudyItemContainer();

        Map<Integer, Histogram> out = new HashMap<>();

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);
            
            if (i % numberOfAnswers == 0) {
                out.put(i, answerDataByStudyItemsContainer.getHistogram());
            }

            answerDataByStudyItemsContainer.addAnswerData(answerData);
        }

        return out;
    }
    
    //TODO: make it faster: only the answerd study items answer rate should be reevaluated
    public Map<Integer, Histogram> getHistogramOfStudyItemAnswerRatesByDays() {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer
                = new AnswerDataByStudyItemContainer();

        Map<Integer, Histogram> out = new HashMap<>();

        GeneralFunctions generalFunctions = new GeneralFunctions();

        int lastDay = generalFunctions.milisecToDay(answerDataContainer.getAnswerData(0).date);
        int actualDay = -1;

        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);

            actualDay = generalFunctions.milisecToDay(answerData.date);

            if (actualDay != lastDay) {
                out.put(lastDay, answerDataByStudyItemsContainer.getHistogram());
                lastDay = actualDay;
            }

            answerDataByStudyItemsContainer.addAnswerData(answerData);
        }
        out.put(actualDay, answerDataByStudyItemsContainer.getHistogram());

        return out;
    }

    public double getAverageAnswerRateOfStudyItems() {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
        double sum = 0.0;
        for (int index : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            sum = sum + answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();
        }
        return sum / (double) answerDataByStudyItemsContainer.numberOfStudiedStudyItems();
    }

    public String getAverageAnswerRateOfStudyItemsAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(getAverageAnswerRateOfStudyItems());
    }

    public int getLongestIntervallSizeOfRightAnswers() {
        int longestIntervallSize = 0;
        int actualIntervallSize = 0;
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            if (answerDataContainer.getAnswerData(i).isRight) {
                actualIntervallSize++;
            } else {
                if (actualIntervallSize > longestIntervallSize) {
                    longestIntervallSize = actualIntervallSize;
                }
                actualIntervallSize = 0;
            }
        }
        if (actualIntervallSize > longestIntervallSize) {
            longestIntervallSize = actualIntervallSize;
        }

        return longestIntervallSize;
    }

    public Vector<Integer> getStudyItemIndexesOrderedByAnswerRate() {
        Logger logger = new Logger();
        logger.debug("run getStudyItemIndexesOrderedByAnswerRate function");

        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByRateOfRightAnswers());

        Vector<Integer> out = new Vector<>();
        for (int i = 0; i < datasToSort.length; i++) {
            out.add(datasToSort[i].getStudyItemIndex());
        }

        //logger.debug("orderen study item indexes: " + out);
        return out;
    }

    public Vector<Integer> getStudyItemIndexesOrderedByLastStudyDate() {
        Logger logger = new Logger();
        logger.debug("run getStudyItemIndexesOrderedByLastStudyDate function");

        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByLastStudyDate());

        Vector<Integer> out = new Vector<>();
        for (int i = 0; i < datasToSort.length; i++) {
            out.add(datasToSort[i].getStudyItemIndex());
        }

        //logger.debug("orderen study item indexes: " + out);
        return out;
    }

    public Vector<Integer> getStudyItemIndexesOrderedByNumberOfAnswers() {
        Logger logger = new Logger();
        logger.debug("run getStudyItemIndexesOrderedByNumberOfAnswers function");

        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);
        AnswerDataByStudyItem[] datasToSort = answerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

        Vector<Integer> out = new Vector<>();
        for (int i = 0; i < datasToSort.length; i++) {
            out.add(datasToSort[i].getStudyItemIndex());
        }

        //logger.debug("orderen study item indexes: " + out);
        return out;
    }

    public Map<Integer, Double> getAnswerRatesByGrammarItems() {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

        Map<Integer, Double> out = new HashMap<>();
        for (int studyItemIndex : answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            AnswerDataByStudyItem answerDataByStudyItem
                    = answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(studyItemIndex);
            out.put(studyItemIndex, answerDataByStudyItem.countRightAnswerRate());
        }

        return out;
    }
    
    public Map<Integer, Integer> numberOfNewStudyItemsTestedByDays() {
        Map<Integer, Integer> out = new HashMap<>();
        Set<Integer> questionedCardIndexes = new HashSet<>();
        Set<Integer> actualDayQuestionedCardIndexes = new HashSet<>();
        GeneralFunctions generalFunctions = new GeneralFunctions();
        
        AnswerData answerData = answerDataContainer.getAnswerData(0);
        int lastAnswerDay = generalFunctions.milisecToDay(answerData.date);
        int actualAnswerDay = 0;
        for (int i=0; i<numberOfAnswers(); i++) {
            answerData = answerDataContainer.getAnswerData(i);
            actualAnswerDay = generalFunctions.milisecToDay(answerData.date);
            if (lastAnswerDay != actualAnswerDay) {
                out.put(actualAnswerDay, actualDayQuestionedCardIndexes.size());
                questionedCardIndexes.addAll(actualDayQuestionedCardIndexes);
                actualDayQuestionedCardIndexes.clear();
                lastAnswerDay = actualAnswerDay;
            }
            else {
                if (!questionedCardIndexes.contains(answerData.index)) {
                    actualDayQuestionedCardIndexes.add(answerData.index);
                }
            }
        }
        out.put(actualAnswerDay,actualDayQuestionedCardIndexes.size());
        
        return out;
    }
    
    public String getPointProgressAsString() {
        AnswerDataByStudyItemContainer answerDataByStudyItemsContainer = new AnswerDataByStudyItemContainer();
        answerDataByStudyItemsContainer.loadDataFromAnswerDataContainer(answerDataContainer);

        double userPoints = 0;
        for (int testedStudyItemIndexes : 
                answerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            userPoints = userPoints 
                    + answerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(testedStudyItemIndexes).countRightAnswerRate();
        }
        
        int allPoints = studyItemContainer.numberOfStudyItems();
        
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(userPoints) + "\\" + Integer.toString(allPoints);
    }

    public double averageNumberOfAnswersOfCards() {
        return (double)(answerDataContainer.numberOfAnswers())
                /((double)(studyItemContainer.numberOfStudyItems()));
    }
    
    public String getAverageNumberOfAnswersOfCardsAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(averageNumberOfAnswersOfCards());
    }
    
    public int getNumberOfAnswersToday() {
        Map<Integer, Integer> numberOfAnswersByDays = getNumberOfAnswersByDays();
        
        Date date = new Date();
        long actualTime = date.getTime();
        
        GeneralFunctions generalFunctions = new GeneralFunctions();
        int actualDay = generalFunctions.milisecToDay(actualTime);
        
        if (numberOfAnswersByDays.containsKey(actualDay)) {
            return numberOfAnswersByDays.get(actualDay);
        }
        else {
            return 0;
        }
    }
    
    public int getNumberOfAnswersOnTheLastDays(int numberOfDays) {
        Map<Integer, Integer> numberOfAnswersByDays = getNumberOfAnswersByDays();

        Date date = new Date();
        GeneralFunctions generalFunctions = new GeneralFunctions();
        int today = generalFunctions.milisecToDay(date.getTime());

        int out = 0;
        
        for (int i = 0; i < numberOfDays; i++) {
            if (numberOfAnswersByDays.keySet().contains(today - i)) {
                out = out + numberOfAnswersByDays.get(today - i);
            }
        }

        return out;
    }
    
    public List<Integer> getBigRightIntervallSizesOrderedByDays(int intervallSizeTreshold) {
        List<Integer> dayAndBigRightIntervallSize = new ArrayList<>();
        GeneralFunctions generalFunctions = new GeneralFunctions();
        
        int actualRightIntervallSize = 0;
        
        for (int i=0; i<numberOfAnswers(); i++) {
            AnswerData answerData = answerDataContainer.getAnswerData(i);
            
            if (answerData.isRight) {
                actualRightIntervallSize++;
            }
            else {
                if (actualRightIntervallSize > intervallSizeTreshold) {
                    dayAndBigRightIntervallSize.add(generalFunctions.milisecToDay(answerData.date));
                    dayAndBigRightIntervallSize.add(actualRightIntervallSize);
                }
                actualRightIntervallSize = 0;
            }
        }
        
        return dayAndBigRightIntervallSize;
    }
    
}