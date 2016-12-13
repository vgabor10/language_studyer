package study_item_objects;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class StatisticsToFileWriter {
    
    public AnswerDataStatisticsMaker answerDataStatisticsMaker;
    
    public void toFileHistogramOfStudyItemAnswerRatesByNumberOfAnswers(
        String filePath, int numberOfAnswers) {
        
        Map<Integer, Histogram> data = 
                answerDataStatisticsMaker.getHistogramOfStudyItemAnswerRatesByNumberOfAnswers(numberOfAnswers);

        Set<Integer> keys = data.keySet();
        SortedSet<Integer> sortedDays = new TreeSet<>(keys);

        try {
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);
            for (int day : sortedDays) {
                fw.write(day + "\t" + data.get(day).toStringHorisontally("\t") + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    public void toFileNumberOfAnswersByDays(String filePath) {
        Map<Integer, Integer> numberOfAnswersByDays = 
                answerDataStatisticsMaker.getNumberOfAnswersByDays();
        
        Set<Integer> keys = numberOfAnswersByDays.keySet();
        SortedSet<Integer> sortedDays = new TreeSet<>(keys);

        try {
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);
            for (int day : sortedDays) {
                fw.write(day + "\t" + numberOfAnswersByDays.get(day) + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public void toFileHistogramOfStudyItemAnswerRatesByDays(String filePath) {
        Map<Integer, Histogram> data = 
                answerDataStatisticsMaker.getHistogramOfStudyItemAnswerRatesByDays();

        Set<Integer> keys = data.keySet();
        SortedSet<Integer> sortedDays = new TreeSet<>(keys);

        try {
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);
            for (int day : sortedDays) {
                fw.write(day + "\t" + data.get(day).toStringHorisontally("\t") + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }    
    
    public void toFileBigRightIntervallSizesOrderedByDays(String filePath) {
        List<Integer> data = 
                answerDataStatisticsMaker.getBigRightIntervallSizesOrderedByDays(10);
        
        try {
            //the true will append the new data
            FileWriter fw = new FileWriter(filePath, false);
            for (int i =0; i<data.size(); i++) {
                if (i % 2 == 0) {
                    fw.write(data.get(i) + "\t");
                }
                else {
                    fw.write(data.get(i) + "\n");
                }
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
}
