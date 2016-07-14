package disc_operation_handlers;

import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;
import grammar_book.GrammarItem;
import java.io.FileWriter;
import java.io.IOException;

public class GrammarDataModificator {

    private GrammarBook grammarBook;
    private GrammarAnswerDataContainer grammarAnswerDataContainer;
        
    public LanguageFilesDataHendler languageFilesDataHendler;

    public void setGrammarBook(GrammarBook gb) {
            grammarBook = gb;
    }

    public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer gac) {
            grammarAnswerDataContainer = gac;
    }       

    public void writeGrammarBookToDisk(String filePath) {   //TODO: implement
        try {
            FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data

            fw.write(grammarBook.preambulum + "\n");
            fw.write("\\begin{document}\n\n");
            fw.write("\\maketitle\n\n");
            fw.write("\\tableofcontents\n\n");

            //int hierarcyDepth = 0;

            for (int orderIndex =0; orderIndex<grammarBook.numberOfGrammarItems(); orderIndex++) {

                GrammarItem grammarItem = grammarBook.getGrammarItemByOrder(orderIndex);
                    
                    
                    
                    /*String categorys[] = grammarBook.getGrammarItem(index).title.split("/");
                    for (int i=hierarcyDepth; i<categorys.length-1; i++) {
                        if (i == 0) {
                            fw.write("\\section{" + categorys[i] + "}\n\n");
                        }
                        if (i == 1) {
                            fw.write("\\subsection{" + categorys[i] + "}\n\n");
                        }
                        if (i == 2) {
                            fw.write("\\subsubsection{" + categorys[i] + "}\n\n");
                        }
                    }
                    hierarcyDepth = categorys.length - 1;*/

                fw.write(grammarItem.toStringInLatexFormat());
                fw.write("\n");
            }

            fw.write("\\end{document}\n");

            fw.close();
        }
        catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void writeGrammarAnswerDataToDisk() {

        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();
        
        try {
            FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data
            for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
                fw.write(grammarAnswerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        }
        catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    public void appendGrammarAnswerDataToFile() {
        String filePath = languageFilesDataHendler.getStudiedLanguageGrammarAnswerDataPath();
        try {
            FileWriter fw = new FileWriter(filePath,true);	//the true will append the new data
            for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
                fw.write(grammarAnswerDataContainer.getAnswerData(i).toStringData() + "\n");	//appends the string to the file
            }
            fw.close();
        }
        catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    //TODO: implement
    public void deleteGrammarItem(int grammarItemIndex) {
            /*grammarBook.deleteGrammarItem(grammarItemIndex);
            for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
                    if (grammarAnswerDataContainer.data.get(i).grammarItemIndex == grammarItemIndex) {
                            grammarAnswerDataContainer.data.remove(i);
                    }
            }
            writeGrammarBookToDisk();
            writeGrammarAnswerDataToDisk();*/
    }

    //TODO: implenet
    public void deleteExample(int grammarItemIndex, int exampleIndex) {
    }
}
