package grammar_book;

public class GrammarBook {
    
    public DataContainer dataContainer = new DataContainer();
    public GrammarTester grammartester = new GrammarTester();
    public DataLoader dataLoader = new DataLoader();
    public DiscFilesMetaDataHandler discFilesMetaDataHandler = new DiscFilesMetaDataHandler();
    public GrammarAnswerDataStatisticsMaker statisticsMaker = new GrammarAnswerDataStatisticsMaker();
    public DataModificator dataModificator = new DataModificator();
    
    public GrammarBook() {
        dataLoader.discFilesMetaDataHandler = discFilesMetaDataHandler;
        dataLoader.setDataContainer(dataContainer);
        dataLoader.loadAllData();
    }
    
}
