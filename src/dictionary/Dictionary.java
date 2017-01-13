package dictionary;

public class Dictionary {
    
    public CardTester cardTester = new CardTester();
    public CardFinder cardFinder = new CardFinder();
    public DataContainer dataContainer = new DataContainer();
    public StatisticsMaker statisticsMaker = new StatisticsMaker();
    public DataLoader dataLoader = new DataLoader();
    public DataModificator dataModificator = new DataModificator();
    public DiscFilesMetaDataHandler discFilesMetaDataHandler = new DiscFilesMetaDataHandler();
    
    public Dictionary() {
        dataLoader.setLanguageFilesDataHandler(discFilesMetaDataHandler);
        dataLoader.setDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        dataModificator.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataModificator.setData(dataContainer);
        
        statisticsMaker.setData(dataContainer);
        
        cardFinder.setCardContainer(dataContainer.cardContainer);
        
        cardTester.setData(dataContainer);
    }
}
