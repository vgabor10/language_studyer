package grammar_book;

import java.util.*;
import java.io.Console;

public class GrammarItemChooser {

	private GrammarBook grammarBook;

	public void setGrammarBook(GrammarBook gb) {
		grammarBook = gb;
	}

	public int chooseSections() {
		Set<String> sectionNames = new HashSet<String>();
		int index = 0;

		for (int i=0; i<grammarBook.numberOfGrammarItems(); i++) {
			String sectionName = grammarBook.getGrammarItemByOrder(i).title.getSection();
			if (!sectionNames.contains(sectionName)) {
				sectionNames.add(sectionName);

				if (grammarBook.getGrammarItemByOrder(i).title.getDebth() == 1) {
					System.out.println(index + " - " + sectionName);
				}
				else {
					System.out.println(index + " - " + sectionName 
						+ " (" + grammarBook.getGrammarItemByOrder(i).numberOfExamples() + ")");
				}
				index++;
			}
		}

		System.out.println("\nchoose a number:");
		Console console = System.console();
		int n = Integer.parseInt(console.readLine());
		return n;
	}

	public void chooseGrammarItem() {
		Console console = System.console();

		GrammarItemHierarcyHandler grammarItemHierarcyHandler = new GrammarItemHierarcyHandler();
		grammarItemHierarcyHandler.setHierarchyFromGrammarBook(grammarBook);

		GrammarItemCategory grammarItemCategory = grammarItemHierarcyHandler.rootItemCategory;

		while (grammarItemCategory.subCategoris.size() != 0) {
			System.out.print("\033[H\033[2J");
			grammarItemCategory.toScreenSubCategoris();
			int n = Integer.parseInt(console.readLine());
			grammarItemCategory = grammarItemCategory.subCategoris.get(n);
		}
	}
}
