package grammar_book;

import language_studyer.DataContainer;

public class GrammarDataContainer extends DataContainer {
    
    public GrammarItemContainer getGrammarItemContainer() {
        return (GrammarItemContainer) this.getStudyItemContainer();
    }
 
}
