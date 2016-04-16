import java.util.*;
import java.text.DecimalFormat;

public class AnswerDataByCard {

	private Vector<AnswerData> data = new Vector<AnswerData>();	//ordered
	private final int numberOfMaxAnswersByCard = 15;	//only the last answers is considered

	public int milisecToDay(long milisecTime) {
		int timeZone = 1;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public void addAnswer(AnswerData answerData) {
		int i = 0;
		while (i<numberOfAnswers() && data.get(i).date < answerData.date) {
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

	public double countRightAnswerRateAtDay(int day) {
		int i=0;
		while (i < numberOfQuestions() && milisecToDay(data.get(i).date) <= day) {
			i++;
		}


		int numberOfRightAnswers = 0;
		int numberOfAnswers = 0;

		int j=i-1;
		while (0<=j && numberOfAnswers < 15) {
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

	public int numberOfQuestions() {
		return data.size();
	}
}
