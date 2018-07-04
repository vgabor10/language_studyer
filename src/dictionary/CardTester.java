package dictionary;

import common.Logger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import language_studyer.StudyItemTester;

public class CardTester extends StudyItemTester {

    private final Logger logger = new Logger();
    
    protected Map<String, Integer> acceptabelAnswersAndCardIndexesForActualQuestion
            = new HashMap<>();

    @Override
    public void moveToNextCardToQuestion() {
        actualQuestionedStudyItem = studyItemsToTest.getStudyItemByOrder(numberOfItemsQuestioned);
        numberOfItemsQuestioned++;

        userAnswerToActualQuestion = "";
        isGetAnswerToActualQuestion = false;

        logger.debug("questioned card: " + actualQuestionedStudyItem.toString());

        acceptabelAnswersAndCardIndexesForActualQuestion
                = getAcceptabelAnswersAndCardIndexes(((Card) actualQuestionedStudyItem).definition);

        logger.debug("acceptabel answers and card indexes: " + acceptabelAnswersAndCardIndexesForActualQuestion.toString());
    }

    public void acceptUserAnswer() {
        if (userAnswerToActualQuestion.equals(((Card) actualQuestionedStudyItem).term)) {
            Date date = new Date();
            userAnswers.addElement(actualQuestionedStudyItem.index, true, date.getTime());
            logger.debug("added answer data: " + actualQuestionedStudyItem.index + ", true");
        } else if (acceptabelAnswersAndCardIndexesForActualQuestion.keySet().contains(
                userAnswerToActualQuestion)) {
            Date date = new Date();
            userAnswers.addElement(
                    acceptabelAnswersAndCardIndexesForActualQuestion.get(userAnswerToActualQuestion), true, date.getTime());

            logger.debug("added answer data: " + acceptabelAnswersAndCardIndexesForActualQuestion.get(userAnswerToActualQuestion) + ", true");
        }
        else {
            Date date = new Date();
            userAnswers.addElement(actualQuestionedStudyItem.index, true, date.getTime());
            logger.debug("added answer data: " + actualQuestionedStudyItem.index + ", true");
        }
    }

    public String getStandardAnswerToLastQuestion() {
        return ((Card) actualQuestionedStudyItem).term;
    }

    public boolean isUserAnswerRightSuggestion() {
        for (String acceptabelAnswer : acceptabelAnswersAndCardIndexesForActualQuestion.keySet()) {
            if (acceptabelAnswer.toLowerCase().equals(userAnswerToActualQuestion.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Integer> getAcceptabelAnswersAndCardIndexes(String definition) {
        Map<String, Integer> acceptableAnswersAndCardIndexes = new HashMap<>();

        Set<String> definitionParts = new HashSet<>(Arrays.asList(definition.split(", ")));

        for (int i = 0; i < allStudyItem.numberOfStudyItems(); i++) {
            Card card = (Card) allStudyItem.getStudyItemByOrder(i);

            Set<String> definitionParts2 = new HashSet<>(Arrays.asList(card.definition.split(", ")));

            definitionParts2.retainAll(definitionParts);

            if (!definitionParts2.isEmpty()) {
                acceptableAnswersAndCardIndexes.put(card.term, card.index);
            }
        }

        return acceptableAnswersAndCardIndexes;
    }

    public Set<Integer> getAcceptableCardIndexes(String definition) {
        Set<Integer> acceptableCardIndexes = new HashSet<>();

        Set<String> definitionParts = new HashSet<>(Arrays.asList(definition.split(", ")));

        for (int i = 0; i < allStudyItem.numberOfStudyItems(); i++) {
            Card card = (Card) allStudyItem.getStudyItemByOrder(i);

            Set<String> definitionParts2 = new HashSet<>(Arrays.asList(card.definition.split(", ")));

            definitionParts2.retainAll(definitionParts);

            if (!definitionParts2.isEmpty()) {
                acceptableCardIndexes.add(card.index);
            }
        }

        return acceptableCardIndexes;
    }

    public Card getActualQuestionedCard() {
       return (Card) this.actualQuestionedStudyItem;
    }

}
