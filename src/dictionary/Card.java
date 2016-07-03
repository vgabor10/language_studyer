package dictionary;

import study_item_objects.StudyItem;

public class Card extends StudyItem {

	public String term;
	public String definition;
	public String group;

	public Card() {
		index = -1;
		term = "";
		definition = "";
		group = "";
	}

	public Card(int i, String t, String d) {
		index = i;
		term = t;
		definition = d;
		group = "";
	}

	public String toStringData() {
		if (group.equals("")) {
			return Integer.toString(index) + "\t" + term + "\t" + definition;
		}
		else {
			return Integer.toString(index) + "\t" + term + "\t" + definition + "\t" + group;
		}
	}

        @Override
	public String toString() {
		return term + " - " + definition;
	}

	public String toStringReverse() {
		return definition + " - " + term;
	}
}
