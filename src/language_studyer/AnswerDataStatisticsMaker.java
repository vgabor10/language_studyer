package language_studyer;

import common.GeneralFunctions;
import common.Logger;
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
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByLastStudyDate;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByNumberOfAnswers;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByRateOfRightAnswers;

public class AnswerDataStatisticsMaker {
        
    public AnswerDataByStudyItemContainer studiedAnswerDataByStudyItemsContainer;
    public AnswerDataContainer studiedAnswerDataContainer;
    public Set<Integer> studiedStudyItemIndexes = new HashSet<>();

    public AnswerDataContainer getAnswerDataContainer() {
        return this.studiedAnswerDataContainer;
    }

    public int numberOfAnswersAtDay(int day) {
        int numberOfAnswers = 0;
        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            if (generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(i).date) == day) {
                numberOfAnswers++;
            }
        }
        return numberOfAnswers;
    }

    public int numberOfRightAnswersAtDay(int day) {
        int numberOfAnswers = 0;
        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            if (studiedAnswerDataContainer.getAnswerData(i).isRight
                    && generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(i).date) == day) {
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
        if (studiedAnswerDataContainer.numberOfAnswers() != 0) {
            int sum = 0;
            for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
                if (studiedAnswerDataContainer.getAnswerData(i).isRight) {
                    sum++;
                }
            }
            return (double) sum / (double) studiedAnswerDataContainer.numberOfAnswers() * 100.0;
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
        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            StudyItemInexes.add(studiedAnswerDataContainer.getAnswerData(i).index);
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
        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            answerDates.add(studiedAnswerDataContainer.getAnswerData(i).date);
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
        if (studiedAnswerDataContainer.numberOfAnswers() != 0) {
            Map<Integer, String> out = new HashMap<>();

            List<Long> answerDates = new ArrayList<>();

            GeneralFunctions generalFunctions = new GeneralFunctions();
            int actualDay = generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(0).date);

            for (int i = 1; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
                int newDay = generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(i).date);
                if (actualDay == newDay) {
                    answerDates.add(studiedAnswerDataContainer.getAnswerData(i).date);
                } else {
                    long practisingTime = evaluatePractisingTime(answerDates);
                    int hours = (int) practisingTime / 3600000;
                    int minutes = (int) ((practisingTime - 3600000 * hours) / 60000);

                    out.put(actualDay, Integer.toString(hours) + " hours " + Integer.toString(minutes) + " minutes");

                    actualDay = newDay;
                    answerDates.clear();
                    answerDates.add(studiedAnswerDataContainer.getAnswerData(i).date);
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
        
        if (studiedAnswerDataByStudyItemsContainer.numberOfStudiedStudyItems() 
                == studiedStudyItemIndexes.size()) {

            Set<Integer> testedStudyItemIndexes = studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes();
            numberOfQuestionsOfLeastStudiedStudyItem = Integer.MAX_VALUE;
            for (int index : testedStudyItemIndexes) {
                int numberOfAnswers = studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).numberOfAnswers();
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
        
        HashMap<Integer, Integer> histogramOfStudyItemsByNumberOfAnswers = new HashMap<>();

        Set<Integer> testedStudyItemIndexes = studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes();
        for (int index : testedStudyItemIndexes) {
            int numberOfAnswers = studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).numberOfAnswers();
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

        int numberOfAnswers = 0;
        int numberOfRightAnswers = 0;

        Set<Integer> testedStudyItemIndexes = studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes();

        for (int i : testedStudyItemIndexes) {
            if (x < studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(i).numberOfAnswers()) {
                if (studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(i).getAnswer(x).isRight) {
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
        return studiedStudyItemIndexes.size();
    }

    public int numberOfAnswers() {
        return studiedAnswerDataContainer.numberOfAnswers();
    }

    public int getNumberOfQuestionedStudyItems() {
        Set<Integer> studyItemIndexes = new HashSet<>();
        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            studyItemIndexes.add(studiedAnswerDataContainer.getAnswerData(i).index);
        }
        return studyItemIndexes.size();
    }

    public long getLastQuestionedStudyItemDate() {
        if (studiedAnswerDataContainer.numberOfAnswers() == 0) {
             return 0;
        }
        
        Set<Integer> StudyItemIndexes = new HashSet<>();
        int i = studiedAnswerDataContainer.numberOfAnswers() - 1;
        int numberOfQuestionedStudyItems = getNumberOfQuestionedStudyItems();
        while ((int) StudyItemIndexes.size() < numberOfQuestionedStudyItems) {
            StudyItemIndexes.add(studiedAnswerDataContainer.getAnswerData(i).index);
            i--;
        }

        return studiedAnswerDataContainer.getAnswerData(i + 1).date;
    }

    public int getNumberOfStudyingDays() {
        Set<Integer> studyingDays = new HashSet<>();
        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            studyingDays.add(generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(i).date));
        }

        return studyingDays.size();
    }

    public Map<Integer, Integer> getNumberOfAnswersByDays() {
        Map<Integer, Integer> out = new HashMap<>();

        GeneralFunctions generalFunctions = new GeneralFunctions();

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            int answerDataDay
                    = generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(i).date);

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

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = studiedAnswerDataContainer.getAnswerData(i);

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
        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            int hour = generalFunctions.milisecToHour(studiedAnswerDataContainer.getAnswerData(i).date);
            numberOfGivenAnswersByHours[hour]++;
        }

        return numberOfGivenAnswersByHours;
    }

    public int[] getHistogramOfStudyItemsByAnswerRate() {

        int[] numberOfStudyItemsInCategory = new int[10];

        for (int index : studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            double rateOfRightAnswers = studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();

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

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = studiedAnswerDataContainer.getAnswerData(i);
            
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

        int lastDay = generalFunctions.milisecToDay(studiedAnswerDataContainer.getAnswerData(0).date);
        int actualDay = -1;

        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            AnswerData answerData = studiedAnswerDataContainer.getAnswerData(i);

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
        double sum = 0.0;
        for (int index : studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            sum = sum + studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(index).countRightAnswerRate();
        }
        return sum / (double) studiedAnswerDataByStudyItemsContainer.numberOfStudiedStudyItems();
    }

    public String getAverageAnswerRateOfStudyItemsAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(getAverageAnswerRateOfStudyItems());
    }

    public int getLongestIntervallSizeOfRightAnswers() {
        int longestIntervallSize = 0;
        int actualIntervallSize = 0;
        for (int i = 0; i < studiedAnswerDataContainer.numberOfAnswers(); i++) {
            if (studiedAnswerDataContainer.getAnswerData(i).isRight) {
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

        AnswerDataByStudyItem[] datasToSort = studiedAnswerDataByStudyItemsContainer.toArray();
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

        AnswerDataByStudyItem[] datasToSort = studiedAnswerDataByStudyItemsContainer.toArray();
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

        AnswerDataByStudyItem[] datasToSort = studiedAnswerDataByStudyItemsContainer.toArray();
        Arrays.sort(datasToSort, new AnswerDataByStudyItemComparatorByNumberOfAnswers());

        Vector<Integer> out = new Vector<>();
        for (int i = 0; i < datasToSort.length; i++) {
            out.add(datasToSort[i].getStudyItemIndex());
        }

        //logger.debug("orderen study item indexes: " + out);
        return out;
    }

    public Map<Integer, Double> getAnswerRatesByGrammarItems() {

        Map<Integer, Double> out = new HashMap<>();
        for (int studyItemIndex : studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            AnswerDataByStudyItem answerDataByStudyItem
                    = studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(studyItemIndex);
            out.put(studyItemIndex, answerDataByStudyItem.countRightAnswerRate());
        }

        return out;
    }
    
    public Map<Integer, Integer> numberOfNewStudyItemsTestedByDays() {
        Map<Integer, Integer> out = new HashMap<>();
        Set<Integer> questionedCardIndexes = new HashSet<>();
        Set<Integer> actualDayQuestionedCardIndexes = new HashSet<>();
        GeneralFunctions generalFunctions = new GeneralFunctions();
        
        AnswerData answerData = studiedAnswerDataContainer.getAnswerData(0);
        int lastAnswerDay = generalFunctions.milisecToDay(answerData.date);
        int actualAnswerDay = 0;
        for (int i=0; i<numberOfAnswers(); i++) {
            answerData = studiedAnswerDataContainer.getAnswerData(i);
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

        double userPoints = 0;
        for (int testedStudyItemIndexes : 
                studiedAnswerDataByStudyItemsContainer.getTestedStudyItemIndexes()) {
            userPoints = userPoints 
                    + studiedAnswerDataByStudyItemsContainer.getAnswerDataByStudyItemByIndex(testedStudyItemIndexes).countRightAnswerRate();
        }
        
        int allPoints = studiedStudyItemIndexes.size();
        
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(userPoints) + "\\" + Integer.toString(allPoints);
    }

    public double averageNumberOfAnswersOfCards() {
        return (double)(studiedAnswerDataContainer.numberOfAnswers())
                /((double)(studiedStudyItemIndexes.size()));
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
            AnswerData answerData = studiedAnswerDataContainer.getAnswerData(i);
            
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
