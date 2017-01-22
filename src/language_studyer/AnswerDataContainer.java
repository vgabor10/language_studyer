package language_studyer;

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

    public void appendAnswerDataContainer(AnswerDataContainer answerDataContainer) {
        for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
            data.add(answerDataContainer.getAnswerData(i));
        }
    }

    public void removeAnswerDataWithIndex(int index) {
        int i=0;
        while (i<numberOfAnswers()) {
            if (this.data.get(i).index == index) {
                data.remove(i);
            }
            else {
                i++;
            }
        }
    }
    
    public void clear() {
        data.clear();
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
