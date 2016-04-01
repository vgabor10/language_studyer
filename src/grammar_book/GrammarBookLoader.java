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

public class GrammarBookLoader {

	private GrammarBook grammarBook;

	public void setGrammarBook(GrammarBook g) {
		grammarBook = g;
	}

	public void loadGrammarBookFromFile(String filePath) {
		BufferedReader br = null;
		String strLine = "";
		String str;

		try {
			br = new BufferedReader( new FileReader(filePath));

			strLine = br.readLine();

			while(!strLine.equals("\\begin{document}")) {
				if (grammarBook.preambulum.length() == 0) {
					grammarBook.preambulum = strLine;
				}
				else {
					grammarBook.preambulum = grammarBook.preambulum + "\n" + strLine;
				}
				strLine = br.readLine();
			}

			GrammarItem grammarItem = new GrammarItem();;

			while(strLine != null) {

				if (strLine.startsWith("GrammarItemIndex")) {
					grammarItem.index = Integer.parseInt(strLine.substring(strLine.indexOf("=") + 2));
				}

				if (strLine.startsWith("\\" + "section")) {
					if (!grammarItem.isEmptyExcludingTitle()) {
						grammarBook.addGrammarItem(grammarItem);
						grammarItem = new GrammarItem();
					}

					String s = strLine.substring(9);
					String section = s.substring(0, s.indexOf("}"));

					grammarItem.title.deleteCategoriesFromDebth(0);
					grammarItem.title.setSection(section);
				}

				if (strLine.startsWith("\\" + "subsection")) {
					if (!grammarItem.isEmptyExcludingTitle()) {
						grammarBook.addGrammarItem(grammarItem);

						GrammarItemTitle title = new GrammarItemTitle(grammarItem.title);
						grammarItem = new GrammarItem();
						grammarItem.title = title;
					}

					String s = strLine.substring(12);
					String subsection = s.substring(0, s.indexOf("}"));

					grammarItem.title.deleteCategoriesFromDebth(1);
					grammarItem.title.setSubsection(subsection);
				}

				if (strLine.startsWith("\\" + "subsubsection")) {
					if (!grammarItem.isEmptyExcludingTitle()) {
						grammarBook.addGrammarItem(grammarItem);

						GrammarItemTitle title = new GrammarItemTitle(grammarItem.title);
						grammarItem = new GrammarItem();
						grammarItem.title = title;
					}

					String s = strLine.substring(15);
					String subsubsection = s.substring(0, s.indexOf("}"));

					grammarItem.title.deleteCategoriesFromDebth(2);
					grammarItem.title.setSubsubsection(subsubsection);
				}

				if (strLine.startsWith("\\" + "paragraph")) {
					if (!grammarItem.isEmptyExcludingTitle()) {
						grammarBook.addGrammarItem(grammarItem);

						GrammarItemTitle title = new GrammarItemTitle(grammarItem.title);
						grammarItem = new GrammarItem();
						grammarItem.title = title;
					}

					String s = strLine.substring(11);
					String subsubsection = s.substring(0, s.indexOf("}"));

					grammarItem.title.deleteCategoriesFromDebth(3);
					grammarItem.title.setParagraph(subsubsection);
				}

				if (strLine.startsWith("%")) {
					grammarItem.commentForExamples = strLine;
				}

				if (strLine.equals("\\" + "begin{desc}")) {
					strLine = br.readLine();

					while (!strLine.equals("\\" + "end{desc}")) {
						grammarItem.description.strData = grammarItem.description.strData + strLine + "\n";
						strLine = br.readLine();
					}

					if (grammarItem.description.strData.endsWith("\n")) {
						grammarItem.description.strData
						= grammarItem.description.strData.substring(0, grammarItem.description.strData.length()-1);
						
					}
				}

				if (strLine.equals("\\" + "begin{exmp}")) {
					strLine = br.readLine();

					while (!strLine.equals("\\" + "end{exmp}")) {
						String[] strings = strLine.split(" \\| ");
						Example example = new Example();

						example.index = Integer.parseInt(strings[0]);
						example.hun = strings[2];
						example.foreign = strings[1];

						grammarItem.addExample(example);

						strLine = br.readLine();
					}
				}

				strLine = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
			System.err.println("Unable to read the file: fileName");
		}
	}
}
