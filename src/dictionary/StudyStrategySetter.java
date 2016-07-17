package dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StudyStrategySetter {
    
    private int studyStrategyIndex;
    
    private String dataFilePath = "../data/settings_data/study_strategy_settings_data_file.txt";
    
    public StudyStrategySetter() {
        loadStadyStrategyIndexFromDisc();
    }
    
    private void loadStadyStrategyIndexFromDisc() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
            studyStrategyIndex = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }        
    }
    
    private void writeStadyStrategyIndexToDisc() {
        try {
            //the true will append the new data
            FileWriter fw = new FileWriter(dataFilePath, false);
            fw.write(Integer.toString(studyStrategyIndex));
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }           
    }
    
    public int getStudyStrategyIndex() {
        return studyStrategyIndex;
    }
    
    public void setStudyStrategyIndex(int index) {
        studyStrategyIndex = index;
        writeStadyStrategyIndexToDisc();
    }
}
