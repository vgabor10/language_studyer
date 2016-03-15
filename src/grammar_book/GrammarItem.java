package grammar_book;

import java.util.*;

public class GrammarItem {

	public int index;
	public GrammarItemTitle title = new GrammarItemTitle();
	public Description description = new Description();
	private Map<Integer, Example> examples = new TreeMap<Integer, Example>();
	String commentForExamples;

	GrammarItem() {
		index = -1;
		title.clear();
		commentForExamples = "";
	}

	public int numberOfExamples() {
		return examples.size();
	}

	public boolean isEmptyExcludingTitle() {
		return (index == -1) && (description.strData.equals("")) && (examples.size() == 0) && (commentForExamples.equals(""));
	}

	public Example getExampleByIndex(int index) {
		return examples.get(index);
	}

	public void addExample(Example e) {
		examples.put(e.index ,e);
	}

	public Set<Integer> getExampleIndexes() {
		return examples.keySet();
	}

	public String toString() {
		String out;
		out = title.toString().toUpperCase() + "\n\nDESCRIPTION:\n" + description.getInReadingForm() + "\n\nEXAMPLES:\n";

		for (int index : examples.keySet()) {
			out = out + getExampleByIndex(index).toString() + "\n";
		}

		if (out.endsWith("\n")) {
			out = out.substring(0, out.length()-1);
		}

		return out;
	}

	public String toStringInLatexFormat() {
		String outString = "";

		if (title.getDebth() == 1) {
			outString = "\\section{" + title.getSection() + "}\n\n";
		}
		if (title.getDebth() == 2) {
			outString = "\\subsection{" + title.getSection() + "}\n\n";
		}
		if (title.getDebth() == 3) {
			outString = "\\subsubsection{" + title.getSection() + "}\n\n";
		}

		outString = outString + "GrammarItemIndex = " + Integer.toString(index) + "\n\n";

		outString = outString + "\\begin{desc}\n" + description.strData + "\n\\end{desc}\n\n";

		if (commentForExamples.length() != 0) {
			outString = outString + commentForExamples + "\n";
		}
		outString = outString + "\\begin{exmp}\n";
		for (int i : getExampleIndexes()) {
			outString = outString + getExampleByIndex(i).toStringInLatexFormat() + "\n";
		}
		outString = outString + "\\end{exmp}\n";

		return outString;
	}

}






