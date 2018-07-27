package dictionary;

import language_studyer.DiscFilesMetaDataHandler;

public class Dictionary {
    
    public CardTester cardTester;
    public CardFinder cardFinder;
    public DictionaryDataContainer dataContainer;
    public DictionaryStatisticsMaker statisticsMaker;
    public DictionaryDataLoader dataLoader;
    public DictionaryDataModificator dataModificator;
    public DiscFilesMetaDataHandler discFilesMetaDataHandler;
    
    public void initialise(DiscFilesMetaDataHandler dfmdh) {
        this.discFilesMetaDataHandler = dfmdh;
        
        this.cardTester = new CardTester();
        this.cardFinder = new CardFinder();
        this.dataContainer = new DictionaryDataContainer();
        this.statisticsMaker = new DictionaryStatisticsMaker();
        this.dataLoader = new DictionaryDataLoader();
        this.dataModificator = new DictionaryDataModificator();
        
        dataLoader.setLanguageFilesDataHandler(discFilesMetaDataHandler);
        dataLoader.setDictionaryDataContainer(dataContainer);
        dataLoader.loadAllData();
        
        dataModificator.setDiscFilesMetaDataHandler(discFilesMetaDataHandler);
        dataModificator.setData(dataContainer);
        
        statisticsMaker.setData(dataContainer);
        
        cardTester.setData(dataContainer);
      
        cardFinder.setCardContainer(dataContainer.getCardContainer());
    }
}
