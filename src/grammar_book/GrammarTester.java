package grammar_book;

import java.util.Date;
import language_studyer.StudyItemTester;

public class GrammarTester extends StudyItemTester {

    private Example actualQuestionedExample = new Example();

    @Override
    public void moveToNextStudyItemToQuestion() {
        actualQuestionedStudyItem 
                = studyItemsToTest.getStudyItemByOrder(numberOfItemsQuestioned);
        numberOfItemsQuestioned++;
        
        actualQuestionedExample = this.studyItemChooser
                .getRandomExample((GrammarItem) actualQuestionedStudyItem);

        userAnswerToActualQuestion = "";
        isGetAnswerToActualQuestion = false;

        logger.debug("questioned grammar item: " 
                + ((GrammarItem) actualQuestionedStudyItem).title.toString());

    }

    public void userAnswerAccepted() {
        long date = new Date().getTime();

        GrammarAnswerData actualAnswerData = new GrammarAnswerData();
        actualAnswerData.date = date;
        actualAnswerData.index = actualQuestionedStudyItem.index;
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
        actualAnswerData.index = actualQuestionedStudyItem.index;
        actualAnswerData.exampleIndex = actualQuestionedExample.index;
        actualAnswerData.isRight = false;

        userAnswers.addAnswerData(actualAnswerData);

        logger.debug("added answer data: " + actualAnswerData.toStringData());
    }

    public Example getActualQuestionedExample() {
        return actualQuestionedExample;
    }

    public GrammarItem getActualQuestionedGrammarItem() {
        return (GrammarItem)this.actualQuestionedStudyItem;
    }

}
