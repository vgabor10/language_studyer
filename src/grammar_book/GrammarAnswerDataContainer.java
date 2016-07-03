package grammar_book;

import study_item_objects.AnswerDataContainer;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;


public class GrammarAnswerDataContainer extends AnswerDataContainer {

        //TODO: remove this function
	public void addElement(int grammarItemIndex, int exampleIndex, boolean isRight, long date) {
		GrammarAnswerData grammarAnswerData = new GrammarAnswerData();
		grammarAnswerData.index = grammarItemIndex;
		grammarAnswerData.exampleIndex = exampleIndex;
		grammarAnswerData.isRight = isRight;
		grammarAnswerData.date = date;

		addAnswerData(grammarAnswerData);
	}

	/*public GrammarAnswerData getAnswerData(int index) {
		return (GrammarAnswerData)getAnswerData(index);
	}*/

	public void loadDataFromFile(String filePath) {

		BufferedReader br = null;
		String strLine = "";
		try {
			br = new BufferedReader( new FileReader(filePath));
			while( (strLine = br.readLine()) != null){
				GrammarAnswerData grammarAnswerData = new GrammarAnswerData();
				grammarAnswerData.setDataFromString(strLine);
				addAnswerData(grammarAnswerData);
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
				fw.write(getAnswerData(i).toStringData() + "\n");	//appends the string to the file
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
			for (int i=0; i<numberOfAnswers(); i++) {
				fw.write(getAnswerData(i).toStringData() + "\n");	//appends the string to the file
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

}
