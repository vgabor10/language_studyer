package grammar_book;

import study_item_objects.AnswerDataContainer;

import java.io.FileWriter;
import java.io.IOException;

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

        /*@Override
	public GrammarAnswerData getAnswerData(int index) {
		return (GrammarAnswerData)getAnswerData(index);
	}*/

	public void saveDataToFile(String filePath) {   //TODO: move to GrammarDataModificator
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

	public void appendToAnswerDataFile(String filePath) {   //TODO: move to answerDataModificator
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
