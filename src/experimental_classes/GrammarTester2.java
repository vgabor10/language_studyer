package experimental_classes;

import dictionary.*;
import study_item_objects.AnswerDataContainer;
import common.Logger;
import grammar_book.Example;
import grammar_book.GrammarAnswerData;
import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;

import java.util.*;

public class GrammarTester2 {

	private GrammarBook grammarBook;
	private int numberOfExamplesQuestioned = 0;
	private final GrammarAnswerDataContainer userAnswers = new GrammarAnswerDataContainer();
	private Example actualQuestionedExample;
	//private String userAnswerToActualQuestion; 
	/*private Map<String, Integer> acceptabelAnswersAndCardIndexesForActualQuestion;
	private boolean isGetAnswerToActualQuestion = false;*/
    
        private Vector<Integer> exampleIndexesToTest;
	private final Logger logger = new Logger();
        private int practicedGrammarItemIndex;
        
        public void setGrammarBook(GrammarBook gb) {
            grammarBook = gb;
        }
        
        public void setPracticedGrammarItemIndex(int a) {
            practicedGrammarItemIndex = a;
        }
        
        public void setExampleIndexesToTest(int numberOfExamples) {

		Vector<Integer> allExampleIndex 
                        = new Vector<>(grammarBook.getGrammarItemByIndex(practicedGrammarItemIndex).getExampleIndexes());

                java.util.Collections.shuffle(allExampleIndex);
		
                for (int i=0; i<numberOfExamples; i++) {
                    exampleIndexesToTest.add(allExampleIndex.elementAt(i));
                }
	}

	/*public GrammarTester2() {
		numberOfCardsQuestioned = 0;
	}*/

	/*public void setAllCard(CardContainer cc) {
		allCard = cc;
	}

	public void setCardsToTest(CardContainer cc) {
		cardsToTest = cc;
	}*/
        
        //before allCard need to be seted
        /*public void setCardsToTestFromCardIndexesSet(Set<Integer> cardIndexes) {
            List<Integer> cardIndexesList = new Vector<Integer>(cardIndexes);
            java.util.Collections.shuffle(cardIndexesList);
            
            cardsToTest = new CardContainer();
            for (int index : cardIndexesList) {
                cardsToTest.addCard(allCard.getCardByIndex(index));
            }
        }*/
        
        /*public CardContainer getCardsToTest() {
            return cardsToTest;
        }*/

	public void moveToNextExampleToQuestion() {
		actualQuestionedExample 
                        = grammarBook.getExample(practicedGrammarItemIndex, numberOfExamplesQuestioned);

		numberOfExamplesQuestioned++;

		logger.debug("questioned example: " + actualQuestionedExample.toString());
	}
        
        public void userAnswerAccepted() {
            long date = new Date().getTime();
            
            GrammarAnswerData actualAnswerData = new GrammarAnswerData();
            actualAnswerData.date = date;
            actualAnswerData.index = practicedGrammarItemIndex;
            actualAnswerData.exampleIndex = actualQuestionedExample.index;
            actualAnswerData.isRight = true;
            
            userAnswers.addAnswerData(actualAnswerData);
            
            logger.debug("added answer data: " + actualAnswerData.toString());
        }

        public void userAnswerIgnored() {
            logger.debug("user answer ignored");
        }
        
        public void userAnswerRejected() {
            long date = new Date().getTime();
            
            GrammarAnswerData actualAnswerData = new GrammarAnswerData();
            actualAnswerData.date = date;
            actualAnswerData.index = practicedGrammarItemIndex;
            actualAnswerData.exampleIndex = actualQuestionedExample.index;
            actualAnswerData.isRight = true;
            
            userAnswers.addAnswerData(actualAnswerData);
            
            logger.debug("added answer data: " + actualAnswerData.toString());
        }
        
	public Example getActualQuestionedExample() {
		return actualQuestionedExample;
	}

/*	public boolean isUserAnswerRight() {
		return acceptabelAnswersAndCardIndexesForActualQuestion.containsKey(userAnswerToActualQuestion);
	}*/

	public boolean isMoreExamplesToQuestion() {
		return numberOfExamplesQuestioned != getNumberOfQuestions();
	}

	public AnswerDataContainer getUserAnswers() {
		return userAnswers;
	}

	public int getNumberOfQuestions() {
		return exampleIndexesToTest.size();
	}

	public int numberOfCardsQuestioned() {
		return numberOfExamplesQuestioned;
	}

}