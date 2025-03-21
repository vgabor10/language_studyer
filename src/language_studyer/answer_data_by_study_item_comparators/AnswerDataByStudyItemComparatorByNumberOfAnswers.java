package language_studyer.answer_data_by_study_item_comparators;

import java.util.Comparator;
import language_studyer.AnswerDataByStudyItem;

public class AnswerDataByStudyItemComparatorByNumberOfAnswers implements Comparator<AnswerDataByStudyItem> {

	@Override
	public int compare(AnswerDataByStudyItem a1, AnswerDataByStudyItem a2) {
		int a1NumberOfAnswers = a1.numberOfAnswers();
		int a2NumberOfAnswers = a2.numberOfAnswers();
		long a1LatestAnswerDate = a1.getLastStudyDate();
		long a2LatestAnswerDate = a2.getLastStudyDate();

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

