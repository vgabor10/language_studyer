package study_item_objects;

import java.util.ArrayList;

public class AnswerDataContainer {

    private ArrayList<AnswerData> data = new ArrayList<>();

    public void addAnswerData(AnswerData answerData) {
            data.add(answerData);
    }

    public void addElement(int index, boolean isRight, long date) {
        AnswerData answerData = new AnswerData();

        answerData.index = index;
        answerData.isRight = isRight;
        answerData.date = date;

        data.add(answerData);
    }

    public int numberOfAnswers() {
            return data.size();
    }

    public AnswerData getAnswerData(int index) {
            return data.get(index);
    }

    public void removeAnswersWithIndex(int index) {
            int i=0;
            while (i<numberOfAnswers()) {
                    if (getAnswerData(i).index == index) {
                            data.remove(i);
                    }
                    else {
                            i++;
                    }
            }
    }

    public void appendAnswerDataContainer(AnswerDataContainer ac) {
        for (int i=0; i<ac.numberOfAnswers(); i++) {
            data.add(ac.getAnswerData(i));
        }
    }

    public void toScreenData() {
        for(int i=0; i<data.size(); i++){
            System.out.println(data.get(i).toStringData());
        }
    }

    @Override
    public String toString() {
            String out = "";

            if (1 <= numberOfAnswers()) {
                    for(int i=0; i<numberOfAnswers()-1; i++){
                            out = out + getAnswerData(i).toStringData() + "\n";
                    }
                    out = out + getAnswerData(numberOfAnswers()-1).toStringData();
            }

            return out;
    }

}
