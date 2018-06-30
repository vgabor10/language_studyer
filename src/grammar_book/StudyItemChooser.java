package grammar_book;

import common.Logger;
import java.util.Random;
import java.util.Set;
import language_studyer.AnswerDataByStudyItemContainer;

public class StudyItemChooser {

    protected Set<Integer> studyItemIndexesFromChoose;
    protected StudyStrategy studyStrategy;
    
    private AnswerDataByStudyItemContainer answerDataByStudyItemsContainer 
            = new AnswerDataByStudyItemContainer();;
    
    private final Random randomGenerator = new Random();
    private final Logger logger = new Logger();
    
    public void setData(DataContainer dataContainer) {
        studyStrategy = dataContainer.studyStrategy;
        studyItemIndexesFromChoose = dataContainer.auxiliaryDataContainer.studiedCardIndexes;
        answerDataByStudyItemsContainer = dataContainer.auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer;
    }
    
}
