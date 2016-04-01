package grammar_book;

import java.util.*;

class GrammarItemTitle {

	private Vector<String> categoris;

	GrammarItemTitle() {
		categoris = new Vector<String>();
	}

	GrammarItemTitle(GrammarItemTitle git) {
		categoris = new Vector<String>(git.getCategorisInVector());
	}

	public void setSection(String section) {
		while (categoris.size() < 1) {
			categoris.add("-");
		}
		categoris.setElementAt(section,0);
	}

	public void setSubsection(String subsection) {
		while (categoris.size() < 2) {
			categoris.add("-");
		}
		categoris.setElementAt(subsection,1);
	}

	public void setSubsubsection(String subsubsection) {
		while (categoris.size() < 3) {
			categoris.add("-");
		}
		categoris.setElementAt(subsubsection,2);
	}

	public void setParagraph(String paragraph) {
		while (categoris.size() < 4) {
			categoris.add("-");
		}
		categoris.setElementAt(paragraph,3);
	}

	public String getSection() {
		return categoris.get(0);
	}

	public String getSubsection() {
		return categoris.get(1);
	}

	public String getSubsubsection() {
		return categoris.get(2);
	}

	public int getDebth() {
		return categoris.size();
	}

	public void deleteCategoriesFromDebth(int debth) {
		while (debth < categoris.size()) {
			categoris.removeElementAt(debth);
		}
	}

	public Vector<String> getCategorisInVector() {
		return categoris;
	}

	public String toString() {
		String out = "";
		if (categoris.size() != 0) {
			out = categoris.get(0);
			for (int i=1; i<categoris.size(); i++) {
				out = out + "/" + categoris.get(i);
			}
		}
		return out;
	}

	public void clear() {
		categoris.clear();
	}

}
