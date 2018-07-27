package language_studyer;

import common.Logger;
import java.io.FileWriter;
import java.io.IOException;

public class DataModificator {
    
    protected final Logger logger = new Logger();
    
    protected DataContainer dataContainer;
    protected AnswerDataContainer answerDataContainer;

    protected DiscFilesMetaDataHandler discFilesMetaDataHandler;
    
    public void setData(DataContainer dc) { //TODO: remove this method
        dataContainer = dc;
        answerDataContainer = dc.getAnswerDataContainer();
    }
    
    protected DataContainer getDataContainer() {
        return dataContainer;
    }
    
    public void setDiscFilesMetaDataHandler(DiscFilesMetaDataHandler dfmdh) {
        discFilesMetaDataHandler = dfmdh;
    }

    public void setAnswerDataContainer(AnswerDataContainer adc) {
        answerDataContainer = adc;
    }
    
    protected void writeStudyStrategyDataToDisc(String filePath) {
        try {
            
            StudyStrategy studyStrategy = dataContainer.getStudyStrategy();
            
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);
            
            fw.write("numberOfRandomCards: " +
                    Integer.toString(studyStrategy.numberOfRandomItems) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown20Percent: "
                    + Integer.toString(studyStrategy.numberOfItemsFromTheLeastKnown20Percent) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown100: "
                    + Integer.toString(studyStrategy.numberOfItemsFromTheLeastKnown100) + "\n");
            
            fw.write("numberOfCardsWithLeastSignificantAr: "
                    + Integer.toString(studyStrategy.numberOfItemsAmongTheLeastSignificantAr) + "\n");

            fw.write("numberOfLatestQuestionedCards: "
                    + Integer.toString(studyStrategy.numberOfLatestQuestionedItems) + "\n");
            
            fw.write("studyingGradually: "
                    + Boolean.toString(studyStrategy.studyingGradually) + "\n");
            
            fw.write("cardCategoryRestrictions:");
            for (int categoryIndex : studyStrategy.cardCategoryRestrictions) {
                fw.write(" " + categoryIndex);
            }
            fw.write("\n");
            
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }           
    }
}
