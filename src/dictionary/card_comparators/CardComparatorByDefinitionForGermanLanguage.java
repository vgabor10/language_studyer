package dictionary.card_comparators;

import dictionary.Card;
import java.util.Comparator;

public class CardComparatorByDefinitionForGermanLanguage implements Comparator<Card> {

        private String cardDefinitionToFind;

        public CardComparatorByDefinitionForGermanLanguage(String a) {
            cardDefinitionToFind = a;
        }
        
	@Override
	public int compare(Card c1, Card c2) {
            String def1 = c1.definition.toLowerCase();
            String def2 = c2.definition.toLowerCase();

            if (def1.startsWith(cardDefinitionToFind)
                    && !def2.startsWith(cardDefinitionToFind)) {
                return -1;
            } else {
                if (!def1.startsWith(cardDefinitionToFind)
                        && def2.startsWith(cardDefinitionToFind)) {
                    return 1;
                } else {
                    return def1.compareTo(def2);
                }
            }
	}
}

