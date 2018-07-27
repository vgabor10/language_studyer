package language_studyer;

import common.Logger;
import java.util.Date;
import java.util.List;

public class StudyItemTester {
    
    protected StudyItemChooser studyItemChooser = new StudyItemChooser();
    
    protected StudyItemContainer allStudyItem;
    protected StudyItemContainer studyItemsToTest = new StudyItemContainer();
    protected int numberOfItemsQuestioned = 0;
    protected final AnswerDataContainer userAnswers = new AnswerDataContainer();
    protected StudyItem actualQuestionedStudyItem;
    protected String userAnswerToActualQuestion;
    protected boolean isGetAnswerToActualQuestion = false;
    
    protected final Logger logger = new Logger();

    public void startNewTest() {
        numberOfItemsQuestioned = 0;
        userAnswers.clear();
        studyItemsToTest.clear();
        actualQuestionedStudyItem = null;
        userAnswerToActualQuestion = null;
        isGetAnswerToActualQuestion = false;
        
        List<Integer> studyItemIndexesToTest = studyItemChooser.getStudyItemIndexes();
        for (int studyItemIndex : studyItemIndexesToTest) {
            studyItemsToTest.addStudyItem(
                    allStudyItem.getStudyItemByIndex(studyItemIndex));
        }
        
        moveToNextStudyItemToQuestion();
    }
    
    public void moveToNextStudyItemToQuestion() {
    }
    
    public void setData(DataContainer dataContainer) {
        this.allStudyItem = dataContainer.getStudyItemContainer();
        
        dataContainer.updateStudiedData();
        this.studyItemChooser.setData(dataContainer);
    }

    public StudyItemContainer getStudyItemsToTest() {
        return studyItemsToTest;
    }

    protected StudyItem getActualQuestionedStudyItem() {
        return actualQuestionedStudyItem;
    }

    public void setUserAnswer(String answer) {
        userAnswerToActualQuestion = answer;

        isGetAnswerToActualQuestion = true;

        logger.debug("user answer: " + answer);

    }
    
    public void rejectUserAnswer() {
        Date date = new Date();
        userAnswers.addElement(actualQuestionedStudyItem.index, false, date.getTime());

        logger.debug("added answer data: " + actualQuestionedStudyItem.index + ", false");        
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

    public boolean isMoreStudyItemsToTest() {
        return numberOfItemsQuestioned != studyItemsToTest.numberOfStudyItems();
    }

    public AnswerDataContainer getUserAnswers() {
        return userAnswers;
    }

    public int getNumberOfQuestions() {
        return studyItemsToTest.numberOfStudyItems();
    }

    public int numberOfStudyItemsQuestioned() {
        return numberOfItemsQuestioned;
    }

}
