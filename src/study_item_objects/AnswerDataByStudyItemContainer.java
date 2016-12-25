package study_item_objects;

import common.GeneralFunctions;
import java.util.*;

public class AnswerDataByStudyItemContainer {

    private Map<Integer, AnswerDataByStudyItem> data = new HashMap<>();

    public void addAnswerData(AnswerData answerData) {
        if (data.containsKey(answerData.index)) {
            data.get(answerData.index).addAnswer(answerData);
        } else {
            AnswerDataByStudyItem answerDataByStudyItem = new AnswerDataByStudyItem();
            answerDataByStudyItem.addAnswer(answerData);
            data.put(answerData.index, answerDataByStudyItem);
        }
    }

    public Set<Integer> getStudyingDays() {
        GeneralFunctions generalFunctions = new GeneralFunctions();
        Set<Integer> studyingDays = new HashSet<>();
        for (int index : data.keySet()) {
            AnswerDataByStudyItem answerDataByStudyItem = getAnswerDataByStudyItemByIndex(index);
            for (int j = 0; j < answerDataByStudyItem.numberOfAnswers(); j++) {
                studyingDays.add(generalFunctions.milisecToDay(answerDataByStudyItem.getAnswer(j).date));
            }
        }
        return studyingDays;
    }

    public boolean containsStudyItemWithIndex(int index) {
        return data.keySet().contains(index);
    }

    public void removeAnswerDataByStudyItemByIndex(int index) {
        data.remove(index);
    }
    
    public Histogram getHistogram() {

        Histogram histogram = new Histogram();

        for (int index : data.keySet()) {
            double rightAnswerRate = data.get(index).countRightAnswerRate();
            if (rightAnswerRate != -1.0) {
                histogram.addAnElementByRightAnswerRate(rightAnswerRate);
            }
        }

        return histogram;
    }

    public void loadDataFromAnswerDataContainer(AnswerDataContainer answerDataContainer) {
        clear();
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            addAnswerData(answerDataContainer.getAnswerData(i));
        }
    }

    //TODO: think it over: not studyed studyItems is not seen here
    public void addDataFromAnswerDataContainer(AnswerDataContainer answerDataContainer) {
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            this.addAnswerData(answerDataContainer.getAnswerData(i));
        }
    }

    public Set<Integer> getTestedStudyItemIndexes() {
        return data.keySet();
    }

    public AnswerDataByStudyItem getAnswerDataByStudyItemByIndex(int index) {
        return data.get(index);
    }

    public double getAverageAnswerRateOfStudyItems() {
        double sum = 0;
        for (int index : data.keySet()) {
            sum = sum + data.get(index).countRightAnswerRate();
        }
        return sum / (double) numberOfStudiedStudyItems();
    }

    public int numberOfStudiedStudyItems() {
        return data.size();
    }

    public AnswerDataByStudyItem[] toArray() {
        AnswerDataByStudyItem[] array = new AnswerDataByStudyItem[numberOfStudiedStudyItems()];
        int arrayIndex = 0;
        for (int index : data.keySet()) {
            array[arrayIndex] = data.get(index);
            arrayIndex++;
        }
        return array;
    }

    public void clear() {
        data.clear();
    }

}
