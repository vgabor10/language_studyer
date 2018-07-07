package grammar_book;

import language_studyer.DataContainer;

public class GrammarDataContainer extends DataContainer {
    
    GrammarDataContainer() {
        studyItemContainer = new GrammarItemContainer();
    }
    
    public GrammarItemContainer getGrammarItemContainer() {
        return (GrammarItemContainer) this.getStudyItemContainer();
    }
 
}
