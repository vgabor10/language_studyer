package grammar_book;

import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.text.DecimalFormat;


public class GrammarAnswerDataContainer {

	public Vector<GrammarAnswerData> data = new Vector<GrammarAnswerData>();

	public void addElement(int grammarItemIndex, int exampleIndex, boolean isRight, long date) {
		GrammarAnswerData grammarAnswerData = new GrammarAnswerData(grammarItemIndex, exampleIndex, isRight, date);
		data.add(grammarAnswerData);
	}

	public int numberOfAnswers() {
		return data.size();
	}

	public GrammarAnswerData getAnswerData(int index) {
		return data.get(index);
	}

	/*public int numberOfQuestions() {
		Set<Integer> cardInexes = new HashSet<Integer>();
		for (int i=0; i<data.size(); i++) {
			cardInexes.add(data.elementAt(i).index);
		}
		return cardInexes.size();
	}

	public void toScreenProgress(int numberOfMeasurementPoints) {	//suppose, that data is ordered by time
		DecimalFormat df = new DecimalFormat("#.00");

		Set<Integer> measurementPoints = new HashSet<Integer>();
		for (int i=1; i <= numberOfMeasurementPoints; i++) {
			int a = (int)Math.floor(((double)i * (double)(data.size()) )/ (double)(numberOfMeasurementPoints)) - 1;
			measurementPoints.add(a);
		}

		int numberOfRightAnswers = 0;
		int numberOfAnswers = 0;
		for (int i=0; i < data.size(); i++) {
			if (data.elementAt(i).isRight) {
				numberOfRightAnswers++;
			}
			numberOfAnswers++;

			if (measurementPoints.contains(i)) {
				double d = (double)numberOfRightAnswers/(double)numberOfAnswers * 100.0;
				System.out.print(df.format(d) + "%   ");
				numberOfRightAnswers = 0;
				numberOfAnswers = 0;
			}
		}
		System.out.println();
	}*/	

	public void loadDataFromFile(String filePath) {
		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader( new FileReader(filePath));
			while( (strLine = br.readLine()) != null){
				GrammarAnswerData grammarAnswerData = new GrammarAnswerData();
				grammarAnswerData.setDataFromString(strLine);
				data.addElement(grammarAnswerData);
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

	/*public void toScreenStatistics(CardContainer cardContainer) {
		AnswerData[] a = new AnswerData[(int)data.size()];
		for (int i=0; i<(int)data.size(); i++) {
			a[i] = data.elementAt(i);
		}

		Arrays.sort(a, new AnswerDataComparator());
		int lastIndex=-1;
		int actualIndex;
		int numberOfWrongAnswers = 0;
		String out = "";
		System.out.println("WORDS | ANSWERS | NUMBER OF WRONG ANSWERS");
		if (data.size() != 0) {
			for (int i=0; i<(int)data.size(); i++) {
				actualIndex = a[i].index;
				if (lastIndex != actualIndex) {
					if (i!=0) {
						System.out.println(out + " | " + Integer.toString(numberOfWrongAnswers));

						numberOfWrongAnswers = 0;
						out = cardContainer.data.elementAt(a[i].index).s1;

						if (a[i].isRight) {
							out = out + " | +";
						}
						else {
							out = out + " | -";
							numberOfWrongAnswers++;
						}

					}
					else {
						out = cardContainer.data.elementAt(a[0].index).s1;
						if (a[i].isRight) {
							out = out + " | +";
						}
						else {
							out = out + " | -";
							numberOfWrongAnswers++;
						}
					}
				}
				else {
					if (a[i].isRight) {
						out = out + " +";
					}
					else {
						out = out + " -";
						numberOfWrongAnswers++;
					}
				}

				lastIndex = actualIndex;
			}

			System.out.println(out + " | " + Integer.toString(numberOfWrongAnswers));
		}
	}

	public void toScreenData() {		//for log
		for(int i=0; i<data.size(); i++){
			System.out.println(data.elementAt(i).toStringData());
		}
	}*/

}
