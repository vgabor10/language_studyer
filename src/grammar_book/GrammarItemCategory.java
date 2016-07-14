package grammar_book;

import java.util.*;

public class GrammarItemCategory {

	public String title;
	public int grammarItemIndex;
	public Vector<GrammarItemCategory> subCategoris = new Vector<GrammarItemCategory>();

	GrammarItemCategory() {
		grammarItemIndex = -1;
	}

	public boolean containsSubCategoryWithTitle(String title) {
		int i=0;
		while (i<subCategoris.size() && !subCategoris.get(i).title.equals(title)) {
			i++;
		}

		return i<subCategoris.size();
	}

	public GrammarItemCategory getSubCategoryWithTitle(String title) {
		int i=0;
		while (i<subCategoris.size() && !subCategoris.get(i).title.equals(title)) {
			i++;
		}

		if (i == subCategoris.size()) {
			return null;
		}
		else {
			return subCategoris.get(i);
		}
	}

	public void toScreenSubCategoris() {
		for (int i=0; i<subCategoris.size(); i++) {
			System.out.println(i + " - " + subCategoris.get(i).title);
		}
	}
}
