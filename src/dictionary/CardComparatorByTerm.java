package dictionary;

import java.util.Comparator;

public class CardComparatorByTerm implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		return c1.s1.compareTo(c2.s1);
	}
}

