package dictionary;

public class Dictionary {
    
    public CardTester cardTester = new CardTester();
    public CardFinder cardFinder = new CardFinder();
    public DictionaryDataContainer dataContainer = new DictionaryDataContainer();
    public DictionaryStatisticsMaker statisticsMaker = new DictionaryStatisticsMaker();
    public DictionaryDataLoader dataLoader = new DictionaryDataLoader();
    public DictionaryDataModificator dataModificator = new DictionaryDataModificator();
    public DiscFilesMetaDataHandler discFilesMetaDataHandler = new DiscFilesMetaDataHandler();
    
    public Dictionary() {
        dataLoader.setLanguageFilesDataHandler(discFilesMetaDataHandler);
        dataLoader.setDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        dataModificator.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataModificator.setData(dataContainer);
        
        statisticsMaker.setData(dataContainer);
        
        cardFinder.setCardContainer(dataContainer.getCardContainer());
        
        cardTester.setData(dataContainer);
    }
}
