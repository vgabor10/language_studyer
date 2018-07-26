package grammar_book;

import language_studyer.DiscFilesMetaDataHandler;

public class GrammarBook {
    
    public GrammarDataContainer dataContainer;
    public GrammarTester grammartester;
    public GrammarDataLoader dataLoader;
    public GrammarAnswerDataStatisticsMaker statisticsMaker;
    public GrammarDataModificator dataModificator;
    public DiscFilesMetaDataHandler discFilesMetaDataHandler;

    
    public void initialise(DiscFilesMetaDataHandler dfmdh) {
        this.discFilesMetaDataHandler = dfmdh;
        
        this.dataContainer = new GrammarDataContainer();
        this.grammartester = new GrammarTester();
        this.dataLoader = new GrammarDataLoader();
        this.statisticsMaker = new GrammarAnswerDataStatisticsMaker();
        this.dataModificator = new GrammarDataModificator();
        
        dataLoader.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataLoader.setGrammarDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        grammartester.setData(dataContainer);
        
        dataModificator.setData(dataContainer);
        
        statisticsMaker.setData(dataContainer);
    }
    
}
