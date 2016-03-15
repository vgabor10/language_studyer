package dictionary;

import java.util.*;
import java.text.DecimalFormat;

public class AnswerDataByCard {

	private Vector<AnswerData> data = new Vector<AnswerData>();	//datas are sorted by date (growing)
	private final int numberOfConsideredAnswersAtAnswerRate = 20;

	public void addAnswer(AnswerData answerData) {
		int i = 0;
		while (i<data.size() && data.get(i).date < answerData.date) {
			i++;
		}

		data.insertElementAt(answerData, i);
	}

	public int numberOfAnswers() {
		return data.size();
	}

	public AnswerData getAnswer(int i) {
		return data.get(i);
	}

	public int getCardIndex() {
		return data.get(0).index;
	}

	public long getLatestAnswerDate() {
		return data.lastElement().date;
	}

	public double countRightAnswerRate() {	//TODO: maybe name more precisely ??? //TODO: more sophisticated definition ???
		int numberOfRightAnswers = 0;

		int i=data.size()-1;
		int k = 0;
		while (0 <= i && k < numberOfConsideredAnswersAtAnswerRate) {
			if (data.get(i).isRight) {
				numberOfRightAnswers++;
			}
			i--;
			k++;
		}

		return (double)numberOfRightAnswers/(double)k;
	}

	public int milisecToDay(long milisecTime) {	//TODO: code repeating
		int timeZone = 1;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public double countRightAnswerRateAtDay(int day) {
		int i=0;
		while (i < numberOfAnswers() && milisecToDay(data.get(i).date) <= day) {
			i++;
		}


		int numberOfRightAnswers = 0;
		int numberOfAnswers = 0;

		int j=i-1;
		while (0<=j && numberOfAnswers < numberOfConsideredAnswersAtAnswerRate) {
			if (data.get(j).isRight) {
				numberOfRightAnswers++;
			}
			numberOfAnswers++;
			j = j-1;
		}		

		if (numberOfAnswers != 0) {
			return (double)numberOfRightAnswers/(double)numberOfAnswers;
		}
		else {
			return -1.0;
		}
	}

	public String toStringShowingPercentage(CardContainer cardContainer) {
		DecimalFormat df = new DecimalFormat("#.00");
		return cardContainer.getCard(data.get(0).index).s2 + " - " + cardContainer.getCard(data.get(0).index).s1 + " | " 
			+ df.format(countRightAnswerRate() * 100) + "%" + " (" + numberOfAnswers() + ")";
	}
}
