package grammar_book;

import common.Logger;
import java.util.Date;
import java.util.Random;

public class GrammarTester {    //TODO: maybe do a StudyItemTester ősosztály

    private final Random randomGenerator = new Random();    //should be taken to other class
    
    private DataContainer dataContainer;
    
    private GrammarItemChooser grammarItemChooser = new GrammarItemChooser();
    
    private int numberOfExamplesQuestioned = 0;
    private int numberOfExamplesToQuestion = 10;
    private final GrammarAnswerDataContainer userAnswers = new GrammarAnswerDataContainer();
    private Example actualQuestionedExample;
    private GrammarItem actualTestedGrammarItem;

    //private List<Integer> exampleIndexesToTest = new ArrayList<>();
    
    private final Logger logger = new Logger();
    
    public void setData(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
        dataContainer.fillAuxiliaryDataContainer();
        this.grammarItemChooser.setData(dataContainer);
    }
    
    public void startNewTest() {
        numberOfExamplesQuestioned = 0;
        userAnswers.clear();
        //exampleIndexesToTest.clear();
        actualQuestionedExample = null;
        
        //exampleIndexesToTest = new ArrayList(actualTestedGrammarItem.getExampleIndexes());
        /*Collections.shuffle(exampleIndexesToTest);
        
        while (10<exampleIndexesToTest.size()) {
            exampleIndexesToTest.remove(10);
        }*/
    }

    public void moveToNextExampleToQuestion() {
        /*int actualExampleIndex = exampleIndexesToTest.get(numberOfExamplesQuestioned);
        actualQuestionedExample
                = actualTestedGrammarItem.getExampleByIndex(actualExampleIndex);*/

        int testedGrammarItemIndex = grammarItemChooser.getRandomGrammarItemIndex();
        actualTestedGrammarItem = dataContainer.grammarItemContainer.getByIndex(testedGrammarItemIndex);
        int actualExampleIndex = randomGenerator.nextInt(actualTestedGrammarItem.numberOfExamples())+1;
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
        //return numberOfExamplesQuestioned != getNumberOfQuestions();
        return numberOfExamplesQuestioned != numberOfExamplesToQuestion;
    }

    public GrammarAnswerDataContainer getUserAnswers() {
        return userAnswers;
    }

    public int getNumberOfQuestions() {
        return numberOfExamplesToQuestion;
    }

    public int numberOfExamplesQuestioned() {
        return numberOfExamplesQuestioned;
    }

}
