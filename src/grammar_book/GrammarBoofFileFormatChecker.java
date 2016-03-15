package grammar_book;

import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class GrammarBoofFileFormatChecker {

	private GrammarBook grammarBook;

	public void setGrammarBook(GrammarBook g) {
		grammarBook = g;
	}

	/*public void completeExamplesWithEnumeration(String inFilePath, String outFilePath) {	//TODO: using?, take to other class, safety
		BufferedReader br = null;
		String strLine = "";

		try {

		FileWriter fw = new FileWriter(outFilePath);
		br = new BufferedReader( new FileReader(inFilePath));
		do {
			strLine = br.readLine();

			if (strLine != null) {

				if (strLine.startsWith("\\" + "begin{exmp}")) {
					fw.write(strLine + "\n");

					strLine = br.readLine();
					int exampleIndex = 1;
					Set<Integer> exampleIndexes = new HashSet<Integer>();
					while (!strLine.startsWith("\\" + "end{exmp}")) {
						String[] strings = strLine.split(" - ");

						if (strings.length != 3) {
							strLine = Integer.toString(exampleIndex) + " - " + strLine;
						}

						if (!strLine.endsWith("\\" + "\\")) {
							strLine = strLine + "\\" + "\\";
						}

						fw.write(strLine + "\n");

						strLine = br.readLine();
						exampleIndex++;
					}
					fw.write(strLine + "\n");
				}
				else {
					fw.write(strLine + "\n");
				}
			}
		} while (strLine != null);

		fw.close();

		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}*/

	public boolean checkGrammarItemIndexes(String filePath) {
		BufferedReader br = null;
		String strLine = "";
		boolean isCorrect = true;

		try {

		br = new BufferedReader( new FileReader(filePath));
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
				}
				else {
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

		br = new BufferedReader( new FileReader(filePath));
		int lineNumber = 0;
		boolean isContent = false;
		boolean isGrammarItemIndex = false;

		strLine = br.readLine();
		while (!strLine.startsWith("\\section{") &&
				!strLine.startsWith("\\subsection{") &&
				!strLine.startsWith("\\subsubsection{")) {
			strLine = br.readLine();
			lineNumber++;
		}

		do {

			if (strLine != null) {

				if (strLine.startsWith("\\section{") ||
					strLine.startsWith("\\subsection{") ||
					strLine.startsWith("\\subsubsection{")) {

					if (isContent && !isGrammarItemIndex) {
						System.out.println("error: missing grammarItemIndex at line " + lineNumber);
						isCorrect = false;
					}

					isContent = false;
					isGrammarItemIndex = false;
				}
				else if (strLine.startsWith("GrammarItemIndex")) {
					isGrammarItemIndex = true;
				}
				else if (!strLine.equals("")) {
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

		br = new BufferedReader( new FileReader(filePath));
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

						if (!strings[2].endsWith("\\\\")) {
							System.out.println("format error: example does not end with " +  "\\" + "\\" + " at line " + lineNumber);
							isCorrect = false;
						}

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
