package grammar_book;

import language_studyer.DiscFilesMetaDataHandler;

public class GrammarBook {
    
    public GrammarDataContainer dataContainer = new GrammarDataContainer();
    public GrammarTester grammartester = new GrammarTester();
    public GrammarDataLoader dataLoader = new GrammarDataLoader();
    public DiscFilesMetaDataHandler discFilesMetaDataHandler = new DiscFilesMetaDataHandler();
    public GrammarAnswerDataStatisticsMaker statisticsMaker = new GrammarAnswerDataStatisticsMaker();
    public GrammarDataModificator dataModificator = new GrammarDataModificator();
    
    public GrammarBook() {
        dataLoader.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataLoader.setGrammarDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        grammartester.setData(dataContainer);
        
        dataModificator.setData(dataContainer);
        
        statisticsMaker.setData(dataContainer);
    }
    
}
