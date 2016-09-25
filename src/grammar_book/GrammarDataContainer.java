package grammar_book;

public class GrammarDataContainer {

    public GrammarBook grammarBook = new GrammarBook();
    public GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
    
    public void clear() {
        grammarBook.clear();
        grammarAnswerDataContainer.clear();
    }
    
}
