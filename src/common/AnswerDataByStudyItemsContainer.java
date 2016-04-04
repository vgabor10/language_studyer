package common;

import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AnswerDataByStudyItemsContainer {

	private Map<Integer,AnswerDataByStudyItem> data = new HashMap<Integer,AnswerDataByStudyItem>();

	public void addAnswerData(AnswerData answerData) {
		if (data.containsKey(answerData.index)) {
			data.get(answerData.index).addAnswer(answerData);
		}
		else {
			AnswerDataByStudyItem answerDataByStudyItem = new AnswerDataByStudyItem();
			answerDataByStudyItem.addAnswer(answerData);
			data.put(answerData.index, answerDataByStudyItem);
		}
	}

	public int milisecToDay(long milisecTime) {	//TODO: code repeating with AnswerDataStatisticsMaker
		int timeZone = 1;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public Set<Integer> getStudyingDays() {
		Set<Integer> studyingDays = new HashSet<Integer>();
		for (int index : data.keySet()) {
			AnswerDataByStudyItem answerDataByStudyItem = getAnswerDataByStudyItemByIndex(index);
			for (int j=0; j<answerDataByStudyItem.numberOfAnswers(); j++) {
				studyingDays.add(milisecToDay(answerDataByStudyItem.getAnswer(j).date));
			}
		}
		return studyingDays;
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

	public void loadDataFromFile(String filePath) {	//TODO: take other class
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

	public void loadDataFromAnswerDataContainer(AnswerDataContainer answerDataContainer) {
		clear();
		for (int i=0; i<answerDataContainer.numberOfAnswers(); i++) {
			addAnswerData(answerDataContainer.getAnswerData(i));
		}
	}

	public Set<Integer> getTestedStudyItemIndexes() {
		return data.keySet();
	}

	public AnswerDataByStudyItem getAnswerDataByStudyItemByIndex(int index) {
		return data.get(index);
	}

	public double getAverageAnswerRateOfStudyItems() {
		double sum = 0;
		for (int index : data.keySet()) {
			sum = sum + data.get(index).countRightAnswerRate();
		}
		return sum / (double)numberOfStudyItems();
	}

	public int numberOfStudyItems() {
		return data.size();
	}

	public AnswerDataByStudyItem[] toArray() {
		AnswerDataByStudyItem[] array = new AnswerDataByStudyItem[numberOfStudyItems()];
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
