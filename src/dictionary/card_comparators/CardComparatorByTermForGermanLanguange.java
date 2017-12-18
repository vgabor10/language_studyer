package dictionary.card_comparators;

import dictionary.Card;
import java.util.Comparator;

public class CardComparatorByTermForGermanLanguange implements Comparator<Card> {

    private String cardTermToFind;

    public CardComparatorByTermForGermanLanguange(String a) {
        cardTermToFind = a;
    }

    @Override
    public int compare(Card c1, Card c2) {
        String term1 = c1.term.toLowerCase();
        if (term1.substring(0, 2).equals("r ")
                || term1.substring(0, 2).equals("e ")
                || term1.substring(0, 2).equals("s ")) {
            term1 = term1.substring(2);
        }
        term1 = term1.replaceAll("ä", "a");

        String term2 = c2.term.toLowerCase();
        if (term2.substring(0, 2).equals("r ")
                || term2.substring(0, 2).equals("e ")
                || term2.substring(0, 2).equals("s ")) {
            term2 = term2.substring(2);
        }
        term2 = term2.replaceAll("ä", "a");
        
        if (term1.startsWith(cardTermToFind)
                && !term2.startsWith(cardTermToFind)) {
            return -1;
        } else {
            if (!term1.startsWith(cardTermToFind)
                    && term2.startsWith(cardTermToFind)) {
                return 1;
            } else {
                return term1.compareTo(term2);
            }
        }
    }
}
