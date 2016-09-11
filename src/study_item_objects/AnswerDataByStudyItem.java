package study_item_objects;

import java.util.ArrayList;

public class AnswerDataByStudyItem {

    //datas are sorted by date (growing)
    private ArrayList<AnswerData> data = new ArrayList<>();
    
    public void addAnswer(AnswerData answerData) {
        int i = 0;
        while (i < data.size() && data.get(i).date < answerData.date) {
            i++;
        }

        data.add(i, answerData);
    }

    public int numberOfAnswers() {
        return data.size();
    }

    public AnswerData getAnswer(int i) {
        return data.get(i);
    }

    public int getStudyItemIndex() {
        return data.get(0).index;
    }

    public long getLastStudyDate() {
        return data.get(data.size() - 1).date;
    }

    public double countRightAnswerRate() {
        if (1 <= numberOfAnswers()) {

            String answerDataClassName = data.get(0).getClass().getSimpleName();
            
            //System.out.println("LOG: answerDataClassName: " + answerDataClassName);
            
            if (answerDataClassName.equals("GrammarAnswerData")) {
                int i = data.size() - 1;
                int actualAnswerWeight = 50;
                int allRightAnswerWeight = 0;
                int allAnswerWeight = 0;
                while (0 <= i && 1 <= actualAnswerWeight) {
                    if (data.get(i).isRight) {
                        allRightAnswerWeight 
                                = allRightAnswerWeight + actualAnswerWeight;
                    }
                    i--;
                    allAnswerWeight = allAnswerWeight + actualAnswerWeight;
                    actualAnswerWeight--;
                }

                return (double) allRightAnswerWeight / (double) allAnswerWeight;
            }
            else {
                int i = data.size() - 1;
                int actualAnswerWeight = 10;
                int allRightAnswerWeight = 0;
                int allAnswerWeight = 0;
                while (0 <= i && 1 <= actualAnswerWeight) {
                    if (data.get(i).isRight) {
                        allRightAnswerWeight 
                                = allRightAnswerWeight + actualAnswerWeight;
                    }
                    i--;
                    allAnswerWeight = allAnswerWeight + actualAnswerWeight;
                    actualAnswerWeight--;
                }

                return (double) allRightAnswerWeight / (double) allAnswerWeight;
            }
        } else {
            return -1;
        }
    }

    public void loadDataFromAnswerDataContainer(int index
            , AnswerDataContainer answerDataContainer) {
        
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            if (answerDataContainer.getAnswerData(i).index == index) {
                addAnswer(answerDataContainer.getAnswerData(i));
            }
        }
    }

    public void clear() {
        data.clear();
    }
}
