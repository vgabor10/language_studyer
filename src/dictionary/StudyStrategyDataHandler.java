package dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StudyStrategyDataHandler {
    
    private String dataFilePath 
            = "../data/settings_data/study_strategy_data_file.txt";
    
    public int numberOfRandomCards = 0;
    public int numberOfCardsFromTheLeastKnown20Percent = 0;
    public int numberOfCardsFromTheLeastKnown100 = 0;    
    public int numberOfCardsAmongTheLeastSignificantAr = 0;
    public int numberOfLatestQuestionedCards = 0;
    public boolean studyingGradually = false;
    
    
    public StudyStrategyDataHandler() {
        loadStudyStrategyDataFromDisc();
    }
    
    private void loadStudyStrategyDataFromDisc() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
            
            String line;
            while ((line = br.readLine()) != null) {

                if (line.startsWith("numberOfRandomCards: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    numberOfRandomCards = Integer.parseInt(s);
                }
                
                if (line.startsWith("numberOfCardsFromTheLeastKnown20Percent: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    numberOfCardsFromTheLeastKnown20Percent = Integer.parseInt(s);
                }
                
                if (line.startsWith("numberOfCardsFromTheLeastKnown100: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    numberOfCardsFromTheLeastKnown100 = Integer.parseInt(s);
                }

                if (line.startsWith("numberOfCardsWithLeastSignificantAr: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    numberOfCardsAmongTheLeastSignificantAr = Integer.parseInt(s);
                }

                if (line.startsWith("numberOfLatestQuestionedCards: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);
                    numberOfLatestQuestionedCards = Integer.parseInt(s);
                }
                
                if (line.startsWith("studyingGradually: ")) {
                    String s = line.substring(line.lastIndexOf(":") + 2);
                    //System.out.println(s);

                    if (s.equals("false")) {
                        studyingGradually = false;
                    }
                    
                    if (s.equals("true")) {
                        studyingGradually = true;
                    }
                }

            }
            
            //studyStrategyIndex = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: fileName");
        } catch (IOException e) {
            System.err.println("Unable to read the file: fileName");
        }   
    }
    
    public void writeStudyStrategyDataToDisc() {
        try {
            //the true will append the new data
            FileWriter fw = new FileWriter(dataFilePath, false);
            
            fw.write("numberOfRandomCards: " +
                    Integer.toString(numberOfRandomCards) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown20Percent: "
                    + Integer.toString(numberOfCardsFromTheLeastKnown20Percent) + "\n");

            fw.write("numberOfCardsFromTheLeastKnown100: "
                    + Integer.toString(numberOfCardsFromTheLeastKnown100) + "\n");
            
            fw.write("numberOfCardsWithLeastSignificantAr: "
                    + Integer.toString(numberOfCardsAmongTheLeastSignificantAr) + "\n");

            fw.write("numberOfLatestQuestionedCards: "
                    + Integer.toString(numberOfLatestQuestionedCards) + "\n");
            
            fw.write("studyingGradually: "
                    + Boolean.toString(studyingGradually) + "\n");
            
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }           
    }
}
