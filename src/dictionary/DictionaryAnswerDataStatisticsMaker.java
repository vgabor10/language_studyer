package dictionary;

import common.*;

import java.util.*;

public class DictionaryAnswerDataStatisticsMaker extends AnswerDataStatisticsMaker {	//TODO: rename: DictionaryStatisticsMaker

	public void setAnswerDataContainer(AnswerDataContainer ac) {
		answerDataContainer = ac;
	}

	public void setCardContainer(CardContainer cc) {
		this.studyItemContainer = cc;
	}

	public CardContainer getCardContainer() {
		return (CardContainer)this.studyItemContainer;
	}
}
