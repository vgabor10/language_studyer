package dictionary;

import language_studyer.DataContainer;

public class DictionaryDataContainer extends DataContainer {
    
    public CardContainer getCardContainer() {
        return (CardContainer) this.getStudyItemContainer();
    }
}
