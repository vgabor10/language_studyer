package grammar_book;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import language_studyer.AnswerDataContainer;

public class GrammarBookFileFormatChecker {

    private GrammarItemContainer grammarItemContainer;
    private AnswerDataContainer grammarAnswerDataContainer;

    public void setData(GrammarDataContainer  grammarDataContainer) {
        grammarItemContainer = grammarDataContainer.getGrammarItemContainer();
        grammarAnswerDataContainer = grammarDataContainer.getAnswerDataContainer();
    }

    public void isThereAnswerDataWithInvalidGrammarItemIndex() {	//TODO: take it to an other class
        Set<Integer> grammarItemIndexes = grammarItemContainer.getGrammarItemIndexes();

        int i = 0;
        while (i < grammarAnswerDataContainer.numberOfAnswers()
                && grammarItemIndexes.contains(grammarAnswerDataContainer.getAnswerData(i).index)) {
            i++;
        }

        if (i == grammarAnswerDataContainer.numberOfAnswers()) {
            System.out.println("there is NOT answer data with invalid grammar item index");
        } else {
            System.out.println("answer data file contains answer data with invalid grammar item index, an invalid index is: "
                    + grammarAnswerDataContainer.getAnswerData(i).index);
        }
    }

    public void isThereDuplicationInGrammarItemIndexes() {
        Set<Integer> grammarItemIndexes = new HashSet<>();
        boolean isCorrect = true;
        
        int i = 0;
        while (i < grammarItemContainer.numberOfGrammarItems()) {
            int grammarItemIndex = grammarItemContainer.getGrammarItemByOrder(i).index;
            if (grammarItemIndexes.contains(grammarItemIndex)) {
                System.out.println("error: duplications in GrammarItemIndexes");
                isCorrect = false;
            } else {
                grammarItemIndexes.add(grammarItemIndex);
            }
            i++;
        }    
        
        if (isCorrect) {
            System.out.println("there is NO duplications in GrammarItemIndexes");
        }
        
    }

    /*public boolean checkExistanceOfGrammarItemIndexes(String filePath) {
        BufferedReader br = null;
        String strLine = "";
        boolean isCorrect = true;

        try {

            br = new BufferedReader(new FileReader(filePath));
            int lineNumber = 0;
            boolean isContent = false;
            boolean isGrammarItemIndex = false;

            strLine = br.readLine();
            while (!strLine.startsWith("\\section{")
                    && !strLine.startsWith("\\subsection{")
                    && !strLine.startsWith("\\subsubsection{")) {
                strLine = br.readLine();
                lineNumber++;
            }

            do {

                if (strLine != null) {

                    if (strLine.startsWith("\\section{")
                            || strLine.startsWith("\\subsection{")
                            || strLine.startsWith("\\subsubsection{")) {

                        if (isContent && !isGrammarItemIndex) {
                            System.out.println("error: missing grammarItemIndex at line " + lineNumber);
                            isCorrect = false;
                        }

                        isContent = false;
                        isGrammarItemIndex = false;
                    } else if (strLine.startsWith("GrammarItemIndex")) {
                        isGrammarItemIndex = true;
                    } else if (!strLine.equals("")) {
                        isContent = true;
                    }

                }

                strLine = br.readLine();
                lineNumber++;
            } while (strLine != null);

        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }

        return isCorrect;
    }*/

    /*public boolean checkExampleEnvironments(String filePath) {	//TODO: rename to: checkExampleEnvironments
        BufferedReader br = null;
        String strLine = "";
        String str;
        boolean isCorrect = true;

        try {

            br = new BufferedReader(new FileReader(filePath));
            int lineNumber = 0;
            do {
                strLine = br.readLine();
                lineNumber++;

                if (strLine != null) {

                    if (strLine.startsWith("\\" + "begin{exmp}")) {
                        strLine = br.readLine();
                        lineNumber++;

                        Set<Integer> exampleIndexes = new HashSet<Integer>();
                        while (strLine != null && !strLine.startsWith("\\" + "end{exmp}")) {
                            String[] strings = strLine.split(" \\| ");

                            if (strings.length != 3) {
                                System.out.println("format error: example does not contain 3 items at line " + lineNumber);
                                isCorrect = false;
                            }

                            int exampleIndex = Integer.parseInt(strings[0]);
                            if (exampleIndexes.contains(exampleIndex)) {
                                System.out.println("format error: duplications in example indexes at line " + lineNumber);
                                isCorrect = false;
                            }
                            exampleIndexes.add(exampleIndex);

                            strLine = br.readLine();
                            lineNumber++;
                        }

                        if (strLine == null) {
                            System.out.println("format error: \\begin{dsc) is not closed");
                            isCorrect = false;
                        }
                    }
                }
            } while (strLine != null);

        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }

        return isCorrect;
    }

    public boolean generalCheck(String filePath) {
        boolean isCorrect = true;
        //isCorrect = checkGrammarItemIndexes(filePath);
        isCorrect = checkExampleEnvironments(filePath) && isCorrect;
        isCorrect = checkExistanceOfGrammarItemIndexes(filePath) && isCorrect;
        return isCorrect;
    }*/

}
