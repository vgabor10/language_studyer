package graphic_user_interface.common;

import dictionary.DictionaryAnswerDataStatisticsMaker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import study_item_objects.AnswerDataContainer;
import study_item_objects.Histogram;

public class TableModelMaker {
    
    public DefaultTableModel numberOfAnswersByDays(AnswerDataContainer answerDataContainer) {
        DictionaryAnswerDataStatisticsMaker dictionaryAnswerDataStatisticsMaker
                = new DictionaryAnswerDataStatisticsMaker();
        
	dictionaryAnswerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);

        Map<Integer,Integer> numberOfAnswersByDays = dictionaryAnswerDataStatisticsMaker.getNumberOfAnswersByDays();

        List<Integer> sortedDays = new ArrayList<>(numberOfAnswersByDays.keySet());
        Comparator<Integer> comparator = Collections.reverseOrder();
        Collections.sort(sortedDays, comparator);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("day");
        model.addColumn("number of answers");
        
        for (int day : sortedDays) {
            model.addRow(new Object[] {
                Integer.toString(day),
                numberOfAnswersByDays.get(day)
            });
        };
        
        return model;
    }
    
    public DefaultTableModel histogramOfAnswerRatesByDays(AnswerDataContainer answerDataContainer) {
        DictionaryAnswerDataStatisticsMaker dictionaryAnswerDataStatisticsMaker
                = new DictionaryAnswerDataStatisticsMaker();

        dictionaryAnswerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);
        
        Map<Integer,Histogram> histogramOfStudyItemAnswerRatesByDays 
                = dictionaryAnswerDataStatisticsMaker.getHistogramOfStudyItemAnswerRatesByDays();
        
        List<Integer> sortedDays = new ArrayList<>(histogramOfStudyItemAnswerRatesByDays.keySet());
        Comparator<Integer> comparator = Collections.reverseOrder();
        Collections.sort(sortedDays, comparator);
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("day");
        model.addColumn("0");
        model.addColumn("1");
        model.addColumn("2");
        model.addColumn("3");
        model.addColumn("4");
        model.addColumn("5");
        model.addColumn("6");
        model.addColumn("7");
        model.addColumn("8");
        model.addColumn("9");
        
        for (int day : sortedDays) {
            Histogram histogram = histogramOfStudyItemAnswerRatesByDays.get(day);
            model.addRow(new Object[] {
                day,
                histogram.getNumberOfObjectsByCategory(0),
                histogram.getNumberOfObjectsByCategory(1),
                histogram.getNumberOfObjectsByCategory(2),
                histogram.getNumberOfObjectsByCategory(3),
                histogram.getNumberOfObjectsByCategory(4),
                histogram.getNumberOfObjectsByCategory(5),
                histogram.getNumberOfObjectsByCategory(6),
                histogram.getNumberOfObjectsByCategory(7),
                histogram.getNumberOfObjectsByCategory(8),
                histogram.getNumberOfObjectsByCategory(9)
            });
        };
       
        return model;
    }
    
    public DefaultTableModel practisingTimeByDay(AnswerDataContainer answerDataContainer) {
        DictionaryAnswerDataStatisticsMaker dictionaryAnswerDataStatisticsMaker
                = new DictionaryAnswerDataStatisticsMaker();
        
        
	dictionaryAnswerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);

        Map<Integer,String> practisingTimeByDays = dictionaryAnswerDataStatisticsMaker.getPractisingTimeByDaysAsString();

        List<Integer> sortedDays = new ArrayList<>(practisingTimeByDays.keySet());
        Comparator<Integer> comparator = Collections.reverseOrder();
        Collections.sort(sortedDays, comparator);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("day");
        model.addColumn("practising time");
        
        for (int day : sortedDays) {
            model.addRow(new Object[] {
                Integer.toString(day),
                practisingTimeByDays.get(day)
            });
        };
        
        return model;
    }
    
    public DefaultTableModel histogramOfStudyItemsByNumberOfAnswers(AnswerDataContainer answerDataContainer) {
        DictionaryAnswerDataStatisticsMaker dictionaryAnswerDataStatisticsMaker
                = new DictionaryAnswerDataStatisticsMaker();
        
	dictionaryAnswerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);

        Map<Integer,Integer> histogramOfStudyItemsByNumberOfAnswers 
                = dictionaryAnswerDataStatisticsMaker.getHistogramOfStudyItemsByNumberOfAnswers();

        List<Integer> sortedDays = new ArrayList<>(histogramOfStudyItemsByNumberOfAnswers.keySet());
        Collections.sort(sortedDays);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("number of answers");
        model.addColumn("number of study items");
        
        for (int day : sortedDays) {
            model.addRow(new Object[] {
                Integer.toString(day),
                histogramOfStudyItemsByNumberOfAnswers.get(day)
            });
        };
        
        return model;
    }
    
    public DefaultTableModel numberOfNewStudyItemsQuestionedByDays(AnswerDataContainer answerDataContainer) {
        DictionaryAnswerDataStatisticsMaker dictionaryAnswerDataStatisticsMaker
                = new DictionaryAnswerDataStatisticsMaker();
        
	dictionaryAnswerDataStatisticsMaker.setAnswerDataContainer(answerDataContainer);

        Map<Integer,Integer> numberOfNewStudyItemsQuestionedByDays 
                = dictionaryAnswerDataStatisticsMaker.numberOfNewStudyItemsTestedByDays();

        List<Integer> sortedDays = new ArrayList<>(numberOfNewStudyItemsQuestionedByDays.keySet());
        Comparator<Integer> comparator = Collections.reverseOrder();
        Collections.sort(sortedDays, comparator);
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("day");
        model.addColumn("number of new study items tested");
        
        for (int day : sortedDays) {
            model.addRow(new Object[] {
                Integer.toString(day),
                numberOfNewStudyItemsQuestionedByDays.get(day)
            });
        };       
        
        return model;
    }
    
}
