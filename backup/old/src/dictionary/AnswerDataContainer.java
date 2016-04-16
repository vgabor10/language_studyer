package dictionary;

import settings_handler.*;

import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.text.DecimalFormat;		//TODO: remove


public class AnswerDataContainer {

	public Vector<AnswerData> data = new Vector<AnswerData>();	//TODO: private

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

	public int numberOfQuestionedCards() {
		Set<Integer> cardInexes = new HashSet<Integer>();
		for (int i=0; i<numberOfAnswers(); i++) {
			cardInexes.add(getAnswerData(i).index);
		}
		return cardInexes.size();
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

	public double percentageOfRightAnswers(int cardIndex) {
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


	public void loadDataFromFile(String filePath) {
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

	public void saveDataToFile(String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath,false);	//the true will append the new data
			for (int i=0; i<numberOfAnswers(); i++) {
				fw.write(data.get(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void appendToAnswerDataFile(String filePath) {
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

	public void toScreenData() {		//for log
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toStringData());
		}
	}

}
