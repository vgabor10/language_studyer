package experimental_classes;

import dictionary.*;
import common.*;
import experimental_classes.*;

import java.util.*;
import java.io.Console;
import java.text.DecimalFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CardTester2 {

	private CardContainer allCard;	//TODO: rename
	private CardContainer cardsToTest;
	private int numberOfCardsQuestioned;
	private AnswerDataContainer userAnswers = new AnswerDataContainer();
	private Card actualQuestion;	//TODO: rename actualQuestionedCard
	private String userAnswerToActualQuestion; 
	private Map<String, Integer> acceptabelAnswersAndCardIndexesForActualQuestion;
	private boolean isGetAnswerToActualQuestion = false;

	private Logger logger = new Logger();

	public CardTester2() {
		numberOfCardsQuestioned = 0;
	}

	public void setAllCard(CardContainer cc) {
		allCard = cc;
	}

	public void setCardsToTest(CardContainer cc) {
		cardsToTest = cc;
	}

	public void moveToNextCardToQuestion() {
		actualQuestion = cardsToTest.getCardByOrder(numberOfCardsQuestioned);
		numberOfCardsQuestioned++;

		userAnswerToActualQuestion = "";
		isGetAnswerToActualQuestion = false;

		logger.debug("questioned card: " + actualQuestion.toString());

		acceptabelAnswersAndCardIndexesForActualQuestion =
			getAcceptabelAnswersAndCardIndexes(actualQuestion.definition);

		logger.debug("acceptabel answers and card indexes: " + acceptabelAnswersAndCardIndexesForActualQuestion.toString());
	}

	public Card getActualQuestionedCard() {
		return actualQuestion;
	}

	public void setUserAnswer(String answer) {
		userAnswerToActualQuestion = answer;

		isGetAnswerToActualQuestion = true;

		logger.debug("user answer: " + answer);

		if (answer.equals(actualQuestion.term)) {
			Date date = new Date();
			userAnswers.addElement(actualQuestion.index, true, date.getTime());
			logger.debug("added answer data: " + actualQuestion.index + ", true");
		}
		else {

			if (acceptabelAnswersAndCardIndexesForActualQuestion.keySet().contains(answer)) {
				Date date = new Date();
				userAnswers.addElement(acceptabelAnswersAndCardIndexesForActualQuestion.get(answer), true, date.getTime());

				logger.debug("added answer data: " + acceptabelAnswersAndCardIndexesForActualQuestion.get(answer) + ", true");
			}
			else {
				Date date = new Date();
				userAnswers.addElement(actualQuestion.index, false, date.getTime());

				logger.debug("added answer data: " + actualQuestion.index + ", false");
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
		return actualQuestion.term;
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

	private Set<String> getAcceptabelAnswers(String definition) {
		Set<String> out = new HashSet<String>();

		Set<String> definitionParts = new HashSet<String>(Arrays.asList(definition.split(", ")));

		for (int i=0; i<allCard.numberOfCards(); i++) {
			Card card = allCard.getCardByOrder(i);

			Set<String> definitionParts2 = new HashSet<String>(Arrays.asList(card.definition.split(", ")));

			definitionParts2.retainAll(definitionParts);

			if (definitionParts2.size() != 0) {
				out.add(card.term);
			}
		}

		return out;
	}

	private Map<String, Integer> getAcceptabelAnswersAndCardIndexes(String definition) {
		Map<String, Integer> acceptableAnswersAndCardIndexes = new HashMap<String, Integer>();

		Set<String> definitionParts = new HashSet<String>(Arrays.asList(definition.split(", ")));

		for (int i=0; i<allCard.numberOfCards(); i++) {
			Card card = allCard.getCardByOrder(i);

			Set<String> definitionParts2 = new HashSet<String>(Arrays.asList(card.definition.split(", ")));

			definitionParts2.retainAll(definitionParts);

			if (definitionParts2.size() != 0) {
				acceptableAnswersAndCardIndexes.put(card.term, card.index);
			}
		}

		return acceptableAnswersAndCardIndexes;
	}

}
