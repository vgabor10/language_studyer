package dictionary;

import disc_operation_handlers.LanguageFilesDataHandler;

public class Dictionary {
    
    public CardTester cardTester = new CardTester();
    public CardFinder cardFinder = new CardFinder();
    public DataContainer dataContainer = new DataContainer();
    public StatisticsMaker statisticsMaker = new StatisticsMaker();
    public DataLoader dataLoader = new DataLoader();
    public DataModificator dataModificator = new DataModificator();
    public StudyStrategyHandler studyStrategyHandler = new StudyStrategyHandler();
    public LanguageFilesDataHandler languageFilesDataHandler = new LanguageFilesDataHandler();
    
    public Dictionary() {
        dataLoader.setLanguageFilesDataHandler(languageFilesDataHandler);
        dataLoader.setDataContainer(dataContainer);
        dataLoader.loadAllData();
        dataModificator.setData(dataContainer);
        statisticsMaker.setData(dataContainer);
        cardFinder.setCardContainer(dataContainer.cardContainer);
        cardTester.setAllCard(dataContainer.cardContainer);
        studyStrategyHandler.loadStudyStrategyDataFromDisc();
    }
}
