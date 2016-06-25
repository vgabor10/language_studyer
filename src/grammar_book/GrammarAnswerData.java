package grammar_book;

import common.*;

public class GrammarAnswerData extends AnswerData {

	public int exampleIndex;

	GrammarAnswerData() {
		exampleIndex = -1;
	}

	public void setDataFromString(String s) {
		index = Integer.parseInt(s.split("\t")[0]);
		exampleIndex = Integer.parseInt(s.split("\t")[1]);

		if (s.split("\t")[2].equals("1")) {
			isRight = true;
		}
		else {
			isRight = false;
		}

		date = Long.parseLong(s.split("\t")[3]);
	}

	public String toStringData() {
		if (isRight) {
			return Integer.toString(index) + "\t" + Integer.toString(exampleIndex) + "\t1\t" + Long.toString(date);
		}
		else {
			return Integer.toString(index) + "\t" + Integer.toString(exampleIndex) + "\t0\t" + Long.toString(date);
		}
	}
}
