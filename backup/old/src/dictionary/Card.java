package dictionary;

public class Card {

	public int index;
	public String s1;
	public String s2;

	public Card() {
		index = -1;
		s1 = "";
		s2 = "";
	}

	public Card(int i, String t, String d) {
		index = i;
		s1 = t;
		s2 = d;
	}

	public void setDataFromString(String s) {
		index = Integer.parseInt(s.split("\t")[0]);
		s1 = s.split("\t")[1];
		s2 = s.split("\t")[2];
	}

	public String toStringData() {
		return Integer.toString(index) + "\t" + s1 + "\t" + s2;
	}

	public String toString() {
		return s1 + " - " + s2;
	}

	public String toStringReverse() {
		return s2 + " - " + s1;
	}
}
