import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AnswerDataByCardsContainer {

	private Map<Integer,AnswerDataByCard> data = new HashMap<Integer,AnswerDataByCard>();

	public int milisecToDay(long milisecTime) {
		int timeZone = 1;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public Set<Integer> getStudyingDays() {
		Set<Integer> studyingDays = new HashSet<Integer>();
		for (int index : data.keySet()) {
			AnswerDataByCard answerDataByCard = getAnswerDataByCardByIndex(index);
			for (int j=0; j<answerDataByCard.numberOfQuestions(); j++) {
				studyingDays.add(milisecToDay(answerDataByCard.getAnswer(j).date));
			}
		}
		return studyingDays;
	}

	public int getNumberOfStudyingDays() {
		Set<Integer> studyingDays = new HashSet<Integer>();
		for (int index : data.keySet()) {
			AnswerDataByCard answerDataByCard = getAnswerDataByCardByIndex(index);
			for (int j=0; j<answerDataByCard.numberOfQuestions(); j++) {
				studyingDays.add(milisecToDay(answerDataByCard.getAnswer(j).date));
			}
		}
		return studyingDays.size();
	}


	public void addAnswerData(AnswerData answerData) {
		if (data.containsKey(answerData.index)) {
			data.get(answerData.index).addAnswer(answerData);
		}
		else {
			AnswerDataByCard answerDataByCard = new AnswerDataByCard();
			answerDataByCard.addAnswer(answerData);
			data.put(answerData.index, answerDataByCard);
		}
	}

	public void loadDataFromFile(String filePath) {
		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader( new FileReader(filePath));
			while( (strLine = br.readLine()) != null){
				AnswerData answerData = new AnswerData();
				answerData.setDataFromString(strLine);
				addAnswerData(answerData);
			}
		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}

	public Histogram getHistogramAtDay(int day) {

		Histogram histogram = new Histogram();

		for (int index : data.keySet()) {
			double rightAnswerRate = data.get(index).countRightAnswerRateAtDay(day);
			if (rightAnswerRate != -1.0) {
				histogram.addAnElementByRightAnswerRate(rightAnswerRate);
				//System.out.println(rightAnswerRate);	//log
			}
		}

		return histogram;
	}

	public Set<Integer> getTestedCardIndexes() {
		return data.keySet();
	}

	public AnswerDataByCard getAnswerDataByCardByIndex(int index) {
		return data.get(index);
	}

	public int numberOfCards() {
		return data.size();
	}

	public AnswerDataByCard[] toArray() {
		AnswerDataByCard[] array = new AnswerDataByCard[numberOfCards()];
		int arrayIndex = 0;
		for (int index : data.keySet()) {
			array[arrayIndex] = data.get(index);
			arrayIndex++;
		}
		return array;
	}

	public void clear() {
		data.clear();
	}

}
