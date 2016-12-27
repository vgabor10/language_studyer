package grammar_book;

public class DataContainer {
    
    public GrammarItemContainer grammarItemContainer = new GrammarItemContainer();
    public GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
    public StudyStrategy studyStrategy = new StudyStrategy();
    
    public void clear() {
        grammarItemContainer.clear();
        grammarAnswerDataContainer.clear();
        studyStrategy.clear();
    }
    
}
