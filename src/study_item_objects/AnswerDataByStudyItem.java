package common;

import java.util.*;

public class AnswerDataByStudyItem {

	private Vector<AnswerData> data = new Vector<AnswerData>();	//datas are sorted by date (growing)

	private final int numberOfConsideredAnswersAtAnswerRateAtCard = 10;
	private final int numberOfConsideredAnswersAtAnswerRateAtGrammarItem = 40;

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

	public int getStudyItemIndex() {
		return data.get(0).index;
	}

	public long getLatestAnswerDate() {	//TODO: rename: lastStudyDate()
		return data.lastElement().date;
	}

	public int numberOfConsideredAnswersAtAnswerRate() {
		String answerDataClassName = data.get(0).getClass().getSimpleName();

		//System.out.println("LOG: answerDataClassName: " + answerDataClassName);		//log

		int numberOfConsideredAnswers;
		if (answerDataClassName.equals("GrammarAnswerData")) {
			//System.out.println("LOG: grammar item branche");	//log
			return numberOfConsideredAnswersAtAnswerRateAtGrammarItem;
		}
		else {
			//System.out.println("LOG: card branche");	//log
			return numberOfConsideredAnswersAtAnswerRateAtCard;
		} 
	}

	public double countRightAnswerRate() {
		if (numberOfAnswers() != 0) {
			int numberOfRightAnswers = 0;

			int i=data.size()-1;
			int k = 0;
			while (0 <= i && k < numberOfConsideredAnswersAtAnswerRate()) {
				if (data.get(i).isRight) {
					numberOfRightAnswers++;
				}
				i--;
				k++;
			}

			return (double)numberOfRightAnswers/(double)k;
		}
		else {
			return -1;
		}
	}

	public double countRightAnswerRateAtDay(int day) {
		GeneralFunctions generalFunctions = new GeneralFunctions();

		int i=0;
		while (i < numberOfAnswers() && generalFunctions.milisecToDay(data.get(i).date) <= day) {
			i++;
		}


		int numberOfRightAnswers = 0;
		int numberOfAnswers = 0;

		int j=i-1;
		while (0<=j && numberOfAnswers < numberOfConsideredAnswersAtAnswerRate()) {
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

	public void loadDataFromAnswerDataContainer(int index, AnswerDataContainer answerDataContainer) {
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			if (answerDataContainer.getAnswerData(i).index == index) {
				addAnswer(answerDataContainer.getAnswerData(i));
			}
		}
	}

	public void clear() {
		data.clear();
	}
}
