package dictionary;

import java.util.*;

public class AnswerData {

	public int index;	//index of card TODO: replace name to cardIndex
	public boolean isRight;
	public long date;

	AnswerData() {
		index = -1;
	}

	AnswerData(int i, boolean b, long d) {
		index = i;
		isRight = b;
		date = d;
	}

	public void setDataFromString(String s) {
		index = Integer.parseInt(s.split(" ")[0]);

		if (s.split(" ")[1].equals("1")) {
			isRight = true;
		}
		else {
			isRight = false;
		}

		date = Long.parseLong(s.split(" ")[2]);
	}

	public String toStringData() {
		if (isRight) {
			return Integer.toString(index) + " 1 " + Long.toString(date);
		}
		else {
			return Integer.toString(index) + " 0 " + Long.toString(date);
		}
	}
}
