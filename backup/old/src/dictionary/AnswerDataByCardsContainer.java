package dictionary;

import java.util.*;

public class AnswerDataByCardsContainer {

	private Vector<AnswerDataByCard> data = new Vector<AnswerDataByCard>();

	public void addAnswerData(AnswerData answerData) {
		int i=0;
		while (i < data.size() && data.get(i).data.get(0).index != answerData.index) {
			i++;
		}

		if(i == data.size()) {
			AnswerDataByCard answerDataByCard = new AnswerDataByCard();
			answerDataByCard.data.add(answerData);
			data.add(answerDataByCard);
		}
		else {
			data.get(i).data.add(answerData);
		}
	}

	public void loadDataFromAnswerDataContainer(AnswerDataContainer answerDataContainer) {
		clear();
		for (int i=0; i<answerDataContainer.data.size(); i++) {
			addAnswerData(answerDataContainer.data.get(i));
		}
	}

	public AnswerDataByCard getAnswerDataByCard(int i) {
		return data.get(i);
	}

	public int numberOfCards() {
		return data.size();
	}

	public AnswerDataByCard[] toArray() {
		AnswerDataByCard[] array = new AnswerDataByCard[numberOfCards()];
		for (int i=0; i<numberOfCards(); i++) {
			array[i] = data.get(i);
		}
		return array;
	}

	public void clear() {
		data.clear();
	}

}
