package dictionary.card_comparators;

import dictionary.Card;
import java.util.Comparator;

public class CardComparatorByDefinition implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		String c1def = c1.definition.toLowerCase();
		String c2def = c2.definition.toLowerCase();


		return c1def.compareTo(c2def);
	}
}

