package grammar_book;

import java.util.*;

public class GrammarAnswerData {

	public int grammarItemIndex;
	public int exampleIndex;
	public boolean isRight;
	public long date;

	GrammarAnswerData() {
		grammarItemIndex = -1;
		exampleIndex = -1;
	}

	GrammarAnswerData(int gii, int ei, boolean b, long d) {
		grammarItemIndex = gii;
		exampleIndex = ei;
		isRight = b;
		date = d;
	}

	public void setDataFromString(String s) {
		grammarItemIndex = Integer.parseInt(s.split("\t")[0]);
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
			return Integer.toString(grammarItemIndex) + "\t" + Integer.toString(exampleIndex) + "\t1\t" + Long.toString(date);
		}
		else {
			return Integer.toString(grammarItemIndex) + "\t" + Integer.toString(exampleIndex) + "\t0\t" + Long.toString(date);
		}
	}
}
