package grammar_book;

import java.io.FileWriter;
import java.io.IOException;
import language_studyer.AnswerDataContainer;

public class DataModificator {

    private GrammarItemContainer grammarBook;
    private AnswerDataContainer answerDataContainer;

    private DiscFilesMetaDataHandler languageFilesDataHendler
            = new DiscFilesMetaDataHandler();

    public void setData(GrammarItemContainer gb) {
        grammarBook = gb;
    }

    public void setGrammarAnswerDataContainer(AnswerDataContainer gac) {
        answerDataContainer = gac;
    }

    //TODO: can be done more safe: first write new file, remove old file, rename new file
    public void writeGrammarBookToDisk() {
        try {
            String filePath = languageFilesDataHendler.getStudiedLanguageGrammarBookPath();
            
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);

            for (int orderIndex = 0; orderIndex < grammarBook.numberOfGrammarItems(); orderIndex++) {

                GrammarItem grammarItem = grammarBook.getGrammarItemByOrder(orderIndex);

                fw.write(grammarItem.toString());
                fw.write("\n");
            }

            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void writeGrammarAnswerDataToDisk() {

        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();

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
            
            String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();
            FileWriter fw = new FileWriter(filePath, true);	//the true will append the new data
            for (int i = 0; i < gadc.numberOfAnswers(); i++) {
                fw.write(gadc.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void deleteGrammarItemByIndex(int grammarItemIndex) {
        grammarBook.removeByIndex(grammarItemIndex);
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
