package disc_operation_handlers;

import grammar_book.GrammarAnswerDataContainer;
import grammar_book.GrammarBook;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrammarDataModificator {

	private GrammarBook grammarBook;
	private	GrammarAnswerDataContainer grammarAnswerDataContainer;
	public String grammarBookSourcePath = "";
	public String grammarAnswerDataSourcePath = "";

	public void setGrammarBook(GrammarBook gb) {
		grammarBook = gb;
	}

	public void setGrammarAnswerDataContainer(GrammarAnswerDataContainer gac) {
		grammarAnswerDataContainer = gac;
	}

	public void writeGrammarBookToDisk(String filePath) {   //TODO: implement
            /*try {
                FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data

                fw.write(grammarBook.preambulum + "\n");
                fw.write("\\begin{document}\n\n");
                fw.write("\\maketitle\n\n");
                fw.write("\\tableofcontents\n\n");

                int hierarcyDepth = 0;

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
                    hierarcyDepth = categorys.length - 1;

                    fw.write(grammarBook.getGrammarItem(index).toStringInLatexFormat());
                    fw.write("\n");
                }

                fw.write("\\end{document}\n");

                fw.close();
            }
            catch(IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());
            }*/
	}

	public void writeGrammarAnswerDataToDisk() {
		String answerdDataFolderPath = grammarAnswerDataSourcePath.substring(0,grammarAnswerDataSourcePath.lastIndexOf('/'));
		grammarAnswerDataContainer.saveDataToFile(answerdDataFolderPath + "/temporary_answerd_data_file.txt");
	}

	public void writeGrammarBookToDisk() {
		String grammarBookSourcePath = grammarAnswerDataSourcePath.substring(0,grammarAnswerDataSourcePath.lastIndexOf('/'));
		grammarAnswerDataContainer.saveDataToFile(grammarBookSourcePath + "/temporary_grammar_book_file.txt");
	}

	public void deleteGrammarItem(int grammarItemIndex) {   //TODO: implement
		/*grammarBook.deleteGrammarItem(grammarItemIndex);
		for (int i=0; i<grammarAnswerDataContainer.numberOfAnswers(); i++) {
			if (grammarAnswerDataContainer.data.get(i).grammarItemIndex == grammarItemIndex) {
				grammarAnswerDataContainer.data.remove(i);
			}
		}
		writeGrammarBookToDisk();
		writeGrammarAnswerDataToDisk();*/
	}

	public void deleteExample(int grammarItemIndex, int exampleIndex) { //TODO: implenet
	}
}
