package dictionary;

import common.*;

import java.util.*;

public class DictionaryDataFormatChecker {

	private CardContainer cardContainer;
	private AnswerDataContainer answerDataContainer;

	public void setCardContainer(CardContainer cc) {
		cardContainer = cc;
	}

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void toScreenNumberOfAnswersWithInvalidIndex() {
		Set<Integer> cardIndexes = cardContainer.getStudyItemIndexes();

		int numberOfAnswersWithInvalidIndex = 0;
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (!cardIndexes.contains(answerDataContainer.getAnswerData(i).index)) {
				numberOfAnswersWithInvalidIndex++;
			}
		}

		System.out.println("number of answers with invalid index: " + numberOfAnswersWithInvalidIndex);
	}

	public void toScreenIfThereAreCardsWithSameIndex() {
		Set<Integer> cardIndexes = new HashSet<Integer>();
		boolean b = false;
		for (int i=0; i<cardContainer.numberOfCards(); i++) {
			Card card = cardContainer.getCardByOrder(i);
			if (cardIndexes.contains(card.index)) {
				b = true;
			}
			else {
				cardIndexes.add(card.index);
			}
		}

		if (b) {
			System.out.println("there are cards with same index");
		}
		else {
			System.out.println("fortunately there are no cards with same index");
		}
	}

}