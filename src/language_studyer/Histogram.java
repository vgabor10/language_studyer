package language_studyer;

public class Histogram {

    private final int[] data = new int[10];

    Histogram() {
        for (int i = 0; i < 10; i++) {
            data[i] = 0;
        }
    }

    public void addAnElementByRightAnswerRate(double rightAnswerRate) {
        if (rightAnswerRate == 1.0) {
            data[9]++;
        } else {
            data[(int) Math.floor(rightAnswerRate * 10.0)]++;
        }
    }

    public int getNumberOfObjectsByCategory(int category) {
        return data[category];
    }

    public String toStringHorisontally(String separatorString) {
        String out = Integer.toString(data[0]);
        for (int i = 1; i < 10; i++) {
            out = out + separatorString + data[i];
        }
        return out;
    }

}
