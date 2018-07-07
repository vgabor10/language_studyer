package dictionary;

import language_studyer.DataContainer;

public class DictionaryDataContainer extends DataContainer {
    
    DictionaryDataContainer () {
        studyItemContainer = new CardContainer();
    }
    
    public CardContainer getCardContainer() {
        return (CardContainer) this.getStudyItemContainer();
    }
}
