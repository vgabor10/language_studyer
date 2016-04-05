package dictionary;

import java.util.Comparator;

public class CardComparatorByTermForGermanLanguange implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		String term = c1.term.toLowerCase();
		if (term.substring(0,2).equals("r ") || term.substring(0,2).equals("e ") || term.substring(0,2).equals("s ")) {
			term = term.substring(2);
		}

		String definition = c2.term.toLowerCase();
		if (definition.substring(0,2).equals("r ") || definition.substring(0,2).equals("e ") || definition.substring(0,2).equals("s ")) {
			definition = definition.substring(2);
		}

		return term.compareTo(definition);
	}
}

