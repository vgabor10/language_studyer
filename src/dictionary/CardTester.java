package dictionary;

import study_item_objects.AnswerDataContainer;
import common.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CardTester {

    private CardChooser cardChooser = new CardChooser();   
    
    private CardContainer allCard;
    private CardContainer cardsToTest = new CardContainer();
    private int numberOfCardsQuestioned = 0;
    private final AnswerDataContainer userAnswers = new AnswerDataContainer();
    private Card actualQuestionedCard;
    private String userAnswerToActualQuestion;
    private Map<String, Integer> acceptabelAnswersAndCardIndexesForActualQuestion
            = new HashMap<>();
    private boolean isGetAnswerToActualQuestion = false;
    
    private final Logger logger = new Logger();

    public void setData(DataContainer dataContainer) {
        this.allCard = dataContainer.cardContainer;
        this.cardChooser.setData(dataContainer);
    }

    public void startNewTest() {
        numberOfCardsQuestioned = 0;
        userAnswers.clear();
        cardsToTest.clear();
        actualQuestionedCard = null;
        userAnswerToActualQuestion = null;
        acceptabelAnswersAndCardIndexesForActualQuestion.clear();
        isGetAnswerToActualQuestion = false;
        
        Set<Integer> cardIndexesToTest = cardChooser.getCardIndexes();
        for (int cardIndex : cardIndexesToTest) {
            cardsToTest.addCard(allCard.getCardByIndex(cardIndex));
        }
        
        moveToNextCardToQuestion();
    }
    
    //before allCard need to be seted
    public void setCardsToTestFromCardIndexesSet(Set<Integer> cardIndexes) {
        List<Integer> cardIndexesList = new ArrayList<>(cardIndexes);
        java.util.Collections.shuffle(cardIndexesList);

        cardsToTest = new CardContainer();
        for (int index : cardIndexesList) {
            cardsToTest.addCard(allCard.getCardByIndex(index));
        }
    }

    public CardContainer getCardsToTest() {
        return cardsToTest;
    }

    public void moveToNextCardToQuestion() {
        actualQuestionedCard = cardsToTest.getCardByOrder(numberOfCardsQuestioned);
        numberOfCardsQuestioned++;

        userAnswerToActualQuestion = "";
        isGetAnswerToActualQuestion = false;

        logger.debug("questioned card: " + actualQuestionedCard.toString());

        acceptabelAnswersAndCardIndexesForActualQuestion
                = getAcceptabelAnswersAndCardIndexes(actualQuestionedCard.definition);

        logger.debug("acceptabel answers and card indexes: " + acceptabelAnswersAndCardIndexesForActualQuestion.toString());
    }

    public Card getActualQuestionedCard() {
        return actualQuestionedCard;
    }

    public void setUserAnswer(String answer) {
        userAnswerToActualQuestion = answer;

        isGetAnswerToActualQuestion = true;

        logger.debug("user answer: " + answer);

    }

    public void acceptUserAnswer() {
        if (userAnswerToActualQuestion.equals(actualQuestionedCard.term)) {
            Date date = new Date();
            userAnswers.addElement(actualQuestionedCard.index, true, date.getTime());
            logger.debug("added answer data: " + actualQuestionedCard.index + ", true");
        } else if (acceptabelAnswersAndCardIndexesForActualQuestion.keySet().contains(
                userAnswerToActualQuestion)) {
            Date date = new Date();
            userAnswers.addElement(
                    acceptabelAnswersAndCardIndexesForActualQuestion.get(userAnswerToActualQuestion), true, date.getTime());

            logger.debug("added answer data: " + acceptabelAnswersAndCardIndexesForActualQuestion.get(userAnswerToActualQuestion) + ", true");
        }
        else {
            Date date = new Date();
            userAnswers.addElement(actualQuestionedCard.index, true, date.getTime());
            logger.debug("added answer data: " + actualQuestionedCard.index + ", true");
        }
    }
    
    public void rejectUserAnswer() {
        Date date = new Date();
        userAnswers.addElement(actualQuestionedCard.index, false, date.getTime());

        logger.debug("added answer data: " + actualQuestionedCard.index + ", false");        
    }
    
    public void ignoreUserAnswer() {
        logger.debug("user answer ignored");  
    }
    
    public boolean isGetAnswerToActualQuestion() {
        return isGetAnswerToActualQuestion;
    }

    public String getUserActualAnswer() {
        return userAnswerToActualQuestion;
    }

    public String getStandardAnswerToLastQuestion() {
        return actualQuestionedCard.term;
    }

    public boolean isUserAnswerRightSuggestion() {
        return acceptabelAnswersAndCardIndexesForActualQuestion.containsKey(userAnswerToActualQuestion);
    }

    public boolean isMoreCardToTest() {
        return numberOfCardsQuestioned != cardsToTest.numberOfCards();
    }

    public AnswerDataContainer getUserAnswers() {
        return userAnswers;
    }

    public int getNumberOfQuestions() {
        return cardsToTest.numberOfCards();
    }

    public int numberOfCardsQuestioned() {
        return numberOfCardsQuestioned;
    }

    private Map<String, Integer> getAcceptabelAnswersAndCardIndexes(String definition) {
        Map<String, Integer> acceptableAnswersAndCardIndexes = new HashMap<>();

        Set<String> definitionParts = new HashSet<>(Arrays.asList(definition.split(", ")));

        for (int i = 0; i < allCard.numberOfCards(); i++) {
            Card card = allCard.getCardByOrder(i);

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

        for (int i = 0; i < allCard.numberOfCards(); i++) {
            Card card = allCard.getCardByOrder(i);

            Set<String> definitionParts2 = new HashSet<>(Arrays.asList(card.definition.split(", ")));

            definitionParts2.retainAll(definitionParts);

            if (!definitionParts2.isEmpty()) {
                acceptableCardIndexes.add(card.index);
            }
        }

        return acceptableCardIndexes;
    }

}
