package dictionary;

import java.util.Comparator;

public class AnswerDataByCardComparatorByNumberOfAnswers implements Comparator<AnswerDataByCard> {

	@Override
	public int compare(AnswerDataByCard a1, AnswerDataByCard a2) {
		int a1NumberOfAnswers = a1.numberOfAnswers();
		int a2NumberOfAnswers = a2.numberOfAnswers();
		long a1LatestAnswerDate = a1.getLatestAnswerDate();
		long a2LatestAnswerDate = a2.getLatestAnswerDate();

		if (a1NumberOfAnswers > a2NumberOfAnswers) {
			return 1;
		}
		else
		if (a1NumberOfAnswers == a2NumberOfAnswers) {
			if (a1LatestAnswerDate > a2LatestAnswerDate) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}
}

