package grammar_book;

import language_studyer.DataModificator;
import java.io.FileWriter;
import java.io.IOException;
import language_studyer.AnswerDataContainer;

public class GrammarDataModificator extends DataModificator {

    private GrammarDataContainer getGrammarDataContainer() {
        return (GrammarDataContainer) this.dataContainer;
    }
    
    //TODO: can be done more safe: first write new file, remove old file, rename new file
    public void writeGrammarBookToDisk() {
        try {
            String filePath = discFilesMetaDataHandler.getStudiedLanguageGrammarBookPath();
            
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);

            for (int orderIndex = 0; orderIndex 
                    < getGrammarDataContainer().getGrammarItemContainer()
                            .numberOfStudyItems(); orderIndex++) {

                GrammarItem grammarItem 
                        = getGrammarDataContainer().getGrammarItemContainer()
                                .getGrammarItemByOrder(orderIndex);

                fw.write(grammarItem.toString());
                fw.write("\n");
            }

            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void writeGrammarAnswerDataToDisk() {

        String filePath = discFilesMetaDataHandler.getStudiedLanguageGrammarAnswerDataPath();

        logger.debug("start to write grammar answer data to file " + filePath);
        
        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < answerDataContainer.numberOfAnswers(); i++) {
                fw.write(answerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void appendGrammarAnswerData(AnswerDataContainer gadc) {
        try {
            answerDataContainer.appendAnswerDataContainer(gadc);
            
            String filePath = discFilesMetaDataHandler.getStudiedLanguageGrammarAnswerDataPath();
            FileWriter fw = new FileWriter(filePath, true);	//the true will append the new data
            for (int i = 0; i < gadc.numberOfAnswers(); i++) {
                fw.write(gadc.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void writeGrammarStudyStrategyDataToDisc() {
        String filePath 
                = discFilesMetaDataHandler.getStudiedLanguageGrammarStudyStrategyPath();
        
        writeStudyStrategyDataToDisc(filePath);
    }
    
    public void deleteGrammarItemByIndex(int grammarItemIndex) {
        getGrammarDataContainer().getGrammarItemContainer()
                .removeByIndex(grammarItemIndex);
        answerDataContainer.removeAnswerDataWithIndex(grammarItemIndex);
        writeDataToDisc();
    }
    
    public void writeDataToDisc() {
        writeGrammarBookToDisk();
        writeGrammarAnswerDataToDisk();
    }
    
    //TODO: implenet
    public void deleteExample(int grammarItemIndex, int exampleIndex) {
    }
}
