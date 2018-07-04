package grammar_book;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import language_studyer.AnswerDataContainer;

public class GrammarBookFileFormatChecker {

    private GrammarItemContainer grammarBook;
    private AnswerDataContainer grammarAnswerDataContainer;

    public void setGrammarBook(GrammarItemContainer g) {
        grammarBook = g;
    }

    public void setGrammarAnswerDataContainer(AnswerDataContainer g) {
        grammarAnswerDataContainer = g;
    }

    public void isThereAnswerDataWithInvalidGrammarItemIndex() {	//TODO: take it to an other class
        Set<Integer> grammarItemIndexes = grammarBook.getGrammarItemIndexes();

        int i = 0;
        while (i < grammarAnswerDataContainer.numberOfAnswers()
                && grammarItemIndexes.contains(grammarAnswerDataContainer.getAnswerData(i).index)) {
            i++;
        }

        if (i == grammarAnswerDataContainer.numberOfAnswers()) {
            System.out.println("fortunately there is not answer data with invalid grammar item index");
        } else {
            System.out.println("answer data file contains answer data with invalid grammar item index, an invalid index is: "
                    + grammarAnswerDataContainer.getAnswerData(i).index);
        }
    }

    public boolean checkGrammarItemIndexes(String filePath) {
        BufferedReader br = null;
        String strLine = "";
        boolean isCorrect = true;

        try {

            br = new BufferedReader(new FileReader(filePath));
            int lineNumber = 0;
            Set<Integer> grammarItemIndexes = new HashSet<Integer>();
            do {
                strLine = br.readLine();
                lineNumber++;

                if (strLine != null && strLine.startsWith("GrammarItemIndex")) {

                    int grammarItemIndex = Integer.parseInt(strLine.substring(19));
                    if (grammarItemIndexes.contains(grammarItemIndex)) {
                        System.out.println("error: duplications in GrammarItemIndexes at line " + lineNumber);
                        isCorrect = false;
                    } else {
                        grammarItemIndexes.add(grammarItemIndex);
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

    public boolean checkExistanceOfGrammarItemIndexes(String filePath) {
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
    }

    public boolean checkExampleEnvironments(String filePath) {	//TODO: rename to: checkExampleEnvironments
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
        isCorrect = checkGrammarItemIndexes(filePath);
        isCorrect = checkExampleEnvironments(filePath) && isCorrect;
        isCorrect = checkExistanceOfGrammarItemIndexes(filePath) && isCorrect;
        return isCorrect;
    }
}
