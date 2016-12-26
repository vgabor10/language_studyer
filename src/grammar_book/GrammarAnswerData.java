package grammar_book;

import language_studyer.AnswerData;

public class GrammarAnswerData extends AnswerData {

    public int exampleIndex;

    public GrammarAnswerData() {
        exampleIndex = -1;
    }

    @Override
    public void setDataFromString(String s) {
        index = Integer.parseInt(s.split("\t")[0]);
        exampleIndex = Integer.parseInt(s.split("\t")[1]);
        isRight = s.split("\t")[2].equals("1");
        date = Long.parseLong(s.split("\t")[3]);
    }

    @Override
    public String toStringData() {
        if (isRight) {
            return Integer.toString(index) + "\t" + Integer.toString(exampleIndex) + "\t1\t" + Long.toString(date);
        } else {
            return Integer.toString(index) + "\t" + Integer.toString(exampleIndex) + "\t0\t" + Long.toString(date);
        }
    }
}
