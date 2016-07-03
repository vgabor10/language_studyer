package common;

import java.util.Comparator;

public class AnswerDataByStudyItemComparatorByLastStudyDate implements Comparator<AnswerDataByStudyItem> {

	@Override
	public int compare(AnswerDataByStudyItem a1, AnswerDataByStudyItem a2) {
		long a1lastStudyDate = a1.getLatestAnswerDate();
		long a2lastStudyDate = a2.getLatestAnswerDate();

		if (a1lastStudyDate > a2lastStudyDate) {
			return 1;
		}
		else {
			return -1;
		}
	}
}

