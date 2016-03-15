package dictionary;

import java.util.Comparator;

public class CardComparatorByTermForGermanLanguange implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		String s1 = c1.s1.toLowerCase();
		if (s1.substring(0,2).equals("r ") || s1.substring(0,2).equals("e ") || s1.substring(0,2).equals("s ")) {
			s1 = s1.substring(2);
		}

		String s2 = c2.s1.toLowerCase();
		if (s2.substring(0,2).equals("r ") || s2.substring(0,2).equals("e ") || s2.substring(0,2).equals("s ")) {
			s2 = s2.substring(2);
		}

		return s1.compareTo(s2);
	}
}

