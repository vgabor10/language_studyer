package dictionary;

import common.*;

public class Card extends StudyItem {

	public String term;
	public String definition;
	public String group;

	public Card() {
		index = -1;
		term = "";
		definition = "";
		group = "no group";
	}

	public Card(int i, String t, String d) {
		index = i;
		term = t;
		definition = d;
	}

	public String toStringData() {
		return Integer.toString(index) + "\t" + term + "\t" + definition;
	}

	public String toString() {
		return term + " - " + definition;
	}

	public String toStringReverse() {
		return definition + " - " + term;
	}
}
