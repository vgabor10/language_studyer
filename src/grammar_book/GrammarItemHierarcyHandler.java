package grammar_book;

import java.util.*;

public class GrammarItemHierarcyHandler {

	public GrammarItemCategory rootItemCategory = new GrammarItemCategory();

	/*public GrammarItemCategory getGrammarItemCategoryByCoordinates(Vector<Integer> coordinates) {
		GrammarItemCategory grammarItemCategory = grammarItemCategoris.get(coordinates.get(0));

		for (int i=1; i<coordinates.size(); i++) {
			grammarItemCategory = grammarItemCategory.subCategoris.get(coordinates.get(i));
		}

		return grammarItemCategory;
	}*/

	public void addCategoryFromGrammarItem(GrammarItem grammarItem) {
		GrammarItemCategory grammarItemCategory = rootItemCategory;

		for (int i=1; i<=grammarItem.title.getDebth(); i++) {

			if (i == 1) {
				GrammarItemCategory grammarItemCategoryToAdd = new GrammarItemCategory();
				grammarItemCategoryToAdd.title = grammarItem.title.getSection();
				grammarItemCategoryToAdd.grammarItemIndex = grammarItem.index;

				if (!grammarItemCategory.containsSubCategoryWithTitle(grammarItemCategoryToAdd.title)) {
					grammarItemCategory.subCategoris.add(grammarItemCategoryToAdd);
					grammarItemCategory = grammarItemCategoryToAdd;
				}
				else {
					grammarItemCategory = grammarItemCategory.getSubCategoryWithTitle(grammarItemCategoryToAdd.title);
				}
				
			}

			if (i == 2) {
				GrammarItemCategory grammarItemCategoryToAdd = new GrammarItemCategory();
				grammarItemCategoryToAdd.title = grammarItem.title.getSubsection();
				grammarItemCategoryToAdd.grammarItemIndex = grammarItem.index;

				if (!grammarItemCategory.containsSubCategoryWithTitle(grammarItemCategoryToAdd.title)) {
					grammarItemCategory.subCategoris.add(grammarItemCategoryToAdd);
					grammarItemCategory = grammarItemCategoryToAdd;
				}
				else {
					grammarItemCategory = grammarItemCategory.getSubCategoryWithTitle(grammarItemCategoryToAdd.title);
				}
			}

			if (i == 3) {
				GrammarItemCategory grammarItemCategoryToAdd = new GrammarItemCategory();
				grammarItemCategoryToAdd.title = grammarItem.title.getSubsubsection();

				grammarItemCategoryToAdd.grammarItemIndex = grammarItem.index;

				if (!grammarItemCategory.containsSubCategoryWithTitle(grammarItemCategoryToAdd.title)) {
					grammarItemCategory.subCategoris.add(grammarItemCategoryToAdd);
					grammarItemCategory = grammarItemCategoryToAdd;
				}
				else {
					grammarItemCategory = grammarItemCategory.getSubCategoryWithTitle(grammarItemCategoryToAdd.title);
				}
			}
		}
	}

	public void setHierarchyFromGrammarBook(GrammarBook grammarBook) {
		for (int i=0; i<grammarBook.numberOfGrammarItems(); i++) {
			addCategoryFromGrammarItem(grammarBook.getGrammarItemByOrder(i));
		}
	}

}
