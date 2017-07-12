package grammar_book;

public class GrammarBook {
    
    public DataContainer dataContainer = new DataContainer();
    public GrammarTester grammartester = new GrammarTester();
    public DataLoader dataLoader = new DataLoader();
    public DiscFilesMetaDataHandler discFilesMetaDataHandler = new DiscFilesMetaDataHandler();
    public StatisticsMaker statisticsMaker = new StatisticsMaker();
    public DataModificator dataModificator = new DataModificator();
    
    public GrammarBook() {
        dataLoader.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataLoader.setDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        grammartester.setData(dataContainer);
        
        statisticsMaker.setData(dataContainer);
    }
    
}
