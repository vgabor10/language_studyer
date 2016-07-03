package common;

import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Math;


public class AnswerDataContainer {

	private Vector<AnswerData> data = new Vector<AnswerData>();

	public void addAnswerData(AnswerData answerData) {
		data.add(answerData);
	}

	public void addElement(int index, boolean isRight, long date) {
		AnswerData answerData = new AnswerData(index, isRight, date);
		data.add(answerData);
	}

	public int numberOfAnswers() {
		return data.size();
	}

	public AnswerData getAnswerData(int index) {
		return data.get(index);
	}

	public void removeAnswersWithIndex(int index) {
		int i=0;
		while (i<numberOfAnswers()) {
			if (getAnswerData(i).index == index) {
				data.remove(i);
			}
			else {
				i++;
			}
		}
	}

	public double percentageOfRightAnswers() {	//TODO: move it to AnswerDataStatisticsMaker
		if (data.size() != 0) {
			int sum = 0;
			for (int i=0; i<data.size(); i++) {
				if (data.elementAt(i).isRight) {
					sum++;
				}
			}
			return (double)sum/(double)data.size() * 100.0;
		}
		else {
			return -1.0;
		}
	}

	public double getAnswerRateOfCard(int cardIndex) {	//TODO: move it to AnswerDataStatisticsMaker
		int numberOfAnswers = 0;
		int numberOfRightAnswers = 0;

		int i = numberOfAnswers()-1;
		while (0 <= i && numberOfAnswers < 20) {
			if (data.get(i).index == cardIndex) {
				numberOfAnswers++;
				if (data.get(i).isRight) {
					numberOfRightAnswers++;
				}
			}
			i--;
		}
		
		return (double)numberOfRightAnswers/(double)numberOfAnswers;
	}

	public double percentageOfRightAnswers(int cardIndex) {		//TODO: move it to AnswerDataStatisticsMaker
		if (data.size() != 0) {
			int numberOfAnswers = 0;
			int numberOfRightAnswers = 0;
			for (int i=0; i<data.size(); i++) {
				if (data.elementAt(i).index == cardIndex) {
					numberOfAnswers++;
					if (data.elementAt(i).isRight) {
						numberOfRightAnswers++;
					}
				}
			}

			if (numberOfAnswers != 0) {
				return (double)numberOfRightAnswers/(double)numberOfAnswers * 100.0;
			}
			else {
				return -1.0;
			}
		}
		else {
			return -2.0;
		}
	}

	public int numberOfAnswersOfCard(int cardIndex) {//TODO: move it to AnswerDataStatisticsMaker
		int numberOfAnswers = 0;
		for (int i=0; i<data.size(); i++) {
			if (data.elementAt(i).index == cardIndex) {
				numberOfAnswers++;
			}
		}
		return numberOfAnswers;
	}

	public void loadDataFromFile(String filePath) {	//TODO: delete: too specific
		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader( new FileReader(filePath));
			while( (strLine = br.readLine()) != null){
				AnswerData answerData = new AnswerData();
				answerData.setDataFromString(strLine);
				data.addElement(answerData);
			}
		} catch (FileNotFoundException e) {
		    System.err.println("Unable to find the file: fileName");
		} catch (IOException e) {
		    System.err.println("Unable to read the file: fileName");
		}
	}

	public void appendToAnswerDataFile(String filePath) {	//TODO: take it to DictionaryDataModificator class
		try {
			FileWriter fw = new FileWriter(filePath,true);	//the true will append the new data
			for (int i=0; i<data.size(); i++) {
				fw.write(data.elementAt(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void appendAnswerDataContainer(AnswerDataContainer ac) {
		for (int i=0; i<ac.numberOfAnswers(); i++) {
			data.add(ac.getAnswerData(i));
		}
	}

	public void toScreenData() {
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toStringData());
		}
	}

        @Override
	public String toString() {
		String out = "";

		if (1 <= numberOfAnswers()) {
			for(int i=0; i<numberOfAnswers()-1; i++){
				out = out + getAnswerData(i).toStringData() + "\n";
			}
			out = out + getAnswerData(numberOfAnswers()-1).toStringData();
		}

		return out;
	}

}
