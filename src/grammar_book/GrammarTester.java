package grammar_book;

import common.Logger;
import java.util.Date;
import java.util.Vector;

public class GrammarTester {

	private int numberOfExamplesQuestioned = 0;
	private final GrammarAnswerDataContainer userAnswers = new GrammarAnswerDataContainer();
	private Example actualQuestionedExample;
        private GrammarItem actualTestedGrammarItem;
    
        private Vector<Integer> exampleIndexesToTest = new Vector<>();;
	private final Logger logger = new Logger();
        
        public void setActualTestedGrammarItem(GrammarItem gi) {
            actualTestedGrammarItem = gi;
        }
        
        public void setExampleIndexesToTest(int numberOfExamples) {

		Vector<Integer> allExampleIndex 
                        = new Vector<>(actualTestedGrammarItem.getExampleIndexes());
                
                java.util.Collections.shuffle(allExampleIndex);
		
                for (int i=0; i<numberOfExamples; i++) {
                    exampleIndexesToTest.add(allExampleIndex.get(i));
                }
	}

	public void moveToNextExampleToQuestion() {
                int actualExampleIndex = exampleIndexesToTest.get(numberOfExamplesQuestioned);
		actualQuestionedExample 
                        = actualTestedGrammarItem.getExampleByIndex(actualExampleIndex);

		numberOfExamplesQuestioned++;

		logger.debug("questioned example: " + actualQuestionedExample.toString());
	}
        
        public void userAnswerAccepted() {
            long date = new Date().getTime();
            
            GrammarAnswerData actualAnswerData = new GrammarAnswerData();
            actualAnswerData.date = date;
            actualAnswerData.index = actualTestedGrammarItem.index;
            actualAnswerData.exampleIndex = actualQuestionedExample.index;
            actualAnswerData.isRight = true;
            
            userAnswers.addAnswerData(actualAnswerData);
            
            logger.debug("added answer data: " + actualAnswerData.toStringData());
        }

        public void userAnswerIgnored() {
            logger.debug("user answer ignored");
        }
        
        public void userAnswerRejected() {
            long date = new Date().getTime();
            
            GrammarAnswerData actualAnswerData = new GrammarAnswerData();
            actualAnswerData.date = date;
            actualAnswerData.index = actualTestedGrammarItem.index;
            actualAnswerData.exampleIndex = actualQuestionedExample.index;
            actualAnswerData.isRight = false;
            
            userAnswers.addAnswerData(actualAnswerData);
            
            logger.debug("added answer data: " + actualAnswerData.toStringData());
        }
        
	public Example getActualQuestionedExample() {
		return actualQuestionedExample;
	}

        public GrammarItem getActualQuestionedGrammarItem() {
		return actualTestedGrammarItem;
	}

	public boolean isMoreExamplesToQuestion() {
		return numberOfExamplesQuestioned != getNumberOfQuestions();
	}

	public GrammarAnswerDataContainer getUserAnswers() {
		return userAnswers;
	}

	public int getNumberOfQuestions() {
		return exampleIndexesToTest.size();
	}

	public int numberOfExamplesQuestioned() {
		return numberOfExamplesQuestioned;
	}

}
