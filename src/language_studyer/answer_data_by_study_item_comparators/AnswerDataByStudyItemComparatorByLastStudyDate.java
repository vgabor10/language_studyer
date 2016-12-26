package language_studyer.answer_data_by_study_item_comparators;

import java.util.Comparator;
import language_studyer.AnswerDataByStudyItem;

public class AnswerDataByStudyItemComparatorByLastStudyDate implements Comparator<AnswerDataByStudyItem> {

	@Override
	public int compare(AnswerDataByStudyItem a1, AnswerDataByStudyItem a2) {
		long a1lastStudyDate = a1.getLastStudyDate();
		long a2lastStudyDate = a2.getLastStudyDate();

		if (a1lastStudyDate > a2lastStudyDate) {
			return 1;
		}
		else {
			return -1;
		}
	}
}

