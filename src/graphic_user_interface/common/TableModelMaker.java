package graphic_user_interface.common;

import dictionary.Card;
import dictionary.DictionaryDataContainer;
import dictionary.DictionaryStatisticsMaker;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import language_studyer.AnswerDataByStudyItem;
import language_studyer.AnswerDataByStudyItemContainer;
import language_studyer.AnswerDataContainer;
import language_studyer.Histogram;
import language_studyer.answer_data_by_study_item_comparators.AnswerDataByStudyItemComparatorByRateOfRightAnswers;

public class TableModelMaker {
    
    public DefaultTableModel numberOfAnswersByDays(DictionaryStatisticsMaker statisticsMaker) {
        Map<Integer,Integer> numberOfAnswersByDays = statisticsMaker.getNumberOfAnswersByDays();

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
    
    public DefaultTableModel histogramOfAnswerRatesByDays(DictionaryStatisticsMaker statisticsMaker) {        
        Map<Integer,Histogram> histogramOfStudyItemAnswerRatesByDays 
                = statisticsMaker.getHistogramOfStudyItemAnswerRatesByDays();
        
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
    
    public DefaultTableModel practisingTimeByDay(DictionaryStatisticsMaker statisticsMaker) {

        Map<Integer,String> practisingTimeByDays = statisticsMaker.getPractisingTimeByDaysAsString();

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
    
    public DefaultTableModel histogramOfStudyItemsByNumberOfAnswers(DictionaryStatisticsMaker statisticsMaker) {

        Map<Integer,Integer> histogramOfStudyItemsByNumberOfAnswers 
                = statisticsMaker.getHistogramOfStudyItemsByNumberOfAnswers();

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
    
    public DefaultTableModel numberOfNewStudyItemsQuestionedByDays(DictionaryStatisticsMaker statisticsMaker) {

        Map<Integer,Integer> numberOfNewStudyItemsQuestionedByDays 
                = statisticsMaker.numberOfNewStudyItemsTestedByDays();

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
   
    public DefaultTableModel cardsOrderedByAnswerRate(DictionaryDataContainer dataContainer) {

        AnswerDataByStudyItemContainer answerDataByStudyItemContainer 
                = dataContainer.auxiliaryDataContainer.studiedAnswerDataByStudyItemContainer;
                
        AnswerDataByStudyItem[] sortedDatas = answerDataByStudyItemContainer.toArray();
        Arrays.sort(sortedDatas,
                Collections.reverseOrder(new AnswerDataByStudyItemComparatorByRateOfRightAnswers()));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("term");
        model.addColumn("definition");
        model.addColumn("answer rate");
        model.addColumn("number of answers");
        
        for (AnswerDataByStudyItem answerDataByStudyItem : sortedDatas) {
            Card card = dataContainer.getCardContainer().getCardByIndex(answerDataByStudyItem.getStudyItemIndex());
                  
            DecimalFormat df = new DecimalFormat("#.0000");
            
            model.addRow(new Object[] {
                card.term,
                card.definition,
                df.format(answerDataByStudyItem.countRightAnswerRate()),
                answerDataByStudyItem.numberOfAnswers()
            });
        };
        
        return model;
    }
    
}
