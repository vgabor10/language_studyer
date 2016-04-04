package common;

public class AnswerData {

	public int index;
	public boolean isRight;
	public long date;

	public AnswerData() {
		index = -1;
		date = -1;
	}

	public AnswerData(int i, boolean b, long d) {	//TODO: remove this constructor
		index = i;
		isRight = b;
		date = d;
	}

	public void setDataFromString(String s) {	//TODO: take to other class
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
