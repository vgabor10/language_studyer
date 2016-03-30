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

public class GrammarBook {

	public String preambulum = "";

	private Vector<GrammarItem> grammarItems = new Vector<GrammarItem>();

	public int numberOfGrammarItems() {
		return grammarItems.size();
	}

	public GrammarItem getGrammarItemByIndex(int grammarItemIndex) {
		int i=0;
		while (i<grammarItems.size() && grammarItems.get(i).index != grammarItemIndex) {
			i++;
		}

		if (i == grammarItems.size()) {
			return null;
		}
		else {
			return grammarItems.get(i);
		}
	}

	public GrammarItem getGrammarItemByOrder(int orderIndex) {
		return grammarItems.get(orderIndex);
	}

	public Set<Integer> getGrammarItemIndexes() {		//TODO: delete
		Set<Integer> grammarItemIndexes = new HashSet<Integer>();

		for (int i=0; i<grammarItems.size(); i++) {
			grammarItemIndexes.add(grammarItems.get(i).index);
		}
		return grammarItemIndexes;
	}

	public void addGrammarItem(GrammarItem gi) {
		grammarItems.add(gi);
	}

	public Example getExample(int grammarItemIndex, int exampleIndex) {
		return getGrammarItemByIndex(grammarItemIndex).getExampleByIndex(exampleIndex);
	}

	public void deleteGrammarItem(int grammarItemIndex) {	//TODO: implement, think it over
		grammarItems.remove(grammarItemIndex);
	}

	/*public void loadGrammarBookFromFile(String filePath) {
		BufferedReader br = null;
		String strLine = "";
		String str;

		try {
			br = new BufferedReader( new FileReader(filePath));
			do {
				GrammarItem grammarItem = new GrammarItem();

				strLine = br.readLine();
				grammarItem.index = Integer.parseInt(strLine);

				//System.out.println("index: " + strLine);	//debug

				br.readLine();
				strLine = br.readLine();
				grammarItem.title = strLine;

				//System.out.println("title: " + strLine);	//debug

				br.readLine();
				str = br.readLine() + "\n";
				strLine = br.readLine();
				while (!strLine.equals("")) {
					str = str + strLine + "\n";
					strLine = br.readLine();
				};
				grammarItem.description = str.substring(0,str.length() - 1);

				//System.out.println("description: " + str);	//debug

				strLine = br.readLine();
				while (strLine != null && !strLine.equals("")) {
					Example example = new Example();
					example.hun = strLine.split("\t")[1];
					example.foreign = strLine.split("\t")[0];
					grammarItem.examples.add(example);

					//System.out.println(example.toString());	//debug

					strLine = br.readLine();
				}

				grammarItems.add(grammarItem);
				//System.out.println(grammarItems.toString());	//debug

			} while (strLine != null);

		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}*/

	public void toScreenTableOfContents() {
		System.out.println("TABLE OF CONTENT");
		System.out.println();
		for (int i=0; i<grammarItems.size(); i++) {
			System.out.println(i + " - " 
				+ grammarItems.get(i).title + " (" + grammarItems.get(i).numberOfExamples() + ")");
		}

		System.out.println();
		System.out.println("note: index - title (number of examples)");
	}

	public void toScreen() {
		for (int i=0; i<grammarItems.size(); i++) {
			System.out.println(grammarItems.get(i).toString());
		}
	}
}
