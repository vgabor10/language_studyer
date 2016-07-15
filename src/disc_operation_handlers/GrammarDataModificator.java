package disc_operation_handlers;

import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;
import grammar_book.GrammarItem;
import grammar_book.GrammarItemTitle;
import java.io.FileWriter;
import java.io.IOException;

public class GrammarDataModificator {

    private GrammarBook grammarBook;
    private GrammarAnswerDataContainer grammarAnswerDataContainer;

    private LanguageFilesDataHendler languageFilesDataHendler;

    public void setGrammarBook(GrammarBook gb) {
        grammarBook = gb;
    }

    public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer gac) {
        grammarAnswerDataContainer = gac;
    }

    public void setLanguageFilesDataHendler(LanguageFilesDataHendler a) {
        languageFilesDataHendler = a;
    }
    
    //TODO: can be done more safe: first write new file, remove old file, rename new file
    public void writeGrammarBookToDisk() {
        try {
            String filePath = languageFilesDataHendler.getStudiedLanguageGrammarBookPath();
            
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data

            fw.write(grammarBook.preambulum + "\n");
            fw.write("\\begin{document}\n\n");
            fw.write("\\maketitle\n\n");
            fw.write("\\tableofcontents\n\n");

            GrammarItemTitle lastGrammarItemTitle = new GrammarItemTitle();
            GrammarItemTitle actualGrammarItemTitle;

            for (int orderIndex = 0; orderIndex < grammarBook.numberOfGrammarItems(); orderIndex++) {

                GrammarItem grammarItem = grammarBook.getGrammarItemByOrder(orderIndex);
                actualGrammarItemTitle = grammarItem.title;

                fw.write(actualGrammarItemTitle.getInLatexFormatAfterGivenTitle(lastGrammarItemTitle));
                fw.write(grammarItem.toStringInLatexFormatWithoutTitle());
                fw.write("\n");

                lastGrammarItemTitle = actualGrammarItemTitle;
            }

            fw.write("\\end{document}\n");

            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void writeGrammarAnswerDataToDisk() {

        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();

        try {
            FileWriter fw = new FileWriter(filePath, false);	//the true will append the new data
            for (int i = 0; i < grammarAnswerDataContainer.numberOfAnswers(); i++) {
                fw.write(grammarAnswerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void appendGrammarAnswerDataToFile() {
        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();
        try {
            FileWriter fw = new FileWriter(filePath, true);	//the true will append the new data
            for (int i = 0; i < grammarAnswerDataContainer.numberOfAnswers(); i++) {
                fw.write(grammarAnswerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void deleteGrammarItemByIndex(int grammarItemIndex) {
        grammarBook.removeByIndex(grammarItemIndex);
        grammarAnswerDataContainer.removeAnswerDataWithIndex(grammarItemIndex);
        
        writeGrammarBookToDisk();
        writeGrammarAnswerDataToDisk();
    }

    //TODO: implenet
    public void deleteExample(int grammarItemIndex, int exampleIndex) {
    }
}
