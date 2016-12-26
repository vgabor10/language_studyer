package dictionary;

import java.util.HashSet;
import java.util.Set;
import language_studyer.AnswerDataContainer;

//TODO: create gui dialog
public class DataFormatChecker {

    private CardContainer cardContainer;
    private AnswerDataContainer answerDataContainer;

    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }

    public void setAnswerDataContainer(AnswerDataContainer ac) {
        answerDataContainer = ac;
    }

    public int getNumberOfAnswersWithInvalidIndex() {
        Set<Integer> cardIndexes = cardContainer.getStudyItemIndexes();

        int numberOfAnswersWithInvalidIndex = 0;
        for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
            if (!cardIndexes.contains(answerDataContainer.getAnswerData(i).index)) {
                numberOfAnswersWithInvalidIndex++;
            }
        }

        return numberOfAnswersWithInvalidIndex;
    }

    public boolean areThereCardsWithSameIndex() {

        Set<Integer> cardIndexes = new HashSet<>();
        boolean b = false;
        for (int i = 0; i < cardContainer.numberOfCards(); i++) {
            Card card = cardContainer.getCardByOrder(i);
            if (cardIndexes.contains(card.index)) {
                b = true;
            } else {
                cardIndexes.add(card.index);
            }
        }

        return b;
    }

    public boolean isAnswerDataOrderedByDate() {
        int i = 0;
        while (i < answerDataContainer.numberOfAnswers() - 1
                && answerDataContainer.getAnswerData(i).date < answerDataContainer.getAnswerData(i + 1).date) {
            i++;
        }

        return i == answerDataContainer.numberOfAnswers() - 1;
    }

}
