package grammar_book;

import common.Logger;
import java.util.*;
import study_item_objects.AnswerData;

public class RandomGrammarItemChooser {

    private final Random randomGenerator = new Random();
    private GrammarAnswerDataStatisticsMaker grammarAnswerDataStatisticsMaker = new GrammarAnswerDataStatisticsMaker();
    private GrammarBook grammarBook = new GrammarBook();
    private GrammarAnswerDataContainer grammarAnswerDataContainer = new GrammarAnswerDataContainer();
    private final Logger logger = new Logger();

    public void setGrammarBookWithAtLeast10Examples(GrammarBook gb) {
        for (int i = 0; i < gb.numberOfGrammarItems(); i++) {
            if (10 <= gb.getGrammarItemByOrder(i).numberOfExamples()) {
                grammarBook.addGrammarItem(gb.getGrammarItemByOrder(i));
            }
        }
    }

    public void setGrammarAnswerDataContainerWithAtLeast10Examples(GrammarAnswerDataContainer gadc) {
        Set<Integer> grammarItemIndexes = grammarBook.getGrammarItemIndexes();

        for (int i = 0; i < gadc.numberOfAnswers(); i++) {
            AnswerData answerData = gadc.getAnswerData(i);
            if (grammarItemIndexes.contains(answerData.index)) {
                grammarAnswerDataContainer.addAnswerData(answerData);
            }
        }
    }

    public void initialise() {
        grammarAnswerDataStatisticsMaker.setGrammarBook(grammarBook);
        grammarAnswerDataStatisticsMaker.setGrammarAnswerDataContainer(grammarAnswerDataContainer);
        
        logger.debug("aaa: " + grammarBook.numberOfGrammarItems());
    }

    public int getRandomGrammarItemIndex() {
        logger.debug("run getRandomGrammarItemIndex function");

        int orderIndex = randomGenerator.nextInt(grammarBook.numberOfGrammarItems());
        int grammarItemIndex = grammarBook.getGrammarItemByOrder(orderIndex).index;

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getRandomIndexFromThe5LeastStudiedGrammarItem() {
        logger.debug("run getRandomIndexFromThe5LeastStudiedGrammarItem function");

        Vector<Integer> grammarItemIndexesOrderedByNumberOfAnswers
                = grammarAnswerDataStatisticsMaker.getStudyItemIndexesOrderedByNumberOfAnswers();

        int r = randomGenerator.nextInt(5);
        int grammarItemIndex = grammarItemIndexesOrderedByNumberOfAnswers.get(r);

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getIndexFromThe5HardestGrammarItem() {
        logger.debug("run getIndexFromThe5HardestGrammarItem function");

        Vector<Integer> grammarItemIndexesOrderedByAnswerRate
                = grammarAnswerDataStatisticsMaker.getStudyItemIndexesOrderedByAnswerRate();
        int r = grammarItemIndexesOrderedByAnswerRate.size() - 1 - randomGenerator.nextInt(5);
        int grammarItemIndex = grammarItemIndexesOrderedByAnswerRate.get(r);

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getIndexOfLatestStudiedGrammarItem() {
        logger.debug("run getIndexOfLatestStudiedGrammarItem function");

        int grammarItemIndex = grammarAnswerDataStatisticsMaker.getLastStudiedGrammarItemIndex();

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    //random grammar item from a random and the latest studyed item
    public int getGrammarItemIndexForTest1() {

        logger.debug("run getGrammarItemIndexForTest1 function");
        
        ArrayList<Integer> grammarItemIndexses = new ArrayList<>();
        
        grammarItemIndexses.add(getRandomGrammarItemIndex());
        grammarItemIndexses.add(getIndexOfLatestStudiedGrammarItem());

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
