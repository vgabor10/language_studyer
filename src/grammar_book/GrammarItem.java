package grammar_book;

import study_item_objects.StudyItem;

import java.util.*;

public class GrammarItem extends StudyItem {

	public GrammarItemTitle title = new GrammarItemTitle();
	public String description;
	private Map<Integer, Example> examples = new TreeMap<>();
	String comments;

	public GrammarItem() {
		title.clear();
		comments = "";
		description = "";
	}

	public int numberOfExamples() {
		return examples.size();
	}

	public boolean isEmptyExcludingTitle() {
		return (index == -1) && (description.equals("")) && (examples.isEmpty()) && (comments.equals(""));
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

        @Override
	public String toString() {
		String out;
		out = title.toString().toUpperCase() + "\n\nDESCRIPTION:\n" + description + "\n\nEXAMPLES:\n";

            for (int ind : examples.keySet()) {
                out = out + getExampleByIndex(ind).toString() + "\n";
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

		outString = outString + "\\begin{desc}\n" + description + "\n\\end{desc}\n\n";

		if (comments.length() != 0) {
			outString = outString + comments + "\n";
		}
		outString = outString + "\\begin{exmp}\n";
		for (int i : getExampleIndexes()) {
			outString = outString + getExampleByIndex(i).toStringInLatexFormat() + "\n";
		}
		outString = outString + "\\end{exmp}\n";

		return outString;
	}

}




