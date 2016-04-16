import java.util.Comparator;

public class AnswerDataByCardComparatorByRateOfRightAnswers implements Comparator<AnswerDataByCard> {

	@Override
	public int compare(AnswerDataByCard a1, AnswerDataByCard a2) {
		return a1.countRightAnswerRate() < a2.countRightAnswerRate();
	}
}

