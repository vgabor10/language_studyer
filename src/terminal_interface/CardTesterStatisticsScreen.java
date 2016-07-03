package terminal_interface;

import study_item_objects.AnswerDataByStudyItemsContainer;
import study_item_objects.AnswerDataContainer;
import dictionary.CardContainer;
import dictionary.CardTestStatisticsMaker;
import java.io.Console;

public class CardTesterStatisticsScreen {

    private AnswerDataContainer oldAnswers;
    private AnswerDataContainer userAnswers;
    private CardContainer cardContainer;
    private long startTime = 0;
    private long finishTime = 0;
    
    Console console = System.console();
    
    public void setOldAnswers(AnswerDataContainer oa) {
        oldAnswers = oa;
    }
    
    public void setUserAnswers(AnswerDataContainer ua) {
        userAnswers = ua;
    }
        
    public void setCardContainer(CardContainer cc) {
        cardContainer = cc;
    }
    
    public void setStartAndFinishTime(long st, long ft) {
        startTime = st;
        finishTime = ft;
    }
    
    public void toScreenStatistics() {
        
        AnswerDataByStudyItemsContainer answerDataByStudyItemsContainerBeforeTest
            = new AnswerDataByStudyItemsContainer();
        answerDataByStudyItemsContainerBeforeTest.loadDataFromAnswerDataContainer(oldAnswers);
                        
        
        AnswerDataByStudyItemsContainer answerDataByStudyItemsContainerAfterTest
            = new AnswerDataByStudyItemsContainer();
        answerDataByStudyItemsContainerAfterTest.loadDataFromAnswerDataContainer(oldAnswers);
                        answerDataByStudyItemsContainerAfterTest.addDataFromAnswerDataContainer(userAnswers);
                        
        CardTestStatisticsMaker cardTestStatisticsMaker = new CardTestStatisticsMaker();
        cardTestStatisticsMaker.setAnswerDatasByStudyItemsAfterTest(answerDataByStudyItemsContainerAfterTest);
        cardTestStatisticsMaker.setAnswerDatasByStudyItemsBeforeTest(answerDataByStudyItemsContainerBeforeTest);
        cardTestStatisticsMaker.setCardContainer(cardContainer);
        cardTestStatisticsMaker.setTestAnswers(userAnswers);
        cardTestStatisticsMaker.setStartAndEndTime(startTime, finishTime);
        cardTestStatisticsMaker.toScreenStatistics();
        
        console.readLine();
    }
}
