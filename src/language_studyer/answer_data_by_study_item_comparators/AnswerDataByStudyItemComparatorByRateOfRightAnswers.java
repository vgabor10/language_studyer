package language_studyer.answer_data_by_study_item_comparators;

import java.util.Comparator;
import language_studyer.AnswerDataByStudyItem;

public class AnswerDataByStudyItemComparatorByRateOfRightAnswers implements Comparator<AnswerDataByStudyItem> {

	@Override
	public int compare(AnswerDataByStudyItem a1, AnswerDataByStudyItem a2) {
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

