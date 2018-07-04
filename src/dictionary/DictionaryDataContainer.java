package dictionary;

import language_studyer.DataContainer;

public class DictionaryDataContainer extends DataContainer {
    
    DictionaryDataContainer() {
        setCardContainer(new CardContainer());
    }
    
    public void setCardContainer(CardContainer cc) {
        setStudyItemContainer(cc);
    }
    
    public CardContainer getCardContainer() {
        return (CardContainer) this.getStudyItemContainer();
    }
}
