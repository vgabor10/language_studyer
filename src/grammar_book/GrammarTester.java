package grammar_book;

import common.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrammarTester {    //TODO: maybe do a StudyItemTester ősosztály

    private DataContainer dataContainer;
    
    private GrammarItemChooser grammarItemChooser = new GrammarItemChooser();
    
    private int numberOfExamplesQuestioned = 0;
    private final GrammarAnswerDataContainer userAnswers = new GrammarAnswerDataContainer();
    private Example actualQuestionedExample;
    private GrammarItem actualTestedGrammarItem;

    private List<Integer> exampleIndexesToTest = new ArrayList<>();
    
    private final Logger logger = new Logger();

    public void setData(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
        dataContainer.fillAuxiliaryDataContainer();
        this.grammarItemChooser.setData(dataContainer);
    }
    
    public void startNewTest() {
        numberOfExamplesQuestioned = 0;
        userAnswers.clear();
        exampleIndexesToTest.clear();
        actualQuestionedExample = null;
        
        int testedGrammarItemIndex = grammarItemChooser.getGrammarItemIndexForTest1();
        actualTestedGrammarItem = dataContainer.grammarItemContainer.getByIndex(testedGrammarItemIndex);
        exampleIndexesToTest = new ArrayList(actualTestedGrammarItem.getExampleIndexes()); 
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
