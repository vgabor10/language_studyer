package grammar_book;

import common.Logger;
import java.util.*;
import study_item_objects.AnswerData;

public class GrammarItemChooser {

    private final Random randomGenerator = new Random();
    private GrammarAnswerDataStatisticsMaker statisticsMaker = new GrammarAnswerDataStatisticsMaker();
    private GrammarItemContainer grammarItemContainer = new GrammarItemContainer();
    private GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
    private final Logger logger = new Logger();

    public void setGrammarBookWithAtLeast10Examples(GrammarItemContainer gb) {
        for (int i = 0; i < gb.numberOfGrammarItems(); i++) {
            if (10 <= gb.getGrammarItemByOrder(i).numberOfExamples()) {
                grammarItemContainer.addGrammarItem(gb.getGrammarItemByOrder(i));
            }
        }
    }

    public void setGrammarAnswerDataContainerWithAtLeast10Examples(GrammarAnswerDataContainer gadc) {
        Set<Integer> grammarItemIndexes = grammarItemContainer.getGrammarItemIndexes();

        for (int i = 0; i < gadc.numberOfAnswers(); i++) {
            AnswerData answerData = gadc.getAnswerData(i);
            if (grammarItemIndexes.contains(answerData.index)) {
                grammarAnswerDataContainer.addAnswerData(answerData);
            }
        }
    }

    public void initialise() {
        //statisticsMaker.setData(grammarItemContainer);
        statisticsMaker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);
        
        logger.debug("aaa: " + grammarItemContainer.numberOfGrammarItems());
    }

    public int getRandomGrammarItemIndex() {
        logger.debug("run getRandomGrammarItemIndex function");

        int orderIndex = randomGenerator.nextInt(grammarItemContainer.numberOfGrammarItems());
        int grammarItemIndex = grammarItemContainer.getGrammarItemByOrder(orderIndex).index;

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getRandomIndexFromThe5LeastStudiedGrammarItem() {
        logger.debug("run getRandomIndexFromThe5LeastStudiedGrammarItem function");

        Vector<Integer> grammarItemIndexesOrderedByNumberOfAnswers
                = statisticsMaker.getStudyItemIndexesOrderedByNumberOfAnswers();

        int r = randomGenerator.nextInt(5);
        int grammarItemIndex = grammarItemIndexesOrderedByNumberOfAnswers.get(r);

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getIndexFromThe5HardestGrammarItem() {
        logger.debug("run getIndexFromThe5HardestGrammarItem function");

        Vector<Integer> grammarItemIndexesOrderedByAnswerRate
                = statisticsMaker.getStudyItemIndexesOrderedByAnswerRate();
        int r = grammarItemIndexesOrderedByAnswerRate.size() - 1 - randomGenerator.nextInt(5);
        int grammarItemIndex = grammarItemIndexesOrderedByAnswerRate.get(r);

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getIndexOfLatestStudiedGrammarItem() {
        logger.debug("run getIndexOfLatestStudiedGrammarItem function");

        int grammarItemIndex = statisticsMaker.getLastStudiedGrammarItemIndex();

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    //random grammar item from a random and the latest studyed item
    public int getGrammarItemIndexForTest1() {

        logger.debug("run getGrammarItemIndexForTest1 function");
        
        ArrayList<Integer> grammarItemIndexses = new ArrayList<>();
        
        grammarItemIndexses.add(getRandomGrammarItemIndex());
        //grammarItemIndexses.add(getIndexOfLatestStudiedGrammarItem());

        int grammarItemIndex = grammarItemIndexses.get(
                randomGenerator.nextInt(grammarItemIndexses.size()));
        
        logger.debug("choosen grammar item index: " + grammarItemIndex);
        
        return grammarItemIndex;
    }
    
    //random grammar item from a random, 5 least studied, 5 hardest, latest studyed item
    public int getGrammarItemIndexForTest3() {
        logger.debug("run getGrammarItemIndexForTest3 function");

        ArrayList<Integer> grammarItemIndexses = new ArrayList<>();

        grammarItemIndexses.add(getRandomGrammarItemIndex());
        grammarItemIndexses.add(getRandomIndexFromThe5LeastStudiedGrammarItem());
        grammarItemIndexses.add(getIndexFromThe5HardestGrammarItem());
        grammarItemIndexses.add(getIndexOfLatestStudiedGrammarItem());

        int grammarItemIndex = grammarItemIndexses.get(
                randomGenerator.nextInt(grammarItemIndexses.size()));

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

}
