package grammar_book;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import language_studyer.AnswerDataByStudyItem;
import language_studyer.AnswerDataContainer;

public class GrammarTestStatisticsMaker {
    
    public GrammarItem testedGrammarItem;
    public AnswerDataContainer testAnswers;
    public AnswerDataContainer oldAnswers;
    
    public long startTime;
    public long finishTime;
    
    //private AnswerDataByStudyItem answerDataByStudyItemBeforeTest = new AnswerDataByStudyItem();
    //private AnswerDataByStudyItem answerDataByStudyItemAfterTest = new AnswerDataByStudyItem();
    
    public void makePreProcessing() {
        AnswerDataContainer answerDataContainer = new AnswerDataContainer();
        answerDataContainer.appendAnswerDataContainer(oldAnswers);
        
        /*answerDataByStudyItemBeforeTest.loadDataFromAnswerDataContainer(
                testedGrammarItem.index, answerDataContainer);
        
        answerDataContainer.appendAnswerDataContainer(testAnswers);
        
        answerDataByStudyItemAfterTest.loadDataFromAnswerDataContainer(
                testedGrammarItem.index, answerDataContainer);*/
    }
    
    public int getNumberOfRegisteredAnswers() {
        return testAnswers.numberOfAnswers();
    }
    
    /*public int getNumberOfAnswersAfterTest() {
        return answerDataByStudyItemAfterTest.numberOfAnswers();
    }*/
    
    public String getPercentageOfRightAnswersAsString() {
        int numberOfRightAnswers = 0;
        for (int i=0; i<testAnswers.numberOfAnswers(); i++) {
                if (testAnswers.getAnswerData(i).isRight) numberOfRightAnswers++;
        }
        double percentage = (double)numberOfRightAnswers * 100.0 / (double)testAnswers.numberOfAnswers();
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(percentage) + "%";
    }
    
    /*public double getGrammarItemRightAnswerRateBeforeTest() {
       return answerDataByStudyItemBeforeTest.countRightAnswerRate();
    }*/
    
    /*public String getGrammarItemRightAnswerRateBeforeTestAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        double rar = getGrammarItemRightAnswerRateBeforeTest();
        return df.format(rar);
    }*/
    
   /* public double getGrammarItemRightAnswerRateAfterTest() {
       return answerDataByStudyItemAfterTest.countRightAnswerRate();
    }*/
    
    /*public String getGrammarItemRightAnswerRateAfterTestAsString() {
        DecimalFormat df = new DecimalFormat("#.0000");
        double rar = getGrammarItemRightAnswerRateAfterTest();
        return df.format(rar);
    }*/
    
    public String gesUsedTimeAsString() {
        Date date = new Date(finishTime - startTime);
	DateFormat formatter = new SimpleDateFormat("mm:ss");        
	String dateFormatted = formatter.format(date);
        
        return dateFormatted;
    }
}
