package grammar_book;

import language_studyer.DataContainer;

public class GrammarDataContainer extends DataContainer {
        
    GrammarDataContainer() {
        setGrammarItemContainer(new GrammarItemContainer());
    }
    
    public void setGrammarItemContainer(GrammarItemContainer gic) {
        setStudyItemContainer(gic);
    }
    
    public GrammarItemContainer getGrammarItemContainer() {
        return (GrammarItemContainer) this.getStudyItemContainer();
    }
 
}
