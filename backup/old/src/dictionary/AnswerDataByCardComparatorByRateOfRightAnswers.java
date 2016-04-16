package dictionary;

import java.util.Comparator;

public class AnswerDataByCardComparatorByRateOfRightAnswers implements Comparator<AnswerDataByCard> {

	@Override
	public int compare(AnswerDataByCard a1, AnswerDataByCard a2) {
		double a = a2.countRightAnswerRate() - a1.countRightAnswerRate();
		if (a > 0) {
			return 1;
		}
		else
		if (a == 0) {
			return 0;
		}
		else {
			return -1;
		}
	}
}

