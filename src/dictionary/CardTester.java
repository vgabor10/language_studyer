package dictionary;

import study_item_objects.AnswerDataContainer;
import common.Logger;

import java.util.*;

public class CardTester {

	private CardContainer allCard;
	private CardContainer cardsToTest;
	private int numberOfCardsQuestioned;
	private final AnswerDataContainer userAnswers = new AnswerDataContainer();
	private Card actualQuestionedCard;
	private String userAnswerToActualQuestion; 
	private Map<String, Integer> acceptabelAnswersAndCardIndexesForActualQuestion;
	private boolean isGetAnswerToActualQuestion = false;

	private final Logger logger = new Logger();

	public CardTester() {
		numberOfCardsQuestioned = 0;
	}

	public void setAllCard(CardContainer cc) {
		allCard = cc;
	}

	public void setCardsToTest(CardContainer cc) {
		cardsToTest = cc;
	}
        
        public void setCardsToTestFromCardIndexesSet(Set<Integer> cardIndexes) {
            List<Integer> cardIndexesList = new Vector<Integer>(cardIndexes);
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

		acceptabelAnswersAndCardIndexesForActualQuestion =
			getAcceptabelAnswersAndCardIndexes(actualQuestionedCard.definition);

		logger.debug("acceptabel answers and card indexes: " + acceptabelAnswersAndCardIndexesForActualQuestion.toString());
	}

	public Card getActualQuestionedCard() {
		return actualQuestionedCard;
	}

	public void setUserAnswer(String answer) {
		userAnswerToActualQuestion = answer;

		isGetAnswerToActualQuestion = true;

		logger.debug("user answer: " + answer);

		if (answer.equals(actualQuestionedCard.term)) {
			Date date = new Date();
			userAnswers.addElement(actualQuestionedCard.index, true, date.getTime());
			logger.debug("added answer data: " + actualQuestionedCard.index + ", true");
		}
		else {

			if (acceptabelAnswersAndCardIndexesForActualQuestion.keySet().contains(answer)) {
				Date date = new Date();
				userAnswers.addElement(acceptabelAnswersAndCardIndexesForActualQuestion.get(answer), true, date.getTime());

				logger.debug("added answer data: " + acceptabelAnswersAndCardIndexesForActualQuestion.get(answer) + ", true");
			}
			else {
				Date date = new Date();
				userAnswers.addElement(actualQuestionedCard.index, false, date.getTime());

				logger.debug("added answer data: " + actualQuestionedCard.index + ", false");
			}
		}

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

	public boolean isUserAnswerRight() {
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
		Map<String, Integer> acceptableAnswersAndCardIndexes = new HashMap<String, Integer>();

		Set<String> definitionParts = new HashSet<String>(Arrays.asList(definition.split(", ")));

		for (int i=0; i<allCard.numberOfCards(); i++) {
			Card card = allCard.getCardByOrder(i);

			Set<String> definitionParts2 = new HashSet<String>(Arrays.asList(card.definition.split(", ")));

			definitionParts2.retainAll(definitionParts);

			if (!definitionParts2.isEmpty()) {
				acceptableAnswersAndCardIndexes.put(card.term, card.index);
			}
		}

		return acceptableAnswersAndCardIndexes;
	}

}
