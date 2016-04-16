package grammar_book;

import java.util.*;

public class GrammarItem {

	public int index;
	public String title;
	public String description;
	public Vector<Example> examples = new Vector<Example>();

	public int numberOfExamples() {
		return examples.size();
	}

	public String toString() {
		String out;
		out = title + "\n\n" + description + "\n\n";

		for (int i=0; i<examples.size(); i++) {
			out = out + examples.get(i).toString() + "\n";
		}

		return out.substring(0, out.length()-2);
	}

}
