package grammar_book;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import study_item_objects.AnswerDataByStudyItem;
import study_item_objects.AnswerDataContainer;

public class GrammarTestStatisticsMaker {
    
    public GrammarItem testedGrammarItem;
    public AnswerDataContainer testAnswers;
    public AnswerDataContainer oldAnswers;
    
    public long startTime;
    public long finishTime;
    
    public int getregisteredAnswers() {
        return testAnswers.numberOfAnswers();
    }
    
    public int getNumberOfAnswersAfterTest() {
        return testAnswers.numberOfAnswers() + oldAnswers.numberOfAnswers();
    }
    
    public String getPercentageOfRightAnswers() {
        int numberOfRightAnswers = 0;
        for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                if (testAnswers.getAnswerData(i).isRight) numberOfRightAnswers++;
        }
        double percentage = (double)numberOfRightAnswers * 100.0 / (double)testAnswers.numberOfAnswers();
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(percentage) + "%";
    }
    
    public double getGrammarItemRightAnswerRateBeforeTest() {
	AnswerDataByStudyItem answerDataByStudyItem = new AnswerDataByStudyItem();
        answerDataByStudyItem.loadDataFromAnswerDataContainer(testedGrammarItem.index, oldAnswers);
        return answerDataByStudyItem.countRightAnswerRate();
    }
    
    public double getGrammarItemRightAnswerRateAfterTest() {
	AnswerDataByStudyItem answerDataByStudyItem = new AnswerDataByStudyItem();
        
        AnswerDataContainer allAnswers = new AnswerDataContainer();
        allAnswers.appendAnswerDataContainer(oldAnswers);
        allAnswers.appendAnswerDataContainer(testAnswers);
        
        answerDataByStudyItem.loadDataFromAnswerDataContainer(testedGrammarItem.index, allAnswers);
        
        return answerDataByStudyItem.countRightAnswerRate();
    }   
    
    public String gesUsedTimeAsString() {
        Date date = new Date(finishTime - startTime);
	DateFormat formatter = new SimpleDateFormat("mm:ss");        
	String dateFormatted = formatter.format(date);
        
        return dateFormatted;
    }
}
