package dictionary;

import java.util.Comparator;

public class CardComparatorByTerm implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		return c1.term.compareTo(c2.term);
	}
}

