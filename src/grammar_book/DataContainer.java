package grammar_book;

public class DataContainer {

    //TODO: implement
    
    public GrammarItemContainer grammarItemContainer = new GrammarItemContainer();
    public GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
    
    public void clear() {
        grammarItemContainer.clear();
        grammarAnswerDataContainer.clear();
    }
    
}
