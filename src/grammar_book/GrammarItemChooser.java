package grammar_book;

import common.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrammarItemChooser extends StudyItemChooser {

    private final Random randomGenerator = new Random();
    private StatisticsMaker statisticsMaker = new StatisticsMaker();
    private final Logger logger = new Logger();

    public int getRandomGrammarItemIndex() {
        logger.debug("run getRandomGrammarItemIndex function");

        int grammarItemIndex = randomGenerator.nextInt(this.studyItemIndexesFromChoose.size());

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getRandomIndexFromThe5LeastStudiedGrammarItem() {
        logger.debug("run getRandomIndexFromThe5LeastStudiedGrammarItem function");

        List<Integer> grammarItemIndexesOrderedByNumberOfAnswers
                = statisticsMaker.getStudyItemIndexesOrderedByNumberOfAnswers();

        int r = randomGenerator.nextInt(5);
        int grammarItemIndex = grammarItemIndexesOrderedByNumberOfAnswers.get(r);

        logger.debug("choosen grammar item index: " + grammarItemIndex);

        return grammarItemIndex;
    }

    public int getIndexFromThe5HardestGrammarItem() {
        logger.debug("run getIndexFromThe5HardestGrammarItem function");

        List<Integer> grammarItemIndexesOrderedByAnswerRate
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
        
        List<Integer> grammarItemIndexses = new ArrayList<>();
        
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
