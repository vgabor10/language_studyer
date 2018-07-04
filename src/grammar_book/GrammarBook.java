package grammar_book;

public class GrammarBook {
    
    public GrammarDataContainer dataContainer = new GrammarDataContainer();
    public GrammarTester grammartester = new GrammarTester();
    public GrammarDataLoader dataLoader = new GrammarDataLoader();
    public DiscFilesMetaDataHandler discFilesMetaDataHandler = new DiscFilesMetaDataHandler();
    public GrammarAnswerDataStatisticsMaker statisticsMaker = new GrammarAnswerDataStatisticsMaker();
    public DataModificator dataModificator = new DataModificator();
    
    public GrammarBook() {
        dataLoader.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataLoader.setDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        grammartester.setData(dataContainer);
        
        statisticsMaker.studiedAnswerDataContainer 
                = dataContainer.getAnswerDataContainer();
        statisticsMaker.studiedAnswerDataByStudyItemsContainer 
                = dataContainer.auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer;
        statisticsMaker.studiedStudyItemIndexes
                = dataContainer.auxiliaryDataContainer.studiedCardIndexes;
    }
    
}
