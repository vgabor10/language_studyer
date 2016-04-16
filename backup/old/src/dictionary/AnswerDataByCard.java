package dictionary;

import java.util.*;
import java.text.DecimalFormat;

public class AnswerDataByCard {

	public Vector<AnswerData> data = new Vector<AnswerData>();	//TODO: take to private
	//private final int numberOfMaxAnswersByCard = 20; 

	public int numberOfAnswers() {
		return data.size();
	}

	public AnswerData getAnswer(int i) {
		return data.get(i);
	}

	public double countRightAnswerRate() {
		int numberOfRightAnswers = 0;
		for (int i=0; i<data.size(); i++) {
			if (data.get(i).isRight) {
				numberOfRightAnswers++;
			}
		}
		return (double)numberOfRightAnswers/(double)data.size();
	}

	public int numberOfQuestions() {
		return data.size();
	}

	public String toStringShowingPercentage(CardContainer cardContainer) {
		DecimalFormat df = new DecimalFormat("#.00");
		return cardContainer.getCard(data.get(0).index).s2 + " - " + cardContainer.getCard(data.get(0).index).s1 + " | " 
			+ df.format(countRightAnswerRate() * 100) + "%" + " (" + Integer.toString(numberOfQuestions()) + ")";
	}
}
